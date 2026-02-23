package edu.ucne.dragonball_planets.data.repository

import edu.ucne.dragonball_planets.data.remote.DragonBallApi
import edu.ucne.dragonball_planets.data.remote.Resource
import edu.ucne.dragonball_planets.data.remote.dto.PlanetDto
import edu.ucne.dragonball_planets.data.remote.dto.PlanetResponseDto
import edu.ucne.dragonball_planets.domain.repository.PlanetRepository
import javax.inject.Inject

class PlanetRepositoryImpl @Inject constructor(
    private val api: DragonBallApi
) : PlanetRepository {

    override suspend fun getPlanets(
        page: Int,
        limit: Int,
        name: String?,
        isDestroyed: Boolean?
    ): Resource<List<PlanetDto>> {
        return try {
            val listaFinal: List<PlanetDto> = if (!name.isNullOrBlank()) {
                val response = api.searchPlanets(name)
                response.body() ?: emptyList()
            } else {
                val response = api.getPlanets(page, limit)
                response.body()?.items ?: emptyList()
            }

            Resource.Success(listaFinal)
        } catch (e: Exception) {
            Resource.Error("Error de conexion: ${e.localizedMessage}")
        }
    }

    override suspend fun getPlanetDetail(id: Int): Resource<PlanetDto> {
        return try {
            val response = api.getPlanetDetail(id)
            val planet = response.body()
            if (response.isSuccessful && planet != null) {
                Resource.Success(planet)
            } else {
                Resource.Error("Planeta no encontrado")
            }
        } catch (e: Exception) {
            Resource.Error("Error: ${e.message}")
        }
    }
}