package pl.kacper.misterski.walldrill.ui.fragments
import androidx.camera.core.CameraSelector
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalibrationScreen() {
   Scaffold(
       topBar = { TopBar(Modifier.padding(8.dp).fillMaxWidth()) },
       content = {paddingValues -> CameraPreview(modifier = Modifier.padding(paddingValues)
           , cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA)}
   )
}

@Composable
fun TopBar(modifier: Modifier) {
    Text(text = "Calibrate the camera", fontSize = 32.sp,
        modifier = modifier,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center)

}

