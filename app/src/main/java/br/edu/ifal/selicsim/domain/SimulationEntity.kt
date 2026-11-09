package br.edu.ifal.selicsim.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "simulations")
data class SimulationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val initialAmount: Double,
    val monthlyContribution: Double,
    val months: Int,
    val annualRate: Double,
    val finalAmount: Double,
    val totalInvested: Double,
    val totalInterest: Double,
    val createdAt: Long
)