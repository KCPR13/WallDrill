package pl.kacper.misterski.walldrill.ui.screens.aim

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import pl.kacper.misterski.walldrill.R
import pl.kacper.misterski.walldrill.ui.AppNavigation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AimScreen(
    modifier: Modifier,
    navController: NavHostController,
    showBottomBar: (Boolean) -> Unit
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Scaffold(modifier = modifier, floatingActionButton =
        {
            FloatingActionButton(
                onClick = {
                    showBottomBar.invoke(true)
                    navController.navigate(AppNavigation.FOLDER)
                }, // TODO K ??
                containerColor = colorResource(id = R.color.mili),

                ) {
                Icon(
                    imageVector = Icons.Outlined.KeyboardArrowLeft,
                    contentDescription = null
                )
            }

        }) { paddingValues ->
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)) {
                    Icon(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(24.dp)
                            .clickable { },//TODO K

                        imageVector = Icons.Outlined.Info,
                        contentDescription = null
                    )

                AimPoint(Modifier.align(Alignment.Center).size(100.dp))

            }
        }
    }

}


@Composable
private fun AimPoint(modifier: Modifier) {
    Canvas(
        modifier = modifier
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val circleRadius = 100.dp.toPx()

        val centerX = canvasWidth / 2
        val centerY = canvasHeight / 2

        drawCircle(
            color = Color.Red,
            radius = circleRadius,
            center = Offset(centerX, centerY)
        )
    }
}

@Preview
@Composable
fun AimScreenPreview() {
    MaterialTheme {
        AimScreen(modifier = Modifier, navController = rememberNavController() , showBottomBar = {} )
    }
}

