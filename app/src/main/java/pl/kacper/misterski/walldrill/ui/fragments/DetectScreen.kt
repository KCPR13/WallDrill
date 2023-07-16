package pl.kacper.misterski.walldrill.ui.fragments

import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.kacper.misterski.walldrill.ui.CameraPreview

@Composable
fun DetectScreen(modifier: Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Detect the color",
                fontSize = 24.sp,
                modifier = Modifier.padding(16.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .aspectRatio(3 / 4f)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
//                CameraPreview(
//                    modifier=,
//                    colorDetectionListener = ,
//
//                )

                Circle(Modifier.padding(8.dp))
            }

            Button(
                onClick = {
                   // detectColor()
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Select")
            }
        }
    }
}


fun detectColor(circleX: Float, circleY: Float, cameraPreviewBitmap: Bitmap?,cameraPreviewHeight: Float,cameraPreviewWidth: Float) {
    // Retrieve the camera preview frame

    // Calculate the color within the small circle
    val color: Int = getPixelColor(cameraPreviewBitmap, circleX, circleY,cameraPreviewHeight, cameraPreviewWidth)

    // Process the color value
    val red: Int = Color.red(color)
    val green: Int = Color.green(color)
    val blue: Int = Color.blue(color)

    // Do something with the color values
    // For example, display the selected color or perform further analysis
    // You can use the red, green, and blue values to represent and work with the color

    // Optionally, you can convert the RGB values to a hexadecimal color code
    val hexColor: String = String.format("#%02X%02X%02X", red, green, blue)

    // Output the color information
    Log.d("ColorDetection", "Selected Color: $hexColor")
}

fun getPixelColor(bitmap: Bitmap?, x: Float, y: Float,cameraPreviewHeight: Float,cameraPreviewWidth: Float): Int {
    if (bitmap == null) return Color.TRANSPARENT

    val scaledX = (x * bitmap.width / cameraPreviewWidth).toInt()
    val scaledY = (y * bitmap.height / cameraPreviewHeight).toInt()

    return bitmap.getPixel(scaledX, scaledY)
}


@Composable
fun Circle(modifier: Modifier) {
    Canvas(modifier = modifier) {
        drawCircle(
            color = androidx.compose.ui.graphics.Color.Cyan, // Orange color
            radius = 50.dp.toPx(),
            center = Offset(size.width / 2, size.height / 2)
        )
    }
}