
package br.edu.ifal.selicsim.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import br.edu.ifal.selicsim.ui.screen.SelicCalculatorScreen
import br.edu.ifal.selicsim.ui.screen.SimulationHistoryScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            NavigationBar {

                NavigationBarItem(
                    selected = currentRoute == "calculator",
                    onClick = {
                        navController.navigate("calculator") {
                            launchSingleTop = true
                        }
                    },
                    icon = { Icon(Icons.Default.Calculate, null) },
                    label = { Text("Simulador") }
                )

                NavigationBarItem(
                    selected = currentRoute == "history",
                    onClick = {
                        navController.navigate("history") {
                            launchSingleTop = true
                        }
                    },
                    icon = { Icon(Icons.Default.History, null) },
                    label = { Text("Histórico") }
                )
            }
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = "calculator",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("calculator") {
                SelicCalculatorScreen()
            }

            composable("history") {
                SimulationHistoryScreen()
            }
        }
    }
}
