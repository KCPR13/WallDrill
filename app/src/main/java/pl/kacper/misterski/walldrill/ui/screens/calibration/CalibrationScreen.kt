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
import androidx.camera.core.CameraSelector
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import org.opencv.core.Rect
import pl.kacper.misterski.walldrill.R
import pl.kacper.misterski.walldrill.ui.AppNavigation
import pl.kacper.misterski.walldrill.ui.CameraPreview
import pl.kacper.misterski.walldrill.ui.common.AppProgress
import pl.kacper.misterski.walldrill.ui.common.AppToolbar

// TODO K cleanup
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalibrationScreen(
    modifier: Modifier,
    viewModel: CalibrationViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val calibrationUiState by viewModel.uiState.collectAsState()
    val snackbarScope = rememberCoroutineScope()

    calibrationUiState.snackbarMessage?.let { message ->
        val snackbarMessage = stringResource(message)
        snackbarScope.launch {
            calibrationUiState.snackbarHostState.showSnackbar(
                message = snackbarMessage,
                withDismissAction = true,
            )
            navController.navigate(AppNavigation.SETTINGS)
            // TODO  dismiss the screen when the snackbar finishes
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = {
            SnackbarHost(hostState = calibrationUiState.snackbarHostState)
        },
        topBar = {
            AppToolbar(
                R.string.calibration,
                Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                scrollBehavior,
                onBackPressedClick = {
                    navController.navigate(AppNavigation.SETTINGS)
                },
            )
        },
        content = { paddingValues ->
            BoxWithConstraints(
                modifier =
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
            ) {
                if (calibrationUiState.progress) {
                    AppProgress(Modifier.align(Alignment.Center))
                } else {
                    CameraPreview(
                        modifier = Modifier.fillMaxSize(),
                        analyzer = viewModel.colorAnalyzer,
                        cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA,
                    )

                    val points =
                        calibrationUiState.detectedPoints.map {
                            Pair(
                                it.first.toFloat(),
                                it.second.toFloat
                                    (),
                            )
                        }

                    Log.d("Kacpur", "detected points: ${points.size}")

                    val rect = calibrationUiState.rect
                    if (rect != null) {
                        val viewWidth = constraints.maxWidth
                        val viewHeight = constraints.maxHeight
                        val rotationDegrees = calibrationUiState.rotationDegrees
                        DisplayRectangle(
                            Modifier
                                .fillMaxSize(),
                            rect,
                            calibrationUiState.width,
                            calibrationUiState.hight,
                            viewWidth,
                            viewHeight,
                            rotationDegrees,
                        )
                    }
                }
            }
        },
    )
}

fun transformCoordinates(
    rect: Rect,
    imageWidth: Int,
    imageHeight: Int,
    rotationDegrees: Int,
): Rect {
    when (rotationDegrees) {
        90 -> {
            val newX = imageHeight - rect.y - rect.height
            val newY = rect.x
            val newWidth = rect.height
            val newHeight = rect.width
            return Rect(newX, newY, newWidth, newHeight)
        }
        180 -> {
            val newX = imageWidth - rect.x - rect.width
            val newY = imageHeight - rect.y - rect.height
            return Rect(newX, newY, rect.width, rect.height)
        }
        270 -> {
            val newX = rect.y
            val newY = imageWidth - rect.x - rect.width
            val newWidth = rect.height
            val newHeight = rect.width
            return Rect(newX, newY, newWidth, newHeight)
        }
        else -> return rect // 0 lub 360 stopni
    }
}

fun invertYCoordinate(
    rect: Rect,
    imageHeight: Int,
): Rect {
    val newX = rect.x
    // Odwracamy współrzędne Y, aby pasowały do orientacji ekranu
    val newY = imageHeight - rect.y - rect.height
    val newWidth = rect.width
    val newHeight = rect.height

    return Rect(newX, newY, newWidth, newHeight)
}

fun scaleRect(
    rect: Rect,
    scaleX: Float,
    scaleY: Float,
): Rect {
    val newX = (rect.x * scaleX)
    val newY = (rect.y * scaleY)
    val newWidth = (rect.width * scaleX)
    val newHeight = (rect.height * scaleY)

    return Rect(newX.toInt(), newY.toInt(), newWidth.toInt(), newHeight.toInt())
}

@Composable
fun DisplayRectangle(
    modifier: Modifier,
    rect: Rect,
    originalImageWidth: Int,
    originalImageHeight: Int,
    viewWidth: Int,
    viewHeight: Int,
    rotationDegrees: Int,
) {
    val scaleX = viewWidth.toFloat() / originalImageWidth.toFloat()
    val scaleY = viewHeight.toFloat() / originalImageHeight.toFloat()

    val transformed =
        scaleRect(
            //  invertYCoordinate(
            transformCoordinates(rect, originalImageWidth, originalImageHeight, rotationDegrees),
            // originalImageHeight,
            // ),
            scaleX,
            scaleY,
        )

    Canvas(modifier = modifier) {
        drawRect(
            color = Color.Red,
            topLeft = Offset(transformed.x.toFloat(), transformed.y.toFloat()),
            size = Size(transformed.width.toFloat(), transformed.height.toFloat()),
            style = Stroke(width = 5f),
        )
    }
}

// @Composable
// fun DisplayRectangle(
//    modifier: Modifier,
//    rect: Rect,
//    originalImageWidth: Int,
//    originalImageHeight: Int,
//    viewWidth: Int,
//    viewHeight: Int,
// ) {
//    val scaleX = viewWidth / originalImageWidth
//    val scaleY = viewHeight / originalImageWidth
//
//    val transformed =
//        scaleRect(
//            invertYCoordinate(
//                transformCoordinates(
//                    rect,
//                    originalImageWidth,
//                    originalImageHeight,
//                ),
//                originalImageHeight,
//            ),
//            scaleX,
//            scaleY,
//        )
//    Canvas(modifier = modifier) {
//        drawRect(
//            color = Color.Red,
//            topLeft = Offset(transformed.x.toFloat(), transformed.y.toFloat()),
//            size = Size(transformed.width.toFloat(), transformed.height.toFloat()),
//            style = Stroke(width = 5f),
//        )
//    }
// }
@Preview
@Composable
fun CalibrationScreenPreview() {
    MaterialTheme {
        CalibrationScreen(
            modifier = Modifier,
            navController = rememberNavController(),
        )
    }
}
