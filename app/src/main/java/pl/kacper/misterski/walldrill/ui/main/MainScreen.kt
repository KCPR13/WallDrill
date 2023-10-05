package pl.kacper.misterski.walldrill.ui.main

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.StateFlow
import pl.kacper.misterski.walldrill.domain.ColorAnalyzer
import pl.kacper.misterski.walldrill.ui.AppNavigation
import pl.kacper.misterski.walldrill.ui.screens.calibration.CalibrationScreen
import pl.kacper.misterski.walldrill.ui.screens.colordetection.ColorDetection
import pl.kacper.misterski.walldrill.ui.screens.settings.SettingsScreen
import pl.kacper.misterski.walldrill.ui.screens.setup.SetupScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    uiState: StateFlow<MainUiState>,
    colorDetectionListener: ColorAnalyzer.ColorDetectionListener,
    onSettingsClick: () -> Unit, // TODO K cleanup
    onFolderClick: () -> Unit,
    onAimClick: () -> Unit

) {
    val navController = rememberNavController()

    Scaffold(
        Modifier.safeContentPadding(),
        bottomBar = { BottomBar(onSettingsClick = {
            navController.navigate(AppNavigation.SETTINGS)
        },
            onAimClick = onAimClick, onFolderClick = onFolderClick) }
    ) { paddingValues ->
        val mainUiState: MainUiState by uiState.collectAsState()

        val destination = if (mainUiState.permissionGranted) {
            AppNavigation.SETTINGS // GO TO MAIN SCREEN
        } else {
            AppNavigation.SETUP
        }

        val contentModifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding())

        NavHost(navController = navController, startDestination = destination) {
            composable(AppNavigation.CALIBRATION) {
                CalibrationScreen(
                    contentModifier,
                    colorDetectionListener
                )
            }
            composable(AppNavigation.COLOR_DETECTION) { ColorDetection(contentModifier) }
            composable(AppNavigation.SETUP) { SetupScreen(contentModifier) }
            composable(AppNavigation.SETTINGS) { SettingsScreen(contentModifier) }

        }

    }






}

