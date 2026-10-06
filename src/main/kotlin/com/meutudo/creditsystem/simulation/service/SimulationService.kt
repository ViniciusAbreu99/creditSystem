package com.meutudo.creditsystem.simulation.service

import com.meutudo.creditsystem.simulation.rest.dto.SimulationRequestDto
import com.meutudo.creditsystem.simulation.entity.SimulationEntity
import com.meutudo.creditsystem.simulation.exception.InvalidInstallmentCountException
import com.meutudo.creditsystem.simulation.exception.InvalidMonthlyIncomeException
import com.meutudo.creditsystem.simulation.exception.InvalidMonthlyInstallmentException
import com.meutudo.creditsystem.simulation.exception.InvalidRequestedAmountException
import com.meutudo.creditsystem.simulation.repository.SimulationRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.UUID

@Service
class SimulationService(
    private val simulationRepository: SimulationRepository
) {
    private val logger = LoggerFactory.getLogger(SimulationService::class.java)

    fun simulate(simulation: SimulationRequestDto): SimulationEntity {
        logger.info("Iniciando simulação para valor solicitado={}, parcelas={}, rendaMensalLiquida={}",
            simulation.valorSolicitado,
            simulation.numeroParcelas,
            simulation.rendaMensalLiquida
        )

        validateSimulationRequest(simulation)

        val monthlyInstallment = calcularParcelaPrice(simulation)
        logger.info("Parcela calculada: {}", monthlyInstallment)

        if (monthlyInstallment > simulation.rendaMensalLiquida.multiply(BigDecimal("0.30")).setScale(2, RoundingMode.HALF_UP)) {
            logger.warn("Parcela excede 30% da renda. parcela={}, limite={} ", monthlyInstallment, simulation.rendaMensalLiquida.multiply(BigDecimal("0.30")).setScale(2, RoundingMode.HALF_UP))
            throw InvalidMonthlyInstallmentException()
        }

        val simulationEntity = SimulationEntity(
            requestedAmount = simulation.valorSolicitado,
            installmentCount = simulation.numeroParcelas,
            monthlyInstallment = monthlyInstallment,
            tax = BigDecimal("0.02")
        )

        val saved = simulationRepository.save(simulationEntity)
        logger.info("Simulação salva com sucesso: id={}, simulationId={}", saved.id, saved.simulationId)
        return saved
    }

    fun existsBySimulationId(simulationId: UUID): Boolean {
        return simulationRepository.existsBySimulationId(simulationId)
    }

    private fun calcularParcelaPrice(
        simulation: SimulationRequestDto,
        taxaMensal: BigDecimal = BigDecimal("0.02")
    ): BigDecimal {
        val taxa = taxaMensal.toDouble()
        val numeroParcelas = simulation.numeroParcelas
        val denominador = 1.0 - Math.pow(1.0 + taxa, -numeroParcelas.toDouble())
        val parcela = simulation.valorSolicitado.toDouble() * taxa / denominador

        return BigDecimal.valueOf(parcela).setScale(2, RoundingMode.HALF_UP)
    }

    private fun validateSimulationRequest(simulationRequestDto: SimulationRequestDto) {
        if (simulationRequestDto.valorSolicitado <= BigDecimal.ZERO) {
            logger.error("Valor solicitado inválido: {}", simulationRequestDto.valorSolicitado)
            throw InvalidRequestedAmountException()
        }

        if (simulationRequestDto.numeroParcelas !in 6..48) {
            logger.error("Quantidade de parcelas inválida: {}", simulationRequestDto.numeroParcelas)
            throw InvalidInstallmentCountException()
        }

        if (simulationRequestDto.rendaMensalLiquida <= BigDecimal.ZERO) {
            logger.error("Renda mensal líquida inválida: {}", simulationRequestDto.rendaMensalLiquida)
            throw InvalidMonthlyIncomeException()
        }
    }
}