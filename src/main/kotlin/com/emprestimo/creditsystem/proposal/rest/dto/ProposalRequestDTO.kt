package com.emprestimo.creditsystem.proposal.rest.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.util.UUID

data class ProposalRequestDTO(
    @field:NotBlank(message = "O CPF é obrigatório.")
    val cpf: String,

    @field:NotBlank(message = "O nome é obrigatório.")
    val name: String,

    @field:NotNull(message = "O simulationId é obrigatório.")
    val simulationId: UUID
)
