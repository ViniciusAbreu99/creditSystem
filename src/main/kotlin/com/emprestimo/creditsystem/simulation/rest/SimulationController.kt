package com.emprestimo.creditsystem.simulation.rest

import com.emprestimo.creditsystem.simulation.entity.SimulationEntity
import com.emprestimo.creditsystem.simulation.rest.dto.SimulationRequestDto
import com.emprestimo.creditsystem.simulation.rest.dto.SimulationResponseDto
import com.emprestimo.creditsystem.simulation.service.SimulationService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController("/simulation")
class SimulationController(
    private val simulationService: SimulationService
) {

    @PostMapping("simulate")
    fun simulate(@Valid @RequestBody request: SimulationRequestDto): ResponseEntity<SimulationResponseDto> {
        val result: SimulationEntity = simulationService.simulate(request)

        val response = SimulationResponseDto(
            simulateId = result.simulationId,
            valorDaParcela = result.monthlyInstallment,
            valorSolicitado = result.requestedAmount,
            quantidadeDeParcelas = result.installmentCount,
            renda = request.rendaMensalLiquida
        )

        return ResponseEntity.ok(response)
    }
}