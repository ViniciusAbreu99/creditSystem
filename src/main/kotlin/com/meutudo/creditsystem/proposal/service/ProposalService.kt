package com.meutudo.creditsystem.proposal.service

import com.meutudo.creditsystem.proposal.entity.ProposalEntity
import com.meutudo.creditsystem.proposal.entity.ProposalStatus
import com.meutudo.creditsystem.proposal.exception.InvalidProposalException
import com.meutudo.creditsystem.proposal.exception.SimulationNotFoundException
import com.meutudo.creditsystem.proposal.repository.ProposalRepository
import com.meutudo.creditsystem.proposal.rest.dto.ProposalRequestDTO
import com.meutudo.creditsystem.simulation.service.SimulationService
import org.slf4j.LoggerFactory
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service

@Service
class ProposalService(
    private val proposalRepository: ProposalRepository,
    private val simulationService: SimulationService
) {
    private val logger = LoggerFactory.getLogger(ProposalService::class.java)

    fun create(request: ProposalRequestDTO): ProposalEntity {
        logger.info("Iniciando criação de proposta para simulationId={}, cpf={}", request.simulationId, request.cpf)

        if (!simulationService.existsBySimulationId(request.simulationId)) {
            logger.error("Simulação não encontrada: simulationId={}", request.simulationId)
            throw SimulationNotFoundException()
        }

        logger.info("Simulação validada com sucesso: simulationId={}", request.simulationId)

        return try {
            val proposal = ProposalEntity(
                cpf = request.cpf,
                name = request.name,
                simulationId = request.simulationId,
                status = ProposalStatus.CREATED
            )

            val saved = proposalRepository.save(proposal)
            logger.info("Proposta criada com sucesso: id={}, simulationId={}, cpf={}, status=CREATED", saved.id, saved.simulationId, saved.cpf)

            sendForAnalysis(saved)

            saved
        } catch (ex: DataIntegrityViolationException) {
            logger.warn("Proposta com simulationId={} já existe, retornando existente", request.simulationId)
            proposalRepository.findBySimulationId(request.simulationId)
                ?: throw ex
        }
    }

    fun findById(id: Long): ProposalEntity? {
        logger.info("Buscando proposta por ID: id={}", id)
        return proposalRepository.findById(id).orElse(null)
    }

    private fun sendForAnalysis(proposal: ProposalEntity) {
        logger.info("Enviando proposta para análise: proposalId={}, simulationId={}, cpf={}", proposal.id, proposal.simulationId, proposal.cpf)

        proposal.status = ProposalStatus.IN_ANALYSIS

        proposalRepository.save(proposal)
        logger.info("Proposta salva em análise: proposalId={}, status=IN_ANALYSIS", proposal.id)
    }

    fun contract(proposalId: Long) : ProposalEntity {

        val proposalEntity = proposalRepository.findByIdAndStatus(proposalId, ProposalStatus.APPROVED) ?: throw InvalidProposalException()
        proposalEntity.status = ProposalStatus.CONTRACTED

        return proposalRepository.save(proposalEntity)
    }
}
