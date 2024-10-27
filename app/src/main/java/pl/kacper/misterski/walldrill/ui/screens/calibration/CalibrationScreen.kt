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

import android.graphics.Rect
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import kotlinx.coroutines.launch
import pl.kacper.misterski.walldrill.R
import pl.kacper.misterski.walldrill.domain.TestColorAnalyzer
import pl.kacper.misterski.walldrill.ui.common.AppProgress
import pl.kacper.misterski.walldrill.ui.common.AppToolbar

// TODO K cleanup
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalibrationScreen(
    modifier: Modifier,
    onSettingsClick: () -> Unit = {},
    uiState: CalibrationUiState,
    analyzer: TestColorAnalyzer,
    redDotRect: Rect?,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val snackbarScope = rememberCoroutineScope()

    uiState.snackbarMessage?.let { message ->
        val snackbarMessage = stringResource(message)
        snackbarScope.launch {
            uiState.snackbarHostState.showSnackbar(
                message = snackbarMessage,
                withDismissAction = true,
            )
            onSettingsClick.invoke()
            // TODO  dismiss the screen when the snackbar finishes
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = {
            SnackbarHost(hostState = uiState.snackbarHostState)
        },
        topBar = {
            AppToolbar(
                R.string.calibration,
                Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                scrollBehavior,
                onBackPressedClick = {
                    onSettingsClick.invoke()
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
                if (uiState.progress) {
                    AppProgress(Modifier.align(Alignment.Center))
                } else {
                    TestCameraScreen(analyzer, redDotRect)
                }
            }
        },
    )
}

@Composable
fun TestCameraScreen(
    analyzer: TestColorAnalyzer,
    redDotRect: Rect?,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        CameraPreviewWithDetection(
            analyzer,
            Modifier.fillMaxSize(),
        )
        Canvas(modifier = Modifier.fillMaxSize()) {
            redDotRect?.let { rect ->
                drawRect(
                    color = Color.Red,
                    topLeft = Offset(rect.left.toFloat(), rect.top.toFloat()),
                    size =
                        Size(
                            (rect.right - rect.left).toFloat(),
                            (rect.bottom - rect.top).toFloat(),
                        ),
                    style = Stroke(width = 4f),
                )
            }
        }
    }
}

@Composable
fun CameraPreviewWithDetection(
    testColorAnalyzer: TestColorAnalyzer,
    modifier: Modifier,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    AndroidView(
        modifier = modifier,
        factory = { ctx ->
            val previewView = PreviewView(ctx)
            val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)

            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()

                // Konfiguracja Preview
                val preview =
                    Preview.Builder().build().apply {
                        setSurfaceProvider(previewView.surfaceProvider)
                    }

                // Konfiguracja ImageAnalysis
                val imageAnalyzer =
                    ImageAnalysis
                        .Builder()
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build()
                        .also {
                            it.setAnalyzer(
                                ContextCompat.getMainExecutor(ctx),
                                testColorAnalyzer,
                            )
                        }

                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    cameraSelector,
                    preview,
                    imageAnalyzer,
                )
            }, ContextCompat.getMainExecutor(ctx))

            previewView
        },
    )
}

// @PreviewLightDark
// @Composable TODO K setup
// fun CalibrationScreenPreview() {
//    WallDrillTheme {
//        CalibrationScreen(
//            modifier = Modifier,
//            onSettingsClick = {},
//            uiState = CalibrationUiState(),
//            analyzer = ColorAnalyzer(AnalyzerMode.COLOR_DETECTION),
//        )
//    }
// }
