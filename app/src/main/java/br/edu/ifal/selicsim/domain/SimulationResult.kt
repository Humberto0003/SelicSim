package br.edu.ifal.selicsim.domain

data class SimulationResult (
    val finalAmount: Double,
    val totalInvested: Double,
    val totalInterest: Double,
    val monthlyRate: Double,
    val monthlyBalances: List<Double>
)