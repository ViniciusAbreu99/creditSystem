package com.meutudo.creditsystem.proposal.exception

import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.LocalDateTime

data class ErrorResponse(
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val status: Int,
    val error: String,
    val message: String,
    val path: String
)

@RestControllerAdvice
class GlobalProposalExceptionHandler {
    private val logger = LoggerFactory.getLogger(GlobalProposalExceptionHandler::class.java)

    @ExceptionHandler(ProposalException::class)
    fun handleProposalException(
        ex: ProposalException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        logger.error("Erro na proposta: {} - path={}", ex.message, request.requestURI)

        val response = ErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            error = HttpStatus.BAD_REQUEST.reasonPhrase,
            message = ex.message ?: "Erro ao processar proposta.",
            path = request.requestURI
        )

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response)
    }
}

