package edu.ucne.dragonball_planets.presentation.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import edu.ucne.dragonball_planets.data.remote.dto.PlanetDto
import edu.ucne.dragonball_planets.presentation.list.ListPlanetUiState
import edu.ucne.dragonball_planets.presentation.planet_list.ListPlanetBodyScreen

@Composable
fun DetailPlanetScreen(
    viewModel: DetailPlanetViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    DetailPlanetBodyScreen(
        state = state,
        onBack = onBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPlanetBodyScreen(
    state: DetailPlanetUiState,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del Planeta") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, null)
                    }
                }
            )
        }
    ) { padding ->
        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        state.planet?.let { planet ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                AsyncImage(
                    model = planet.image,
                    contentDescription = planet.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = planet.name,
                    style = MaterialTheme.typography.headlineMedium
                )

                Text(
                    text = if (planet.isDestroyed) "Estado: Destruido" else "Estado: Activo",
                    style = MaterialTheme.typography.titleMedium,
                    color = if (planet.isDestroyed) Color.Red else Color.Green
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Descripción:",
                    style = MaterialTheme.typography.titleSmall
                )

                Text(
                    text = planet.description,
                    style = MaterialTheme.typography.bodyLarge
                )
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
}