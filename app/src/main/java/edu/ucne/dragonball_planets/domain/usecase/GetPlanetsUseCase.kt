package edu.ucne.dragonball_planets.domain.usecase

import edu.ucne.dragonball_planets.data.remote.Resource
import edu.ucne.dragonball_planets.data.remote.dto.PlanetDto
import edu.ucne.dragonball_planets.domain.repository.PlanetRepository
import javax.inject.Inject

class GetPlanetsUseCase @Inject constructor(
    private val repository: PlanetRepository
) {
    suspend operator fun invoke(
        page: Int = 1,
        limit: Int = 10,
        name: String? = null
    ): Resource<List<PlanetDto>> {
        return repository.getPlanets(page, limit, name, null)
    }
}