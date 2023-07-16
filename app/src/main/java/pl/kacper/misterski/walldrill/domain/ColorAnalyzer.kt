package pl.kacper.misterski.walldrill.domain

import android.graphics.Color
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import java.lang.Math.abs
import kotlin.experimental.and


 class ColorAnalyzer(private val colorDetectionListener: ColorDetectionListener) : ImageAnalysis.Analyzer {

    companion object {
        private const val RED_THRESHOLD = 40 // Adjust this value to detect red color accurately
    }

    override fun analyze(image: ImageProxy) {
        val buffer = image.planes[0].buffer
        val data = ByteArray(buffer.remaining())
        buffer.get(data)

        val stride = image.planes[0].rowStride
        val width = image.width
        val height = image.height

        var redPixelsCount = 0
        val detectedLocations = mutableListOf<Pair<Int, Int>>()


        for (row in 0 until height) {
            for (col in 0 until width) {
                val pixel = (data[row * stride + col] and 0xFF.toByte()).toInt()
                val red = (pixel shr 16) and 0xFF
                val green = (pixel shr 8) and 0xFF
                val blue = pixel and 0xFF

                if (isRed(red, green, blue)) {
                    redPixelsCount++
                    detectedLocations.add(Pair(col, row))
                }
            }
        }
        Log.d("ColorAnalyzer", "detectedPoints: ${detectedLocations.size}")

        val isRedDetected = redPixelsCount > (width * height) / 10 // Adjust this value to control the detection sensitivity

        image.close()

        // Notify the activity when red color is detected and provide the detected locations
        colorDetectionListener.onRedColorDetected(isRedDetected, detectedLocations)

    }

     private fun isRed(red: Int, green: Int, blue: Int): Boolean {
         val hsv = FloatArray(3)
         Color.RGBToHSV(red, green, blue, hsv)

         val hue = hsv[0]
         val saturation = hsv[1]
         val value = hsv[2]

         val minHue = 230f // Adjust this value to include the desired range of red hues
         val maxHue = 20f // Adjust this value to include the desired range of red hues

         val minSaturation = 0.4f // Adjust this value to set the minimum saturation for red detection
         val maxSaturation = 1.0f // Adjust this value to set the maximum saturation for red detection

         val minValue = 0.1f // Adjust this value to set the minimum value/brightness for red detection
         val maxValue = 1.0f // Adjust this value to set the maximum value/brightness for red detection

         return (hue >= minHue || hue <= maxHue
                 && saturation >= minSaturation && saturation <= maxSaturation
                 && value >= minValue && value <= maxValue)
     }

     interface ColorDetectionListener {
         fun onRedColorDetected(isDetected: Boolean, detectedLocations: List<Pair<Int, Int>>)
     }
}