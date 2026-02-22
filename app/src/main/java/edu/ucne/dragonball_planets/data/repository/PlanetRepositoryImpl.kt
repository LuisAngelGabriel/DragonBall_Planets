package edu.ucne.dragonball_planets.data.repository

import edu.ucne.dragonball_planets.data.remote.DragonBallApi
import edu.ucne.dragonball_planets.data.remote.Resource
import edu.ucne.dragonball_planets.data.remote.dto.PlanetDto
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
            val response = api.getPlanets(page, limit, name, isDestroyed)

            if (response.isSuccessful && response.body() != null) {
                val data = response.body()!!.items
                Resource.Success(data)
            } else {
                Resource.Error("Error servidor: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error("Error conexión: ${e.localizedMessage}")
        }
    }

    override suspend fun getPlanetDetail(id: Int): Resource<PlanetDto> {
        return try {
            val response = api.getPlanetDetail(id)
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error("Planeta no encontrado")
            }
        } catch (e: Exception) {
            Resource.Error("Error: ${e.message}")
        }
    }
}