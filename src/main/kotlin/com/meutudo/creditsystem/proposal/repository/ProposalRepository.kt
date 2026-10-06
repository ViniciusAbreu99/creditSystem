package com.meutudo.creditsystem.proposal.repository

import com.meutudo.creditsystem.proposal.entity.ProposalEntity
import com.meutudo.creditsystem.proposal.entity.ProposalStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ProposalRepository : JpaRepository<ProposalEntity, Long> {
    fun findBySimulationId(simulationId: UUID): ProposalEntity?
    fun findByIdAndStatus(id: Long, status: ProposalStatus): ProposalEntity?
}
