package pl.kacper.misterski.walldrill.ui.screens.calibration

import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import pl.kacper.misterski.walldrill.R
import pl.kacper.misterski.walldrill.ui.AppNavigation
import pl.kacper.misterski.walldrill.ui.CameraPreview
import pl.kacper.misterski.walldrill.ui.common.AppToolbar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalibrationScreen(
    modifier: Modifier,
    viewModel: CalibrationViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val calibrationUiState by viewModel.uiState.collectAsState()
    viewModel.initAnalyzer()
    Scaffold(
        modifier = modifier,
        topBar = {
            AppToolbar(
                R.string.calibration,
                Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                scrollBehavior,
                onBackPressedClick = {
                    viewModel.disposeAnalyzer()
                    navController.navigate(AppNavigation.SETTINGS)
                })
        },
        content = { paddingValues ->
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)) {
                CameraPreview(
                    analyzer = viewModel.colorAnalyzer,
                    cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

                )
                // Draw the overlay rectangles on the camera preview
                androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxSize()) {
                    Log.d("Kacpur", "detectedPoints: ${calibrationUiState.detectedPoints.size}")
                    calibrationUiState.detectedPoints.forEach { location ->
                        drawRect(
                            color = Color.Red,
                            topLeft = Offset(location.first.toFloat(), location.second.toFloat()),
                            size = Size(1f, 1f)
                        )
                    }
                }
            }
        }
    )
}

@Preview
@Composable
fun CalibrationScreenPreview() {
    MaterialTheme {
        CalibrationScreen(modifier = Modifier,
            navController = rememberNavController() )
    }
}