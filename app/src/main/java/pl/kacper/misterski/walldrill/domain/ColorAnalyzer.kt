package pl.kacper.misterski.walldrill.domain

import android.graphics.Bitmap
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.compose.ui.graphics.Color
import pl.kacper.misterski.walldrill.domain.enums.AnalyzerMode
import pl.kacper.misterski.walldrill.domain.models.AnalyzerResult


class ColorAnalyzer(private val analyzerMode: AnalyzerMode) : ImageAnalysis.Analyzer {

    private val TAG = "ColorAnalyzer"

    private var onColorDetected: ((AnalyzerResult) -> Unit)? = null
    private var colorToDetect: Color? = null
    fun init(
        colorToDetect: Color?=null,
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


       val result =  when (analyzerMode) {
            AnalyzerMode.AIM -> analyzeForAimMode(bitmap)
            AnalyzerMode.DETECTION -> analyzeForDetectionMode(bitmap)
        }


        image.close()
        result?.let { onColorDetected?.invoke(it) }
    }


    private fun isColorDetected(pixelColor: Color, colorToDetect: Color): Boolean {
        return pixelColor.red == colorToDetect.red &&
                pixelColor.green == colorToDetect.green &&
                pixelColor.blue == colorToDetect.blue
    }

    private fun analyzeForDetectionMode(bitmap: Bitmap): AnalyzerResult {
        val x = bitmap.width / 2
        val y = bitmap.height / 2
        val centerPixelColor = bitmap.getPixel(x, y)
        return AnalyzerResult(Color(centerPixelColor), listOf(Pair(x,y)))
    }

    private fun analyzeForAimMode(bitmap: Bitmap): AnalyzerResult?{
        val colorToDetect = colorToDetect ?: return null

        val width = bitmap.width
        val height = bitmap.height


        var detectedPixelsCount = 0
        val detectedLocations = mutableListOf<Pair<Int, Int>>()

        for (y in 0 until height) {
            for (x in 0 until width) {
                val pixel = bitmap.getPixel(x, y)
                val color = Color(pixel)

                if (isColorDetected(color, colorToDetect)) {
                    detectedPixelsCount++
                    detectedLocations.add(Pair(x, y))
                }
            }
        }
        Log.d(TAG, "detectedPoints: ${detectedLocations.size}")

        return AnalyzerResult(colorToDetect,detectedLocations)
    }
}

