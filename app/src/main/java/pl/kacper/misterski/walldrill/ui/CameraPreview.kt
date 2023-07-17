package pl.kacper.misterski.walldrill.ui

import android.util.Log
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import kotlinx.coroutines.launch
import pl.kacper.misterski.walldrill.domain.extensions.getCameraProvider
import java.util.concurrent.Executors

@Composable
fun CameraPreview(
    modifier: Modifier = Modifier,
    analyzer: ImageAnalysis.Analyzer?,
    scaleType: PreviewView.ScaleType = PreviewView.ScaleType.FILL_CENTER,
    cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
) {
    val coroutineScope = rememberCoroutineScope()
    val lifecycleOwner = LocalLifecycleOwner.current
    AndroidView(
        modifier = modifier,
        factory = { context ->
            val previewView = PreviewView(context).apply {
                this.scaleType = scaleType
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }

            val cameraExecutor = Executors.newSingleThreadExecutor()


            val imageAnalyzer = ImageAnalysis.Builder()
                .setBackpressureStrategy(STRATEGY_KEEP_ONLY_LATEST)
                .build()
                .also {
                    if (analyzer != null) {
                        it.setAnalyzer(cameraExecutor, analyzer)
                    }
                }

//            // CameraX Preview UseCase
            val previewUseCase = androidx.camera.core.Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }

            coroutineScope.launch {
                val cameraProvider = context.getCameraProvider()
                try {
                    // Must unbind the use-cases before rebinding them.
                    cameraProvider.unbindAll()

                    cameraProvider.bindToLifecycle(
                        lifecycleOwner, cameraSelector, imageAnalyzer,previewUseCase
                    )
                } catch (ex: Exception) {
                    Log.e("CameraPreview", "Use case binding failed", ex)
                }
            }

            previewView
        }
    )
}
//private fun takePhoto(context: Context) { TODO
//    // Get a stable reference of the modifiable image capture use case
//    val imageCapture = imageCapture ?: return
//
//    // Create time stamped name and MediaStore entry.
//    val name = SimpleDateFormat(FILENAME_FORMAT, Locale.US)
//        .format(System.currentTimeMillis())
//    val contentValues = ContentValues().apply {
//        put(MediaStore.MediaColumns.DISPLAY_NAME, name)
//        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
//        put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
//
//    }
//
//    // Create output options object which contains file + metadata
//    val outputOptions = ImageCapture.OutputFileOptions
//        .Builder(contentResolver,
//            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//            contentValues)
//        .build()
//
//    // Set up image capture listener, which is triggered after photo has
//    // been taken
//    imageCapture.takePicture(
//        outputOptions,
//        ContextCompat.getMainExecutor(context),
//        object : ImageCapture.OnImageSavedCallback {
//            override fun onError(exc: ImageCaptureException) {
//                Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
//            }
//
//            override fun
//                    onImageSaved(output: ImageCapture.OutputFileResults){
//                val msg = "Photo capture succeeded: ${output.savedUri}"
//                Log.d(TAG, msg)
//            }
//        }
//    )
//}

