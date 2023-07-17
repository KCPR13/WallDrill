package pl.kacper.misterski.walldrill.ui.main

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.StateFlow
import pl.kacper.misterski.walldrill.domain.ColorAnalyzer
import pl.kacper.misterski.walldrill.ui.screens.calibration.CalibrationScreen
import pl.kacper.misterski.walldrill.ui.screens.setup.SetupScreen
import androidx.compose.ui.Modifier
import pl.kacper.misterski.walldrill.ui.screens.colordetection.ColorDetection
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun MainScreen(uiState: StateFlow<MainUiState>, colorDetectionListener: ColorAnalyzer.ColorDetectionListener) {
    val navController = rememberNavController()

    val mainUiState:MainUiState by uiState.collectAsState()

    val destination = if (mainUiState.permissionGranted) {
        "colorDetection"
    } else {
        "setup"
    }

    NavHost(navController = navController, startDestination = destination) {
        composable("calibration") { CalibrationScreen(colorDetectionListener) }
        composable("colorDetection") { ColorDetection(Modifier) }
        composable("setup") { SetupScreen(Modifier) }
    }

}