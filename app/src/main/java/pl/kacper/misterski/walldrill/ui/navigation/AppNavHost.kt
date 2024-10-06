package pl.kacper.misterski.walldrill.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import pl.kacper.misterski.walldrill.ui.screens.aim.AimScreen
import pl.kacper.misterski.walldrill.ui.screens.calibration.CalibrationScreen
import pl.kacper.misterski.walldrill.ui.screens.colordetection.ColorDetection
import pl.kacper.misterski.walldrill.ui.screens.colors.ColorsScreen
import pl.kacper.misterski.walldrill.ui.screens.folder.FolderScreen
import pl.kacper.misterski.walldrill.ui.screens.settings.SettingsScreen
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
            CalibrationScreen(modifier = modifier, onSettingsClick = {
                navController.navigate(NavigationItem.Settings.route)
            })
        }
        composable(NavigationItem.ColorDetection.route) {
            ColorDetection(modifier = modifier, onColorsClick = {
                navController.navigate(NavigationItem.Colors.route)
            })
        }
        composable(NavigationItem.Settings.route) {
            SettingsScreen(
                modifier = modifier,
                onColorsClick = {
                    navController.navigate(NavigationItem.Colors.route)
                },
                onCalibrationClick = {
                    navController.navigate(NavigationItem.Calibration.route)
                },
            )
        }
        composable(NavigationItem.Folder.route) {
            FolderScreen(modifier = modifier)
        }
        composable(NavigationItem.Colors.route) {
            ColorsScreen(
                modifier = modifier,
                onSettingsClick = {
                    navController.navigate(NavigationItem.Settings.route)
                },
                onColorDetectionClick = {
                    navController.navigate(NavigationItem.ColorDetection.route)
                },
            )
        }
    }
}
