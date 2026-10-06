package com.emprestimo.creditsystem.simulation.service

import com.meutudo.creditsystem.simulation.entity.SimulationEntity
import com.meutudo.creditsystem.simulation.exception.InvalidInstallmentCountException
import com.meutudo.creditsystem.simulation.exception.InvalidMonthlyIncomeException
import com.meutudo.creditsystem.simulation.exception.InvalidMonthlyInstallmentException
import com.meutudo.creditsystem.simulation.exception.InvalidRequestedAmountException
import com.meutudo.creditsystem.simulation.repository.SimulationRepository
import com.meutudo.creditsystem.simulation.rest.dto.SimulationRequestDto
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal

class SimulationServiceTest {

    private val simulationRepository: SimulationRepository = mockk(relaxed = true)
    private val service = SimulationService(simulationRepository)

    @Test
    fun `should calculate and save simulation when request is valid`() {
        val request = SimulationRequestDto(
            valorSolicitado = BigDecimal("20000.00"),
            numeroParcelas = 12,
            rendaMensalLiquida = BigDecimal("10000.00")
        )

        val savedEntity = SimulationEntity(
            requestedAmount = request.valorSolicitado,
            installmentCount = request.numeroParcelas,
            monthlyInstallment = BigDecimal("1895.22"),
            tax = BigDecimal("0.02")
        )

        every { simulationRepository.save(any(SimulationEntity::class)) } returns savedEntity

        val result = service.simulate(request)

        assertNotNull(result)
        assertEquals(BigDecimal("1895.22"), result.monthlyInstallment)
        assertEquals(BigDecimal("20000.00"), result.requestedAmount)
        assertEquals(12, result.installmentCount)
        verify { simulationRepository.save(any(SimulationEntity::class)) }
    }

    @Test
    fun `should throw when requested amount is zero`() {
        val request = SimulationRequestDto(
            valorSolicitado = BigDecimal.ZERO,
            numeroParcelas = 12,
            rendaMensalLiquida = BigDecimal("5000.00")
        )

        val exception = assertThrows<InvalidRequestedAmountException> {
            service.simulate(request)
        }

        assertEquals("O valor solicitado deve ser maior que zero.", exception.message)
    }

    @Test
    fun `should throw when installment count is outside range`() {
        val request = SimulationRequestDto(
            valorSolicitado = BigDecimal("20000.00"),
            numeroParcelas = 5,
            rendaMensalLiquida = BigDecimal("5000.00")
        )

        val exception = assertThrows<InvalidInstallmentCountException> {
            service.simulate(request)
        }

        assertEquals("O número de parcelas deve estar entre 6 e 48.", exception.message)
    }

    @Test
    fun `should throw when monthly income is zero`() {
        val request = SimulationRequestDto(
            valorSolicitado = BigDecimal("20000.00"),
            numeroParcelas = 12,
            rendaMensalLiquida = BigDecimal.ZERO
        )

        val exception = assertThrows<InvalidMonthlyIncomeException> {
            service.simulate(request)
        }

        assertEquals("A renda mensal líquida deve ser maior que zero.", exception.message)
    }

    @Test
    fun `should throw when installment is greater than 30 percent of monthly income`() {
        val request = SimulationRequestDto(
            valorSolicitado = BigDecimal("100000.00"),
            numeroParcelas = 12,
            rendaMensalLiquida = BigDecimal("5000.00")
        )

        val exception = assertThrows<InvalidMonthlyInstallmentException> {
            service.simulate(request)
        }

        assertEquals("A parcela gerada excede 30% da renda mensal líquida.", exception.message)
    }
}
