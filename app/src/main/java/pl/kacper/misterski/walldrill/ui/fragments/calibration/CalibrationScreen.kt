package pl.kacper.misterski.walldrill.ui.fragments.calibration

import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.StateFlow
import pl.kacper.misterski.walldrill.ui.MainUiState
import androidx.compose.ui.graphics.Color
import pl.kacper.misterski.walldrill.domain.ColorAnalyzer
import pl.kacper.misterski.walldrill.ui.CameraPreview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalibrationScreen(colorDetectionListener: ColorAnalyzer.ColorDetectionListener,
                      uiState: StateFlow<MainUiState>) {

    val mainUiState by uiState.collectAsState()

    Scaffold(
        topBar = { TopBar(
            Modifier
                .padding(8.dp)
                .fillMaxWidth()) },
        content = { paddingValues ->
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            CameraPreview(
                colorDetectionListener= colorDetectionListener,
                cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

            )
                // Draw the overlay rectangles on the camera preview
                androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxSize()) {
                    Log.d("Kacpur", "detectedPoints: ${mainUiState.detectedPoints.size}")
                    mainUiState.detectedPoints.forEach { location ->
                        drawRect(
                            color = Color.Green,
                            topLeft = Offset(location.first.toFloat(), location.second.toFloat()),
                            size = Size(1f, 1f),
                            style = Stroke(width = 5f)
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun TopBar(modifier: Modifier) {
    Text(
        text = "Calibrate the camera", fontSize = 32.sp,
        modifier = modifier,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )

}

