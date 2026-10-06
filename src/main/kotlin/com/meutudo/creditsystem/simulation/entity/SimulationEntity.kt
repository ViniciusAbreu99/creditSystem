package com.meutudo.creditsystem.simulation.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "simulation")
class SimulationEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "simulation_id", nullable = false, unique = true, updatable = false)
    var simulationId: UUID = UUID.randomUUID(),

    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "requested_amount", nullable = false, precision = 19, scale = 2)
    var requestedAmount: BigDecimal = BigDecimal.ZERO,

    @Column(name = "installment_count", nullable = false)
    var installmentCount: Int = 0,

    @Column(name = "monthly_installment", nullable = false, precision = 19, scale = 2)
    var monthlyInstallment: BigDecimal = BigDecimal.ZERO,

    @Column(name = "tax", nullable = false, precision = 5, scale = 4)
    var tax: BigDecimal = BigDecimal.ZERO
)
