package edu.ucne.dragonball_planets.domain.repository

import edu.ucne.dragonball_planets.data.remote.Resource
import edu.ucne.dragonball_planets.data.remote.dto.PlanetDto

interface PlanetRepository {
    suspend fun getPlanets(
        page: Int,
        limit: Int,
        name: String?,
        isDestroyed: Boolean?
    ): Resource<List<PlanetDto>>

    suspend fun getPlanetDetail(id: Int): Resource<PlanetDto>
}