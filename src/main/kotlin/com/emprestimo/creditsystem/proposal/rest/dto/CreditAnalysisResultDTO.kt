package com.emprestimo.creditsystem.proposal.rest.dto

import java.time.LocalDateTime

data class CreditAnalysisResultDTO (
    val eventId: String,
    val proposalId: String,
    val score: Int,
    val occurredAt: LocalDateTime
)
