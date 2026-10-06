package com.emprestimo.creditsystem.proposal.rest

import com.emprestimo.creditsystem.proposal.rest.dto.CreditAnalysisResultDTO
import com.emprestimo.creditsystem.proposal.service.CreditAnalysisService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/webhooks")
class WebHookController(
    private val creditAnalysisService: CreditAnalysisService
) {

    @PostMapping("credit-analysis")
    fun creditAnalysis(@RequestBody creditAnalysisResultDTO: CreditAnalysisResultDTO) {
        creditAnalysisService.analyze(creditAnalysisResultDTO)
    }
}