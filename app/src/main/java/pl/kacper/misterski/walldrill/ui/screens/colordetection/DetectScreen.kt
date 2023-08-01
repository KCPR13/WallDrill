package pl.kacper.misterski.walldrill.ui.screens.colordetection

import androidx.camera.core.CameraSelector
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import pl.kacper.misterski.walldrill.domain.DetectColorAnalyzer
import pl.kacper.misterski.walldrill.ui.CameraPreview

const val TAG = "DetectScreen"
@Composable
fun ColorDetection(modifier: Modifier, viewModel: ColorDetectionViewModel = viewModel()) {


    val state = viewModel.uiState.collectAsState()

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
                CameraPreview(
                    analyzer = DetectColorAnalyzer(viewModel._uiState),
                    cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

                )

                Ring(modifier = Modifier
                    .size(300.dp)
                    .background(Color.Transparent))
            }

            Text(
                text = "Detected color",
                fontSize = 24.sp,
                modifier = Modifier.padding(16.dp)
            )
            Box(modifier = Modifier
                .size(50.dp)
                .drawBehind {
                    drawRect(size = size, color = state.value)
                }
            )

            Button(
                onClick = {
                   // takePhoto(LocalContext.current) TODO
                          },
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Select")
            }
        }
    }
}




@Composable
fun Ring(modifier: Modifier) {
    Canvas(
        modifier
    ) {
        drawCircle(
            brush = Brush.sweepGradient(listOf(Color.Blue, Color.Blue)),
            radius = 50f,
            style = Stroke(4f)
        )
    }
}

