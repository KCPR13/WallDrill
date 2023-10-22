package pl.kacper.misterski.walldrill.ui.screens.colordetection

import androidx.camera.core.CameraSelector
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import pl.kacper.misterski.walldrill.R
import pl.kacper.misterski.walldrill.ui.AppNavigation
import pl.kacper.misterski.walldrill.ui.CameraPreview
import pl.kacper.misterski.walldrill.ui.common.AppToolbar
import pl.kacper.misterski.walldrill.ui.common.SelectedColor
import pl.kacper.misterski.walldrill.ui.theme.BorderWidth
import pl.kacper.misterski.walldrill.ui.theme.CameraPreviewSize
import pl.kacper.misterski.walldrill.ui.theme.CornerRadius
import pl.kacper.misterski.walldrill.ui.theme.FontLarge
import pl.kacper.misterski.walldrill.ui.theme.Mili
import pl.kacper.misterski.walldrill.ui.theme.PaddingLarge
import pl.kacper.misterski.walldrill.ui.theme.RingSize
import pl.kacper.misterski.walldrill.ui.theme.SelectedColorSize

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorDetection(
    modifier: Modifier,
    viewModel: ColorDetectionViewModel = hiltViewModel(),
    navController: NavHostController
) {

    val state = viewModel.uiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(modifier,
        topBar = {
            AppToolbar(
                R.string.detect_the_color,
                Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                scrollBehavior

            ) { navController.navigate(AppNavigation.COLORS) }
        },
        bottomBar = {
            Button(
                onClick = {
                    viewModel.saveColor {
                        navController.navigate(AppNavigation.COLORS)
                    }

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(PaddingLarge),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(containerColor = Mili)
            ) {
                Text(text = stringResource(R.string.save))
            }
        }) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(PaddingLarge)
        ) {
            Box(
                modifier = Modifier
                    .size(CameraPreviewSize)
                    .clip(RoundedCornerShape(CornerRadius))
                    .border(BorderWidth, Mili),
                contentAlignment = Alignment.Center

            ) {
                CameraPreview(
                    analyzer = viewModel.colorAnalyzer,
                    cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

                )

                Ring(
                    modifier = Modifier
                        .size(RingSize)
                        .background(Color.Transparent)
                )
            }

            Text(
                text = stringResource(R.string.detected_color),
                fontSize = FontLarge
            )
            SelectedColor(
                modifier = Modifier.size(SelectedColorSize), color = state.value, drawBorder = true
            )
        }
    }

}


@Composable
private fun Ring(modifier: Modifier) {
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

@Preview
@Composable
fun ColorDetectPreview() {
    MaterialTheme {
        ColorDetection(Modifier, navController = rememberNavController())
    }
}
