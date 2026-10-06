package com.meutudo.creditsystem.proposal.rest.dto

import com.meutudo.creditsystem.proposal.entity.ProposalStatus
import java.util.UUID

data class ProposalResponseDto(
    val id: Long,
    val cpf: String,
    val name: String,
    val simulationId: UUID,
    val status: ProposalStatus
)
