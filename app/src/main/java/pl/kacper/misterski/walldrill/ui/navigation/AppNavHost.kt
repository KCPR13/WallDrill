package pl.kacper.misterski.walldrill.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
    startDestination: String = NavigationItem.Setup.route,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(NavigationItem.Setup.route) {
            SetupScreen(modifier = modifier)
        }
        composable(NavigationItem.Aim.route) {
            AimScreen(modifier = modifier, onFolderClick = {
                navController.navigate(NavigationItem.Folder.route)
            })
        }
        composable(NavigationItem.Calibration.route) {
            val viewModel: CalibrationViewModel = hiltViewModel()
            val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

            CalibrationScreen(
                modifier = modifier,
                onSettingsClick = {
                    navController.navigate(NavigationItem.Settings.route)
                },
                uiState = uiState,
                analyzer = viewModel.colorAnalyzer,
            )
        }
        composable(NavigationItem.ColorDetection.route) {
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
        composable(NavigationItem.Settings.route) {
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
        composable(NavigationItem.Folder.route) {
            FolderScreen(modifier = modifier)
        }
        composable(NavigationItem.Colors.route) {
            val viewModel: ColorsViewModel = hiltViewModel()
            val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

            ColorsScreen(
                modifier = modifier,
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
