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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import pl.kacper.misterski.walldrill.R
import pl.kacper.misterski.walldrill.ui.AppNavigation
import pl.kacper.misterski.walldrill.ui.CameraPreview
import pl.kacper.misterski.walldrill.ui.common.AppToolbar
import pl.kacper.misterski.walldrill.ui.common.SelectedColor
import pl.kacper.misterski.walldrill.ui.theme.Mili

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorDetection(
    modifier: Modifier,
    viewModel: ColorDetectionViewModel = hiltViewModel(), // TODO K all viewModels to hiltViewModel
    navController: NavHostController
) {

    val state = viewModel.uiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())


    Scaffold(modifier,
        topBar = {
            AppToolbar(
                R.string.detect_the_color,
                Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                scrollBehavior,
                onBackPressedClick = { navController.navigate(AppNavigation.COLORS) }

            )
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
                    .padding(16.dp),
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(300.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .border(4.dp, Mili), // TODO K padding constants
                contentAlignment = Alignment.Center

            ) {
                CameraPreview(
                    analyzer = viewModel.detectColorAnalyzer,
                    cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                )

                Ring(
                    modifier = Modifier
                        .size(300.dp)
                        .background(Color.Transparent)
                )
            }

            Text(
                text = stringResource(R.string.detected_color),
                fontSize = 24.sp
            )
            SelectedColor(
                modifier = Modifier.size(50.dp), color = state.value, drawBorder = true
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
