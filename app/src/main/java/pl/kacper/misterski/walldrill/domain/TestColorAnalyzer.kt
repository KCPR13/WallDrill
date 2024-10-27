package pl.kacper.misterski.walldrill.domain

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageFormat
import android.graphics.Rect
import android.graphics.YuvImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import pl.kacper.misterski.walldrill.di.ApplicationScope
import pl.kacper.misterski.walldrill.di.BackgroundDispatcher
import java.io.ByteArrayOutputStream

class TestColorAnalyzer(
    @ApplicationScope private val applicationScope: CoroutineScope,
    @BackgroundDispatcher private val backgroundDispatcher: CoroutineDispatcher,
) : ImageAnalysis.Analyzer {
    private val scaleFactor: Float = 50f // przykładowa wartość pikseli na cm dla referencyjnej odległości

    private val _redDot = MutableSharedFlow<Rect>()
    val redDot =
        _redDot.shareIn(
            applicationScope,
            SharingStarted.Eagerly,
            1,
        )

    override fun analyze(imageProxy: ImageProxy) {
        applicationScope.launch(backgroundDispatcher) {
            val buffer = imageProxy.planes[0].buffer
            val data = ByteArray(buffer.remaining())
            buffer.get(data)

            val yuvImage = YuvImage(data, ImageFormat.NV21, imageProxy.width, imageProxy.height, null)
            val out = ByteArrayOutputStream()
            yuvImage.compressToJpeg(Rect(0, 0, imageProxy.width, imageProxy.height), 100, out)
            val bitmap = BitmapFactory.decodeByteArray(out.toByteArray(), 0, out.size())

            // Wyszukiwanie czerwonej kropki w bitmapie
            val redRect = findRedDot(bitmap)
            if (redRect != null) {
                applicationScope.launch {
                    _redDot.emit(redRect)
                }
            }

            imageProxy.close()
        }
    }

    private fun findRedDot(bitmap: Bitmap): Rect? {
        val width = bitmap.width
        val height = bitmap.height
        val minDotSizePixels = (scaleFactor * 1).toInt() // Minimalny rozmiar czerwonej kropki o średnicy 1 cm

        val visited = mutableSetOf<Pair<Int, Int>>()
        val redRegions = mutableListOf<Rect>()

        for (y in 0 until height) {
            for (x in 0 until width) {
                if (Pair(x, y) !in visited) {
                    val pixel = bitmap.getPixel(x, y)
                    val red = (pixel shr 16) and 0xFF
                    val green = (pixel shr 8) and 0xFF
                    val blue = pixel and 0xFF

                    // Warunek wykrywania czerwonego koloru
                    if (red > 200 && green < 100 && blue < 100) {
                        val redRegion = findConnectedRedRegion(bitmap, x, y, visited)
                        if (redRegion.width() >= minDotSizePixels && redRegion.height() >= minDotSizePixels) {
                            redRegions.add(redRegion)
                        }
                    }
                }
            }
        }

        // Znalezienie największej kropki, jeśli istnieje
        return redRegions.maxByOrNull { it.width() * it.height() }
    }

    private fun findConnectedRedRegion(
        bitmap: Bitmap,
        startX: Int,
        startY: Int,
        visited: MutableSet<Pair<Int, Int>>,
    ): Rect {
        val queue = ArrayDeque<Pair<Int, Int>>()
        queue.add(Pair(startX, startY))
        visited.add(Pair(startX, startY))

        var minX = startX
        var minY = startY
        var maxX = startX
        var maxY =             startY

        while (queue.isNotEmpty()) {
            val (x, y) = queue.removeFirst()
            val neighbors =
                listOf(
                    Pair(x + 1, y),
                    Pair(x - 1, y),
                    Pair(x, y + 1),
                    Pair(x, y - 1),
                )

            for ((nx, ny) in neighbors) {
                if (nx in 0 until bitmap.width && ny in 0 until bitmap.height && Pair(nx, ny) !in visited) {
                    val pixel = bitmap.getPixel(nx, ny)
                    val red = (pixel shr 16) and 0xFF
                    val green = (pixel shr 8) and 0xFF
                    val blue = pixel and 0xFF

                    if (red > 200 && green < 100 && blue < 100) {
                        queue.add(Pair(nx, ny))
                        visited.add(Pair(nx, ny))

                        minX = minOf(minX, nx)
                        minY = minOf(minY, ny)
                        maxX = maxOf(maxX, nx)
                        maxY = maxOf(maxY, ny)
                    }
                }
            }
        }

        return Rect(minX, minY, maxX, maxY)
    }
}
