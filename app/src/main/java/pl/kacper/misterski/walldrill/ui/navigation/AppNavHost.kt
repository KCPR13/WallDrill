package pl.kacper.misterski.walldrill.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import pl.kacper.misterski.walldrill.ui.common.animatedDestination
import pl.kacper.misterski.walldrill.ui.screens.aim.AimScreen
import pl.kacper.misterski.walldrill.ui.screens.calibration.CalibrationScreen
import pl.kacper.misterski.walldrill.ui.screens.calibration.CalibrationViewModel
import pl.kacper.misterski.walldrill.ui.screens.colordetection.ColorDetection
import pl.kacper.misterski.walldrill.ui.screens.colordetection.ColorDetectionViewModel
import pl.kacper.misterski.walldrill.ui.screens.colors.ColorsScreen
import pl.kacper.misterski.walldrill.ui.screens.colors.ColorsViewModel
import pl.kacper.misterski.walldrill.ui.screens.folder.FolderScreen
import pl.kacper.misterski.walldrill.ui.screens.settings.SettingsScreen
import pl.kacper.misterski.walldrill.ui.screens.settings.SettingsViewModel
import pl.kacper.misterski.walldrill.ui.screens.setup.SetupScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    showBottomBar: () -> Unit,
    startDestination: String = NavigationItem.Setup.route,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        animatedDestination(NavigationItem.Setup.route) {
            SetupScreen(modifier = modifier)
        }
        animatedDestination(NavigationItem.Aim.route) {
            AimScreen(
                modifier = modifier,
                onFolderClick = {
                    navController.navigateUp()
                    showBottomBar.invoke()
                },
            )
        }
        animatedDestination(NavigationItem.Calibration.route) {
            val viewModel: CalibrationViewModel = hiltViewModel()
            val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
            val redDot = viewModel.redDot.collectAsStateWithLifecycle().value
            CalibrationScreen(
                modifier = modifier,
                onSettingsClick = {
                    navController.navigate(NavigationItem.Settings.route)
                },
                uiState = uiState,
                analyzer = viewModel.colorAnalyzer,
                redDotRect = redDot,
            )
        }
        animatedDestination(NavigationItem.ColorDetection.route) {
            val viewModel: ColorDetectionViewModel = hiltViewModel()
            val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

            ColorDetection(
                modifier = modifier,
                onColorsClick = {
                    navController.navigate(NavigationItem.Colors.route)
                },
                onSaveColor = viewModel::saveColor,
                uiState = uiState,
                colorAnalyzer = viewModel.colorAnalyzer,
            )
        }
        animatedDestination(NavigationItem.Settings.route) {
            val viewModel: SettingsViewModel = hiltViewModel()
            val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
            SettingsScreen(
                modifier = modifier,
                onColorsClick = {
                    navController.navigate(NavigationItem.Colors.route)
                },
                onCalibrationClick = {
                    navController.navigate(NavigationItem.Calibration.route)
                },
                uiState = uiState,
            )
        }
        animatedDestination(NavigationItem.Folder.route) {
            FolderScreen(modifier = modifier)
        }
        animatedDestination(NavigationItem.Colors.route) {
            val viewModel: ColorsViewModel = hiltViewModel()
            val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

            ColorsScreen(
                modifier = Modifier.padding(0.dp),
                onSettingsClick = {
                    navController.navigate(NavigationItem.Settings.route)
                },
                onColorDetectionClick = {
                    navController.navigate(NavigationItem.ColorDetection.route)
                },
                uiState = uiState,
                onRemoveItem = viewModel::onRemoveItem,
                onItemClick = viewModel::onItemClick,
            )
        }
    }
}
