package edu.ucne.dragonball_planets.presentation.list

import edu.ucne.dragonball_planets.data.remote.dto.PlanetDto

data class ListPlanetUiState (
    val isLoading: Boolean = false,
    val planets: List<PlanetDto> = emptyList(),
    val error: String? = null,
    val filterName: String = "",
    val filterIsDestroyed: Boolean? = null
)