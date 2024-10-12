/*
 * Copyright 2024 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pl.kacper.misterski.walldrill.ui.main

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import pl.kacper.misterski.walldrill.ui.navigation.AppNavHost
import pl.kacper.misterski.walldrill.ui.navigation.NavigationItem
import pl.kacper.misterski.walldrill.ui.theme.WallDrillTheme

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    uiState: MainUiState,
    onAimClick: () -> Unit,
    navController: NavHostController = rememberNavController(),
) {
    Scaffold(
        modifier.safeContentPadding(),
        bottomBar = {
            AnimatedBottomBar(
                show = uiState.showBottomBar,
                onSettingsClick = {
                    navController.navigate(NavigationItem.Settings.route)
                },
                onAimClick = {
                    onAimClick.invoke()
                    navController.navigate(NavigationItem.Aim.route)
                },
                onFolderClick = { navController.navigate(NavigationItem.Folder.route) },
            )
        },
    ) { paddingValues ->

        val startDestination =
            if (uiState.permissionGranted) {
                NavigationItem.Settings.route
            } else {
                NavigationItem.Setup.route
            }

        val contentModifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding())

        AppNavHost(
            modifier = contentModifier,
            navController = navController,
            startDestination = startDestination,
        )
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    WallDrillTheme {
        MainScreen(
            uiState = MainUiState(),
            onAimClick = { },
        )
    }
}
