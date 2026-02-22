package edu.ucne.dragonball_planets.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.room.util.copy
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.dragonball_planets.data.remote.Resource
import edu.ucne.dragonball_planets.domain.usecase.GetPlanetDetailUseCase
import edu.ucne.dragonball_planets.presentation.navigation.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.ui.tooling.preview.Preview


@HiltViewModel
class DetailPlanetViewModel @Inject constructor(
    private val getPlanetDetailUseCase: GetPlanetDetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(DetailPlanetUiState())
    val state = _state.asStateFlow()

    init {
        val args = savedStateHandle.toRoute<Screen.PlanetDetail>()
        loadPlanet(args.id)
    }

    private fun loadPlanet(id: Int) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            when (val result = getPlanetDetailUseCase(id)) {
                is Resource.Success ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            planet = result.data
                        )
                    }

                is Resource.Error ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }

                is Resource.Loading -> Unit
            }
        }
    }
}