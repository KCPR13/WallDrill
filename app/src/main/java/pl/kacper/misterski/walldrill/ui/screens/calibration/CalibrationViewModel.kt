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

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.kacper.misterski.walldrill.R
import pl.kacper.misterski.walldrill.core.BaseViewModel
import pl.kacper.misterski.walldrill.core.di.AimAnalyzer
import pl.kacper.misterski.walldrill.domain.ColorAnalyzer
import javax.inject.Inject

@HiltViewModel
class CalibrationViewModel
@Inject
constructor(
    @AimAnalyzer val colorAnalyzer: ColorAnalyzer,
) : BaseViewModel() {
    private val _uiState = MutableStateFlow(CalibrationUiState())
    val uiState = _uiState.asStateFlow()

    init {
        initAnalyzer()
    }
// TODO K cleanup

    private fun initAnalyzer() {
        Log.d(tag, "initAnalyzer")
        viewModelScope.launch {
            // _uiState.update { it.showProgress() }
//                colorRepository.getSelectedColor()
//                    .onEach { color ->
//                        color?.let { colorNotNull ->
            colorAnalyzer.init(colorToDetect = null) { analyzerResult ->
                _uiState.update {
                    CalibrationUiState(
                        rect = analyzerResult.rect,
                        detectedPoints =
                        analyzerResult
                            .detectedPoints,
                        width = analyzerResult.width,
                        hight = analyzerResult.height,
                        rotationDegrees = analyzerResult.rotationDegrees,
                    )
                }
            }
            //  _uiState.update { it.hideProgress() }
        } ?: kotlin.run {
            _uiState.update { it.showError(R.string.no_color_selected) }
        }
//                    }
//                    .catch { error ->
//                        Log.e(tag, "initAnalyzer error: ${error.message}")
//                        error.printStackTrace()
//                        _uiState.update { it.showError(R.string.no_color_selected) }
//                    }
//                    .collect()
    }

    fun calculateCircleRadius(points: List<Pair<Float, Float>>): Float {
        val centerX = points.map { it.first }.average()
        val centerY = points.map { it.second }.average()

        val distances =
            points.map { (x, y) ->
                val dx = x - centerX
                val dy = y - centerY
                Math.sqrt((dx * dx + dy * dy).toDouble()).toFloat()
            }

        // Return the average distance as the radius
        return distances.average().toFloat()
    }

    fun calculateScaleFactor(
        circleRadius: Float,
        desiredRectangleSize: Float,
    ): Float {
        // Adjust the scaling factor based on the ratio between the circle radius and the desired rectangle size
        return desiredRectangleSize / circleRadius
    }
}
