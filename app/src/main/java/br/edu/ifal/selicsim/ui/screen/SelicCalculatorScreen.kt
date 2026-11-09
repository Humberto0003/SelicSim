
package br.edu.ifal.selicsim.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import br.edu.ifal.selicsim.api.RetrofitHelper
import br.edu.ifal.selicsim.db.DatabaseHelper
import br.edu.ifal.selicsim.domain.InvestmentCalculator
import br.edu.ifal.selicsim.domain.SimulationEntity
import br.edu.ifal.selicsim.domain.SimulationResult
import kotlinx.coroutines.launch

@Composable
fun SelicCalculatorScreen() {

    var initialAmount by remember { mutableStateOf("") }
    var monthlyContribution by remember { mutableStateOf("") }
    var months by remember { mutableStateOf("") }
    var annualRate by remember { mutableStateOf("") }

    var result by remember { mutableStateOf<SimulationResult?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val calculator = InvestmentCalculator()

    val context = LocalContext.current
    val database = remember(context) {
        DatabaseHelper.getInstance(context)
    }
    val simulationDao = database.simulationDao()
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        val dados = RetrofitHelper().selicApi.getSelic()
        annualRate = dados.last().valor
    }

    Column(
        modifier = Modifier.padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "SelicSim",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Simulador de investimentos",
            style = MaterialTheme.typography.titleMedium
        )

        OutlinedTextField(
            value = initialAmount,
            onValueChange = { initialAmount = it },
            label = { Text("Valor inicial (R$)") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            ),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = monthlyContribution,
            onValueChange = { monthlyContribution = it },
            label = { Text("Aporte mensal (R$)") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            ),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = months,
            onValueChange = { months = it },
            label = { Text("Prazo (meses)") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = annualRate,
            onValueChange = { annualRate = it },
            label = { Text("Taxa anual (%)") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                val initial = initialAmount.replace(",", ".").toDoubleOrNull()
                val contribution = monthlyContribution.replace(",", ".").toDoubleOrNull()
                val period = months.toIntOrNull()
                val rate = annualRate.replace(",", ".").toDoubleOrNull()

                if (
                    initial != null &&
                    contribution != null &&
                    period != null &&
                    rate != null &&
                    initial >= 0 &&
                    contribution >= 0 &&
                    period > 0 &&
                    rate >= 0
                ) {

                    val calculatedResult = calculator.calculate(
                        initialAmount = initial,
                        monthlyContribution = contribution,
                        months = period,
                        annualRate = rate
                    )

                    result = calculatedResult
                    errorMessage = null

                    val simulation = SimulationEntity(
                        initialAmount = initial,
                        monthlyContribution = contribution,
                        months = period,
                        annualRate = rate,
                        finalAmount = calculatedResult.finalAmount,
                        totalInvested = calculatedResult.totalInvested,
                        totalInterest = calculatedResult.totalInterest,
                        createdAt = System.currentTimeMillis()
                    )

                    scope.launch {
                        simulationDao.insert(simulation)
                    }

                } else {
                    result = null
                    errorMessage = "Informe valores válidos para a simulação."
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calcular")
        }

        errorMessage?.let { message ->
            Text(
                text = message,
                color = MaterialTheme.colorScheme.error
            )
        }

        result?.let { simulation ->

            HorizontalDivider()

            Text(
                text = "Resultado da simulação",
                style = MaterialTheme.typography.titleLarge
            )

            Text("Montante final: R$ ${"%.2f".format(simulation.finalAmount)}")

            Text("Total investido: R$ ${"%.2f".format(simulation.totalInvested)}")

            Text("Juros acumulados: R$ ${"%.2f".format(simulation.totalInterest)}")
        }
    }
}