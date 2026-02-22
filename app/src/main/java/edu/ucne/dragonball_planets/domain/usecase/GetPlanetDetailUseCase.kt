package edu.ucne.dragonball_planets.domain.usecase

import edu.ucne.dragonball_planets.domain.repository.PlanetRepository
import javax.inject.Inject

class GetPlanetDetailUseCase @Inject constructor(
    private val repository: PlanetRepository
) {
    suspend operator fun invoke(id: Int) = repository.getPlanetDetail(id)
}