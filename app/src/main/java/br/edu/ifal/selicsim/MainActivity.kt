package br.edu.ifal.selicsim

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import br.edu.ifal.selicsim.navigation.AppNavigation
import br.edu.ifal.selicsim.ui.screen.SelicCalculatorScreen
import br.edu.ifal.selicsim.ui.theme.SelicSimTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SelicSimTheme {
                AppNavigation()
                }
            }
        }
    }


