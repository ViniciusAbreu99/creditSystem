package com.emprestimo.creditsystem.proposal.service

import com.emprestimo.creditsystem.proposal.entity.ProposalStatus
import com.emprestimo.creditsystem.proposal.exception.InvalidProposalException
import com.emprestimo.creditsystem.proposal.repository.ProposalRepository
import com.emprestimo.creditsystem.proposal.rest.dto.CreditAnalysisResultDTO
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
