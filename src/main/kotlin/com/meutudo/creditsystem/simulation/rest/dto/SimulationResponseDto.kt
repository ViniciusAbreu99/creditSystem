package com.meutudo.creditsystem.simulation.rest.dto

import java.math.BigDecimal
import java.util.UUID

data class SimulationResponseDto(
    val simulateId: UUID,
    val valorDaParcela: BigDecimal,
    val valorSolicitado: BigDecimal,
    val quantidadeDeParcelas: Int,
    val renda: BigDecimal
)

