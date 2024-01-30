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
package pl.kacper.misterski.walldrill.domain

import android.graphics.Bitmap
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import pl.kacper.misterski.walldrill.domain.enums.AnalyzerMode
import pl.kacper.misterski.walldrill.domain.models.AnalyzerResult
import kotlin.math.abs
import android.graphics.Color as AndroidColor

class ColorAnalyzer(private val analyzerMode: AnalyzerMode) : ImageAnalysis.Analyzer {
    companion object {
        const val TAG = "ColorAnalyzer"
    }

    private var onColorDetected: ((AnalyzerResult) -> Unit)? = null
    private var colorToDetect: Color? = null

    fun init(
        colorToDetect: Color? = null,
        onColorDetected: ((AnalyzerResult) -> Unit)?,
    ) {
        this.onColorDetected = onColorDetected
        this.colorToDetect = colorToDetect
    }

    override fun analyze(image: ImageProxy) {
        val buffer = image.planes[0].buffer
        val data = ByteArray(buffer.remaining())
        buffer.get(data)

        val bitmap = image.toBitmap()

        val result =
            when (analyzerMode) {
                AnalyzerMode.AIM -> analyzeForAimMode(bitmap)
                AnalyzerMode.DETECTION -> analyzeForDetectionMode(bitmap)
            }

        image.close()
        result?.let { onColorDetected?.invoke(it) }
    }

    // TODO K ktlint adding extra , on the param
    private fun isColorDetected(
        pixelColor: Color,
        prevPixelColor: Color?,
    ): Boolean {
//
//        return pixelColor.red == colorToDetect.red &&
//                pixelColor.green == colorToDetect.green &&
//                pixelColor.blue == colorToDetect.blue

        val threshold = 0.02f
        val argb = pixelColor.toArgb()

        // Convert Color to luminance (grayscale)
        val luminance =
            (
                0.299 * AndroidColor.red(argb) +
                    0.587 * AndroidColor.green(argb) +
                    0.114 * AndroidColor.blue(argb)
            ) / 255.0

        if (prevPixelColor == null) return false
        val prevArgb = prevPixelColor.toArgb()
        val prevLuminance =
            (
                0.299 * AndroidColor.red(prevArgb) +
                    0.587 * AndroidColor.green(prevArgb) +
                    0.114 * AndroidColor.blue(prevArgb)
            ) / 255.0

        val diff = abs(luminance - prevLuminance)

        // Check if the luminance crosses the specified threshold
        return diff > threshold && (prevPixelColor.isBlackOrWhite() && pixelColor.isBlackOrWhite())
    }

    private fun analyzeForDetectionMode(bitmap: Bitmap): AnalyzerResult {
        val x = bitmap.width / 2
        val y = bitmap.height / 2
        val centerPixelColor = bitmap.getPixel(x, y)
        return AnalyzerResult(Color(centerPixelColor), listOf(Pair(x, y)))
    }

    private fun analyzeForAimMode(bitmap: Bitmap): AnalyzerResult? {
        val colorToDetect = colorToDetect ?: return null

        val width = bitmap.width
        val height = bitmap.height

        var detectedPixelsCount = 0
        val detectedLocations = mutableListOf<Pair<Int, Int>>()

        var prevPixelColor: Color? = null
        for (y in 0 until height) {
            for (x in 0 until width) {
                val pixel = bitmap.getPixel(x, y)
                val color = Color(pixel)

                if (isColorDetected(color, prevPixelColor)) {
                    detectedPixelsCount++
                    detectedLocations.add(Pair(x, y))
                }
                prevPixelColor = color
            }
        }
        Log.d(TAG, "detectedPoints: ${detectedLocations.size}")

        return AnalyzerResult(colorToDetect, detectedLocations)
    }
}

fun Color.isBlackOrWhite(
    blackThreshold: Float = 0.1f,
    whiteThreshold: Float = 0.95f,
): Boolean {
    val argb = this.toArgb()

    // Calculate luminance (grayscale)
    val luminance =
        (
            0.299 * AndroidColor.red(argb) +
                0.587 * AndroidColor.green(argb) +
                0.114 * AndroidColor.blue(argb)
        ) / 255.0

    // Check if the color is black or white based on the thresholds
    return luminance <= blackThreshold || luminance >= whiteThreshold
}
