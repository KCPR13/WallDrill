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
package pl.kacper.misterski.walldrill.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.ImageFormat
import android.hardware.camera2.CameraCharacteristics
import android.util.Log
import android.util.Size
import android.view.ViewGroup
import androidx.annotation.OptIn
import androidx.camera.camera2.interop.Camera2CameraInfo
import androidx.camera.camera2.interop.ExperimentalCamera2Interop
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import kotlinx.coroutines.launch
import pl.kacper.misterski.walldrill.domain.extensions.getCameraProvider
import java.util.concurrent.Executors

@Composable
fun CameraPreview(
    modifier: Modifier = Modifier,
    analyzer: ImageAnalysis.Analyzer?,
    scaleType: PreviewView.ScaleType = PreviewView.ScaleType.FILL_CENTER,
    cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA,
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val lifecycleOwner =
        LocalLifecycleOwner.current

    DisposableEffect(Unit) {
        val cameraProvider = cameraProviderFuture.get()
        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

        // Get camera characteristics
        val cameraCharacteristics = getCameraCharacteristics(context, cameraSelector)

        if (cameraCharacteristics != null) {
            val streamConfigurationMap =
                cameraCharacteristics.get(
                    CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP,
                )
            val resolutions = streamConfigurationMap?.getOutputSizes(ImageFormat.JPEG)

            // Log the available resolutions
            resolutions?.forEach { size ->
                Log.d("CameraCapture", "Resolution: ${size.width}x${size.height}")
            }
        }

        onDispose {
            cameraProvider.unbindAll()
        }
    }

    AndroidView(
        modifier = modifier,
        factory = { context ->
            val previewView =
                PreviewView(context).apply {
                    this.scaleType = scaleType
                    layoutParams =
                        ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT,
                        )
                }

            val cameraExecutor = Executors.newSingleThreadExecutor()

            val imageAnalyzer =
                ImageAnalysis
                    .Builder()
                    .setBackpressureStrategy(STRATEGY_KEEP_ONLY_LATEST)
                    .setTargetResolution(Size(1080, 1835))
                    .build()
                    .also {
                        if (analyzer != null) {
                            it.setAnalyzer(cameraExecutor, analyzer)
                        }
                    }

//            // CameraX Preview UseCase
            val previewUseCase =
                androidx
                    .camera
                    .core
                    .Preview
                    .Builder()
                    .build()
                    .also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }

            coroutineScope.launch {
                val cameraProvider = context.getCameraProvider()
                try {
                    // Must unbind the use-cases before rebinding them.
                    cameraProvider.unbindAll()

                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        imageAnalyzer,
                        previewUseCase,
                    )
                } catch (ex: Exception) {
                    Log.e("CameraPreview", "Use case binding failed", ex)
                }
            }

            previewView
        },
    )
}

// TODO K NEEDED?
// Helper function to get camera characteristics
@SuppressLint("RestrictedApi")
@OptIn(ExperimentalCamera2Interop::class)
fun getCameraCharacteristics(
    context: Context,
    cameraSelector: CameraSelector,
): CameraCharacteristics? {
    val cameraProvider = ProcessCameraProvider.getInstance(context).get()

    for (cameraInfo in cameraProvider.availableCameraInfos) {
        val cameraSelectorResult = cameraSelector.filter(cameraProvider.availableCameraInfos)
        if (cameraSelectorResult.contains(cameraInfo)) {
            return Camera2CameraInfo.extractCameraCharacteristics(cameraInfo)
        }
    }
    return null
}
