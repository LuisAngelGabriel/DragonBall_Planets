package edu.ucne.dragonball_planets.presentation.list

sealed interface ListPlanetUiEvent {

    data class UpdateFilters(
        val name: String,
        val isDestroyed: Boolean?
    ) : ListPlanetUiEvent

    data object Search : ListPlanetUiEvent
}