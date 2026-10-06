package com.meutudo.creditsystem.proposal.rest

import com.meutudo.creditsystem.proposal.entity.ProposalEntity
import com.meutudo.creditsystem.proposal.rest.dto.ProposalRequestDTO
import com.meutudo.creditsystem.proposal.rest.dto.ProposalResponseDto
import com.meutudo.creditsystem.proposal.service.ProposalService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/proposal")
class ProposalController(
    private val proposalService: ProposalService
) {

    @PostMapping
    fun create(@Valid @RequestBody request: ProposalRequestDTO): ResponseEntity<ProposalResponseDto> {
        val proposal: ProposalEntity = proposalService.create(request)

        val response = ProposalResponseDto(
            id = proposal.id!!,
            cpf = proposal.cpf,
            name = proposal.name,
            simulationId = proposal.simulationId,
            status = proposal.status
        )

        return ResponseEntity.ok(response)
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<ProposalResponseDto> {
        val proposal = proposalService.findById(id) ?: return ResponseEntity.notFound().build()

        val response = ProposalResponseDto(
            id = proposal.id!!,
            cpf = proposal.cpf,
            name = proposal.name,
            simulationId = proposal.simulationId,
            status = proposal.status
        )

        return ResponseEntity.ok(response)
    }

    @PostMapping("/{id}/contract")
    fun contract(@PathVariable("id") proposalId: Long): ResponseEntity<ProposalResponseDto> {

        val proposal = proposalService.contract(proposalId)
        val response = ProposalResponseDto(
            id = proposal.id!!,
            cpf = proposal.cpf,
            name = proposal.name,
            simulationId = proposal.simulationId,
            status = proposal.status
        )
        return ResponseEntity.ok(response)
    }
}
