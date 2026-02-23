package edu.ucne.dragonball_planets.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.dragonball_planets.data.remote.Resource
import edu.ucne.dragonball_planets.domain.usecase.GetPlanetsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListPlanetViewModel @Inject constructor(
    private val getPlanetsUseCase: GetPlanetsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ListPlanetUiState())
    val state = _state.asStateFlow()

    init {
        loadPlanets()
    }

    fun onEvent(event: ListPlanetUiEvent) {
        when (event) {
            is ListPlanetUiEvent.UpdateFilters -> {
                _state.update {
                    it.copy(
                        filterName = event.name,
                        filterIsDestroyed = event.isDestroyed
                    )
                }
                loadPlanets()
            }
            ListPlanetUiEvent.Search -> loadPlanets()
        }
    }

    private fun loadPlanets() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            val current = _state.value

            val result = getPlanetsUseCase(
                name = current.filterName.takeIf { it.isNotBlank() }
            )

            when (result) {
                is Resource.Success -> {
                    val data = result.data ?: emptyList()
                    val filteredData = if (current.filterIsDestroyed != null) {
                        data.filter { it.isDestroyed == current.filterIsDestroyed }
                    } else {
                        data
                    }
                    _state.update {
                        it.copy(
                            isLoading = false,
                            planets = filteredData
                        )
                    }
                }
                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
                is Resource.Loading -> {
                    _state.update { it.copy(isLoading = true) }
                }
            }
        }
    }
}