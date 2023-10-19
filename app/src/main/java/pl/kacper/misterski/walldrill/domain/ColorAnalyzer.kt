package pl.kacper.misterski.walldrill.domain

import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.compose.ui.graphics.Color
import javax.inject.Inject


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

         Log.d("ColorAnalyzer", "planes: ${image.planes.size}")
         val bitmap =
             image.toBitmap()

         val width = bitmap.width
         val height = bitmap.height



         var detectedPixelsCount = 0
         val detectedLocations = mutableListOf<Pair<Int, Int>>()

         for (y in 0 until height ) {
             for (x in 0 until width ) {
                 val pixel = bitmap.getPixel(x,y)
                 val color = Color(pixel)

                 if (isColorDetected(color, colorToDetect)) {
                     detectedPixelsCount++
                     detectedLocations.add(Pair(x, y))
                 }
             }
         }
         Log.d("ColorAnalyzer", "detectedPoints: ${detectedLocations.size}")

         val isColorDetected = detectedPixelsCount > (width * height) / 2 // Adjust this value to control the detection sensitivity

         image.close()

         // Notify the activity when the color is detected and provide the detected locations
         colorDetectionListener?.onColorDetected(isColorDetected, detectedLocations)
     }
     private fun isColorDetected(pixelColor: Color, colorToDetect: Color): Boolean {
         return pixelColor.red  == colorToDetect.red &&
                 pixelColor.green == colorToDetect.green &&
                 pixelColor.blue == colorToDetect.blue
     }
     interface ColorDetectionListener {
         fun onColorDetected(isDetected: Boolean, detectedLocations: List<Pair<Int, Int>>)
     }
}