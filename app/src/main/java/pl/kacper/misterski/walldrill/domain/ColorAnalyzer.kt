package pl.kacper.misterski.walldrill.domain

import android.graphics.Bitmap
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.compose.ui.graphics.Color
import pl.kacper.misterski.walldrill.domain.enums.AnalyzerMode
import pl.kacper.misterski.walldrill.domain.exceptions.ColorDetectionException
import pl.kacper.misterski.walldrill.domain.interfaces.ColorListener
import pl.kacper.misterski.walldrill.domain.models.AnalyzerResult


class ColorAnalyzer(private val analyzerMode: AnalyzerMode) : ImageAnalysis.Analyzer {

    private var colorListener: ColorListener? = null
    private var colorToDetect: Color? = null
    fun init(
        colorListener: ColorListener,
        colorToDetect: Color?=null
    ) {
        this.colorListener = colorListener
        this.colorToDetect = colorToDetect
    }

    fun dispose() {
        colorListener = null
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
        colorListener?.onColorDetected(result)
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

    @Throws(ColorDetectionException::class)
    private fun analyzeForAimMode(bitmap: Bitmap): AnalyzerResult{
        val colorToDetect = colorToDetect ?: throw ColorDetectionException() //TODO K handle

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
        Log.d("ColorAnalyzer", "detectedPoints: ${detectedLocations.size}")

        return AnalyzerResult(colorToDetect,detectedLocations)
    }
}

