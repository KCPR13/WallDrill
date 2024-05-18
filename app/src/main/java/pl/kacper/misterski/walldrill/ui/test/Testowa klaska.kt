package pl.kacper.misterski.walldrill.ui.test

import android.util.Log
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.common.util.concurrent.ListenableFuture
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.opencv.core.Core
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.core.MatOfPoint
import org.opencv.core.MatOfPoint2f
import org.opencv.core.Rect
import org.opencv.core.Scalar
import org.opencv.imgproc.Imgproc
import pl.kacper.misterski.walldrill.ui.CameraPreview

// TODO K cleanup
fun findRedCircle(image: Mat): Rect? {
    // Konwersja obrazu do przestrzeni kolorów HSV
    Imgproc.cvtColor(image, image, Imgproc.COLOR_RGB2HSV)
    // Filtracja czerwonego koloru
    Core.inRange(
        image,
        Scalar(0.0, 70.0, 50.0),
        Scalar(
            10.0,
            255.0,
            255.0,
        ),
        image,
    )
    // Znajdowanie konturów
    val contours = ArrayList<MatOfPoint>()
    Imgproc.findContours(image, contours, Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE)
    // Szukanie konturu z maksymalnym polem i obliczanie prostokąta ograniczającego
    return contours.maxByOrNull { Imgproc.contourArea(it) }?.let {
        val contour2f = MatOfPoint2f()
        it.convertTo(contour2f, CvType.CV_32F) // konwersja do MatOfPoint2f
        Imgproc.boundingRect(contour2f)
    }
}

@Composable
fun DrawBoundingBox(boundingBox: Rect?) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        boundingBox?.let {
            drawRect(
                color = Color.Green,
                topLeft = Offset(it.x.toFloat(), it.y.toFloat()),
                size = Size(it.width.toFloat(), it.height.toFloat()),
                style = Stroke(width = 3f),
            )
        }
    }
}

@Composable
fun CameraScreen(modifier: Modifier) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    var boundingBox by remember { mutableStateOf<Rect?>(null) }
    // Damienne do obsługi obrazu z kamery
    val imageAnalysis =
        remember {
            ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build().also {
                    it.setAnalyzer(ContextCompat.getMainExecutor(context)) { imageProxy ->
                        CoroutineScope(Dispatchers.Default).launch {
                            val frame = imageProxy.toMat()
                            boundingBox = findRedCircle(frame)
                            imageProxy.close() // Ważne, aby zwolnić zasoby!
                        }
                    }
                }
        }

    Box(modifier = modifier.fillMaxSize()) {
        CameraPreview(cameraProviderFuture, lifecycleOwner, imageAnalysis)
        if (boundingBox != null) {
            DrawBoundingBox(boundingBox)
        }
    }
}

@Composable
fun CameraPreview(
    cameraProviderFuture: ListenableFuture<ProcessCameraProvider>,
    lifecycleOwner: LifecycleOwner,
    imageAnalysis: ImageAnalysis,
) {
    val context = LocalContext.current
    AndroidView(factory = { AndroidViewContext ->
        PreviewView(AndroidViewContext).apply {
            layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        }
    }, update = { previewView ->
        val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
        val preview =
            Preview.Builder().build().also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }
        val cameraSelector =
            CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_FRONT)
                .build()
        try {
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                preview,
                imageAnalysis,
            )
        } catch (exc: Exception) {
            Log.e("CameraPreview", "Use case binding failed", exc)
        }
    })
}

fun ImageProxy.toMat(): Mat {
    val yBuffer = planes[0].buffer
    val uBuffer = planes[1].buffer
    val vBuffer = planes[2].buffer

    val ySize = yBuffer.remaining()
    val uSize = uBuffer.remaining()
    val vSize = vBuffer.remaining()

    val nv21 = ByteArray(ySize + uSize + vSize)

    yBuffer.get(nv21, 0, ySize)
    vBuffer.get(nv21, ySize, vSize)
    uBuffer.get(nv21, ySize + vSize, uSize)

    val yuvMat = Mat(height + height / 2, width, CvType.CV_8UC1)
    yuvMat.put(0, 0, nv21)

    val rgbMat = Mat()
    Imgproc.cvtColor(yuvMat, rgbMat, Imgproc.COLOR_YUV2RGB_NV21, 3)
    yuvMat.release()

    return rgbMat
}
