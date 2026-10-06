package com.meutudo.creditsystem.proposal.service

import com.meutudo.creditsystem.proposal.entity.ProposalStatus
import com.meutudo.creditsystem.proposal.exception.InvalidProposalException
import com.meutudo.creditsystem.proposal.repository.ProposalRepository
import com.meutudo.creditsystem.proposal.rest.dto.CreditAnalysisResultDTO
import org.springframework.stereotype.Service

@Service
class CreditAnalysisService(
    private val proposalRepository: ProposalRepository
) {

    fun analyze(creditAnalysisResultDTO: CreditAnalysisResultDTO) {
        val status = if (creditAnalysisResultDTO.score >= 600) ProposalStatus.APPROVED else ProposalStatus.REJECTED

        val proposal = proposalRepository.findByIdAndStatus(creditAnalysisResultDTO.proposalId.toLong(), ProposalStatus.IN_ANALYSIS)

        if (proposal != null) {
            proposal.status = status
            proposalRepository.save(proposal)
        } else {
            throw InvalidProposalException()
        }
    }
}
