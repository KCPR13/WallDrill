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
package pl.kacper.misterski.walldrill.ui.navigation

sealed class NavigationItem(
    val route: String,
) {
    data object Calibration : NavigationItem(Screen.CALIBRATION.name)

    data object Setup : NavigationItem(Screen.SETUP.name)

    data object ColorDetection : NavigationItem(Screen.COLOR_DETECTION.name)

    data object Settings : NavigationItem(Screen.SETTINGS.name)

    data object Aim : NavigationItem(Screen.AIM.name)

    data object Folder : NavigationItem(Screen.FOLDER.name)

    data object Colors : NavigationItem(Screen.COLORS.name)
}
