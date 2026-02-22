package edu.ucne.dragonball_planets.data.remote.dto

import edu.ucne.dragonball_planets.domain.model.Planet

data class PlanetResponseDto(
    val items: List<PlanetDto>
)

data class PlanetDto (
    val id: Int,
    val name: String,
    val isDestroyed: Boolean,
    val description: String,
    val image: String,
)  {
    fun toDomain() = Planet(
        id = id,
        name = name,
        isDestroyed = isDestroyed,
        description = description,
        image = image,
    )
}