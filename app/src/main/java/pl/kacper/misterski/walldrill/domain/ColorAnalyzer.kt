package pl.kacper.misterski.walldrill.domain

import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.compose.ui.graphics.Color
import javax.inject.Inject
import kotlin.experimental.and


class ColorAnalyzer @Inject constructor(): ImageAnalysis.Analyzer {

     private var colorDetectionListener: ColorDetectionListener? = null
     private var colorToDetect: Color? = null

     fun init(colorDetectionListener: ColorDetectionListener, colorToDetect: Color){
         this.colorDetectionListener = colorDetectionListener
         this.colorToDetect = colorToDetect
     }

     fun dispose(){ // TODO K usage
         colorDetectionListener = null
     }


     override fun analyze(image: ImageProxy) {
         val colorToDetect = colorToDetect ?: return // Exit early if colorToDetect is null

         val buffer = image.planes[0].buffer
         val data = ByteArray(buffer.remaining())
         buffer.get(data)

         val stride = image.planes[0].rowStride
         val width = image.width
         val height = image.height

         var detectedPixelsCount = 0
         val detectedLocations = mutableListOf<Pair<Int, Int>>()

         for (row in 0 until height) {
             for (col in 0 until width) {
                 val pixel = (data[row * stride + col] and 0xFF.toByte()).toInt()
                 val red = (pixel shr 16) and 0xFF
                 val green = (pixel shr 8) and 0xFF
                 val blue = pixel and 0xFF
                 val color = Color(
                     red = red,
                     green = green,
                     blue = blue
                 )
                 if (isColorDetected(color, colorToDetect)) {
                     detectedPixelsCount++
                     detectedLocations.add(Pair(col, row))
                 }
             }
         }
         Log.d("ColorAnalyzer", "detectedPoints: ${detectedLocations.size}")

         val isColorDetected = detectedPixelsCount > (width * height) / 10 // Adjust this value to control the detection sensitivity

         image.close()

         // Notify the activity when the color is detected and provide the detected locations
         colorDetectionListener?.onColorDetected(isColorDetected, detectedLocations)
     }
     private fun isColorDetected(pixelColor: Color, colorToDetect: Color): Boolean {
         // Define your color detection criteria here
         // For example, you can compare RGB values
         // or use other properties like luminance, etc.
         // For simplicity, this example only compares RGB values.

         val minRed = colorToDetect.red - 30
         val maxRed = colorToDetect.red + 30

         val minGreen = colorToDetect.green - 30
         val maxGreen = colorToDetect.green + 30

         val minBlue = colorToDetect.blue - 30
         val maxBlue = colorToDetect.blue + 30

         return (pixelColor.red in minRed..maxRed &&
                 pixelColor.green in minGreen..maxGreen &&
                 pixelColor.blue in minBlue..maxBlue)
     }
     interface ColorDetectionListener {
         fun onColorDetected(isDetected: Boolean, detectedLocations: List<Pair<Int, Int>>)
     }
}