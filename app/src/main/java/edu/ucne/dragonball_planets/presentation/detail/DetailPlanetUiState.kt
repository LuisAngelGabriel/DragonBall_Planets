package edu.ucne.dragonball_planets.presentation.detail

import edu.ucne.dragonball_planets.data.remote.dto.PlanetDto

data class DetailPlanetUiState(
    val isLoading: Boolean = false,
    val planet: PlanetDto? = null,
    val error: String? = null
)