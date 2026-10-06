package com.emprestimo.creditsystem.simulation.repository

import com.emprestimo.creditsystem.simulation.entity.SimulationEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface SimulationRepository : JpaRepository<SimulationEntity, Long> {
    fun existsBySimulationId(simulationId: UUID): Boolean
}
