package edu.ucne.dragonball_planets.presentation.planet_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import edu.ucne.dragonball_planets.data.remote.dto.PlanetDto
import edu.ucne.dragonball_planets.presentation.list.ListPlanetUiEvent
import edu.ucne.dragonball_planets.presentation.list.ListPlanetUiState
import edu.ucne.dragonball_planets.presentation.list.ListPlanetViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListPlanetScreen(
    viewModel: ListPlanetViewModel = hiltViewModel(),
    onPlanetClick: (Int) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    ListPlanetBodyScreen(
        state = state,
        onEvent = viewModel::onEvent,
        onPlanetClick = onPlanetClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListPlanetBodyScreen(
    state: ListPlanetUiState,
    onEvent: (ListPlanetUiEvent) -> Unit,
    onPlanetClick: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Dragon Ball Planets") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = state.filterName,
                onValueChange = { onEvent(ListPlanetUiEvent.UpdateFilters(it, state.filterIsDestroyed)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Buscar planeta...") },
                trailingIcon = {
                    IconButton(onClick = { onEvent(ListPlanetUiEvent.Search) }) {
                        Icon(Icons.Default.Search, contentDescription = null)
                    }
                },
                singleLine = true
            )

            if (state.isLoading) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            if (state.error != null) {
                Text(
                    text = state.error,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(16.dp).align(Alignment.CenterHorizontally)
                )
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(state.planets) { planet ->
                    PlanetItem(
                        planet = planet,
                        onClick = { onPlanetClick(planet.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun PlanetItem(
    planet: PlanetDto,
    onClick: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = planet.image,
                contentDescription = planet.name,
                modifier = Modifier.size(70.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = planet.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = if (planet.isDestroyed) "Destruido" else "Activo",
                    style = MaterialTheme.typography.bodySmall,
                    color = if (planet.isDestroyed)
                        MaterialTheme.colorScheme.error
                    else
                        MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListPlanetBodyScreenPreview() {
    val samplePlanets = listOf(
        PlanetDto(
            id = 2,
            name = "Tierra",
            isDestroyed = false,
            description = "Planeta de los guerreros Z",
            image = ""
        )
    )
    val state = ListPlanetUiState(
        planets = samplePlanets,
        filterName = ""
    )

    MaterialTheme {
        Surface {
            ListPlanetBodyScreen(
                state = state,
                onEvent = {},
                onPlanetClick = {}
            )
        }
    }
}