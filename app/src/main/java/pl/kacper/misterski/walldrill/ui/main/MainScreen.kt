package pl.kacper.misterski.walldrill.ui.main

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pl.kacper.misterski.walldrill.ui.AppNavigation
import pl.kacper.misterski.walldrill.ui.screens.aim.AimScreen
import pl.kacper.misterski.walldrill.ui.screens.calibration.CalibrationScreen
import pl.kacper.misterski.walldrill.ui.screens.colordetection.ColorDetection
import pl.kacper.misterski.walldrill.ui.screens.colors.ColorsScreen
import pl.kacper.misterski.walldrill.ui.screens.folder.FolderScreen
import pl.kacper.misterski.walldrill.ui.screens.settings.SettingsScreen
import pl.kacper.misterski.walldrill.ui.screens.setup.SetupScreen
import pl.kacper.misterski.walldrill.ui.theme.WallDrillTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel,
    navController: NavHostController

) {
    val mainUiState: MainUiState by viewModel.uiState.collectAsState()

    Scaffold(
        Modifier.safeContentPadding(),
        bottomBar = {
            AnimatedBottomBar(
                show = mainUiState.showBottomBar,
                onSettingsClick = {
                    navController.navigate(AppNavigation.SETTINGS)
                },
                onAimClick = {
                    viewModel.updateBottomBarVisibility(false)
                    navController.navigate(AppNavigation.AIM)
                },
                onFolderClick = { navController.navigate(AppNavigation.FOLDER) })
        }
    ) { paddingValues ->


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
                    navController = navController
                )
            }
            composable(AppNavigation.COLOR_DETECTION) {
                ColorDetection(
                    contentModifier,
                    navController = navController
                )
            }
            composable(AppNavigation.SETUP) { SetupScreen(contentModifier) }
            composable(AppNavigation.SETTINGS) {
                SettingsScreen(
                    contentModifier,
                    navController = navController
                )
            }
            composable(AppNavigation.FOLDER) { FolderScreen(contentModifier) }
            composable(AppNavigation.AIM) {
                AimScreen(contentModifier, navController, showBottomBar = { showBottomBar ->
                    viewModel.updateBottomBarVisibility(showBottomBar)
                })
            }
            composable(AppNavigation.COLORS) {
                ColorsScreen(
                    contentModifier,
                    navController = navController
                )
            }

        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    WallDrillTheme {
        MainScreen(viewModel =  hiltViewModel(), rememberNavController())
    }
}

