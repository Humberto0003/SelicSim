package br.edu.ifal.selicsim.domain

import kotlin.math.pow

class InvestmentCalculator {

    fun calculate(
        initialAmount: Double,
        monthlyContribution: Double,
        months: Int,
        annualRate: Double
    ): SimulationResult {

        val monthlyRate = (1 + annualRate / 100).pow(1.0 / 12.0) - 1

        var balance = initialAmount
        val monthlyBalances = mutableListOf<Double>()

        repeat(months) {
            balance *= (1 + monthlyRate)
            balance += monthlyContribution

            monthlyBalances.add(balance)
        }

        val totalInvested = initialAmount + monthlyContribution * months

        return SimulationResult(
            finalAmount = balance,
            totalInvested = totalInvested,
            totalInterest = balance - totalInvested,
            monthlyRate = monthlyRate,
            monthlyBalances = monthlyBalances
        )
    }
}
