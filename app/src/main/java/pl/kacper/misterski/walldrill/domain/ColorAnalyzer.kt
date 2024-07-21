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
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.compose.ui.graphics.Color
import org.opencv.android.Utils
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.core.MatOfPoint
import org.opencv.core.MatOfPoint2f
import org.opencv.core.Point
import org.opencv.core.Rect
import org.opencv.core.Scalar
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc
import pl.kacper.misterski.walldrill.domain.enums.AnalyzerMode
import pl.kacper.misterski.walldrill.domain.models.AnalyzerResult

class ColorAnalyzer(private val analyzerMode: AnalyzerMode) : ImageAnalysis.Analyzer {
    companion object {
        const val TAG = "ColorAnalyzer"
    }
// TODO K cleanup

    // TODO K instead of lambda hot flow
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
        val bitmap = image.toBitmap()

        val result =
            when (analyzerMode) {
                AnalyzerMode.AIM -> analyzeForAimMode(image)
                AnalyzerMode.COLOR_DETECTION -> detectColor(bitmap)
            }

        result?.let { onColorDetected?.invoke(it) }
        image.close()
    }

    private fun detectRedCircle(image: Bitmap): Rect? { // List<Pair<Int, Int>>? {
        // Convert Bitmap to Mat
        val mat = Mat()
        Utils.bitmapToMat(image, mat)

        // Preprocessing: Reduce noise with a Gaussian blur
        Imgproc.GaussianBlur(mat, mat, Size(5.0, 5.0), 0.0)

        // Convert to HSV color space
        val hsvMat = Mat()
        Imgproc.cvtColor(mat, hsvMat, Imgproc.COLOR_RGB2HSV)

//        // Fine-tune these ranges based on your specific red color
//        val lowerRed = Scalar(0.0, 100.0, 100.0) // Adjust these values
//        val upperRed = Scalar(255.0, 255.0, 255.0) // Adjust these values

//        val lowerRed = Scalar(0.0, 100.0, 100.0) // Adjust these values
//        val upperRed = Scalar(10.0, 255.0, 255.0) // Adjust these values

        val lowerRed = Scalar(0.0, 100.0, 100.0) // Adjust these values
        val upperRed = Scalar(255.0, 255.0, 255.0) // Adjust these values

        // Threshold the HSV image to get only red colors
        val mask = Mat()
        Core.inRange(hsvMat, lowerRed, upperRed, mask)

        // Further noise reduction with morphological opening (remove small objects from the foreground)
        Imgproc.morphologyEx(mask, mask, Imgproc.MORPH_OPEN, Mat(), Point(-1.0, -1.0), 2)

        // Find contours
        val contours = ArrayList<MatOfPoint>()
        val hierarchy = Mat()
        Imgproc.findContours(mask, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE)

        // Filter for circular shapes and a certain size
        val circleContours =
            contours.filter { contour ->
                val contour2f = MatOfPoint2f(*contour.toArray())
                val center = Point()
                val radius = FloatArray(1)
                Imgproc.minEnclosingCircle(contour2f, center, radius)
                val circularity =
                    (
                        4 * Math.PI *
                            Imgproc.contourArea(
                                contour,
                            )
                        ) / (Imgproc.arcLength(contour2f, true) * Imgproc.arcLength(contour2f, true))
                circularity > 0.8 && radius[0] > 10 // Adjust the minimum radius as needed
            }

        // Get the largest circle based on area
        return circleContours.maxByOrNull { Imgproc.contourArea(it) }?.let { contour ->
            val rect = Imgproc.boundingRect(contour)
            rect
            //            listOf(
//                Pair(rect.x, rect.y), // Top-left corner
//                Pair(rect.x + rect.width, rect.y), // Top-right corner
//                Pair(rect.x + rect.width, rect.y + rect.height), // Bottom-right corner
//                Pair(rect.x, rect.y + rect.height), // Bottom-left corner
//            )
        }
    }

    private fun detectColor(bitmap: Bitmap): AnalyzerResult {
        val x = bitmap.width / 2
        val y = bitmap.height / 2
        val centerPixelColor = bitmap.getPixel(x, y)
        return AnalyzerResult(Color(centerPixelColor), listOf(Pair(x, y)))
    }

    // TODO to implement
    private fun analyzeForAimMode(image: ImageProxy): AnalyzerResult? {
        val bitmap = image.toBitmap()
        val result = detectRedCircle(bitmap)

//        objectDetector.process(inputImage)
//            .addOnSuccessListener { detectedObjects ->
//                for (detectedObject in detectedObjects) {
//                    val boundingBox = detectedObject.boundingBox
//                    val trackingId = detectedObject.trackingId
//                    for (label in detectedObject.labels) {
//                        val text = label.text
//                    }
//                }
//            }
//            .addOnFailureListener { e ->
//                // Task failed with an exception
//                // ...
//            }

        // Log.d(TAG, "detected points: ${result?.size}")
        return AnalyzerResult(Color.Yellow, emptyList(), result, image.width, image.height, image.imageInfo.rotationDegrees)
    }
}
