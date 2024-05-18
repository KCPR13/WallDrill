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
package pl.kacper.misterski.walldrill.ui.screens.calibration

import androidx.annotation.StringRes
import androidx.compose.material3.SnackbarHostState
import org.opencv.core.Rect
// TODO K cleanup

data class CalibrationUiState(
    val progress: Boolean = false,
    val rect: Rect? = null,
    val detectedPoints: List<Pair<Int, Int>> = mutableListOf(),
    val snackbarHostState: SnackbarHostState = SnackbarHostState(),
    @StringRes val snackbarMessage: Int? = null,
    val width: Int = 0,
    val hight: Int = 0,
    val rotationDegrees: Int = 0,
) {
    fun showProgress() = this.copy(progress = true)

    fun hideProgress() = this.copy(progress = false)

    fun showError(
        @StringRes message: Int,
    ) = this.copy(snackbarMessage = message)
}
