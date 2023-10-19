package pl.kacper.misterski.walldrill.domain

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.compose.ui.graphics.Color
import pl.kacper.misterski.walldrill.domain.interfaces.DetectColorListener


//TODO K only one analyzer with mode
class DetectColorAnalyzer(private val listener: DetectColorListener) :
    ImageAnalysis.Analyzer {

    @SuppressLint("UnsafeExperimentalUsageError")
    override fun analyze(image: ImageProxy) {
        // Convert the image to a format suitable for color analysis (e.g., Bitmap or OpenCV Mat)
        val bitmap =
            image.toBitmap() // Assuming you have a function to convert the ImageProxy to Bitmap

        // Perform color analysis on the bitmap
        val mainColor = analyzeColor(bitmap) // Implement your color analysis logic here
        Log.d("DetectColorAnalyzer", "color: $mainColor")
        // Update the detected color state variable
        listener.onColorDetected(mainColor)

        // Close the image proxy to release its resources
        image.close()
    }

    private fun analyzeColor(bitmap: Bitmap): Color {
        // Implement your color analysis logic here
        // Process the bitmap and determine the main color
        // Return the detected color as a Color object

        // Example implementation: Just return the color of the pixel at the center of the image
        val centerPixelColor = bitmap.getPixel(bitmap.width / 2, bitmap.height / 2)
        return Color(centerPixelColor)
    }
}