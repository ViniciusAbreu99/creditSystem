package com.meutudo.creditsystem.simulation.rest.dto

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import java.math.BigDecimal

data class SimulationRequestDto(
    @field:NotNull(message = "O valor solicitado é obrigatório.")
    @field:Positive(message = "O valor solicitado deve ser maior que zero.")
    val valorSolicitado: BigDecimal,

    @field:NotNull(message = "O número de parcelas é obrigatório.")
    @field:Min(value = 6, message = "O número de parcelas deve ser no mínimo 6.")
    @field:Max(value = 48, message = "O número de parcelas deve ser no máximo 48.")
    val numeroParcelas: Int,

    @field:NotNull(message = "A renda mensal líquida é obrigatória.")
    @field:Positive(message = "A renda mensal líquida deve ser maior que zero.")
    val rendaMensalLiquida: BigDecimal
)
