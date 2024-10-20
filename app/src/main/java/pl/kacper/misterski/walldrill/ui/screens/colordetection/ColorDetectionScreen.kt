/*
 * Copyright 2024 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import pl.kacper.misterski.walldrill.R
import pl.kacper.misterski.walldrill.domain.ColorAnalyzer
import pl.kacper.misterski.walldrill.domain.enums.AnalyzerMode
import pl.kacper.misterski.walldrill.ui.CameraPreview
import pl.kacper.misterski.walldrill.ui.common.AppToolbar
import pl.kacper.misterski.walldrill.ui.common.SelectedColor
import pl.kacper.misterski.walldrill.ui.theme.BorderWidth
import pl.kacper.misterski.walldrill.ui.theme.CameraPreviewSize
import pl.kacper.misterski.walldrill.ui.theme.CornerRadius
import pl.kacper.misterski.walldrill.ui.theme.FontLarge
import pl.kacper.misterski.walldrill.ui.theme.PaddingLarge
import pl.kacper.misterski.walldrill.ui.theme.RingSize
import pl.kacper.misterski.walldrill.ui.theme.SelectedColorSize
import pl.kacper.misterski.walldrill.ui.theme.WallDrillTheme
import pl.kacper.misterski.walldrill.ui.theme.primaryDark

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorDetection(
    modifier: Modifier,
    onColorsClick: () -> Unit,
    onSaveColor: () -> Unit,
    uiState: Color,
    colorAnalyzer: ColorAnalyzer,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier,
        topBar = {
            AppToolbar(
                R.string.detect_the_color,
                Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                scrollBehavior,
            ) { onColorsClick.invoke() }
        },
        bottomBar = {
            Button(
                onClick = {
                    onSaveColor.invoke()
                    onColorsClick.invoke()
                },
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(PaddingLarge),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(containerColor = primaryDark),
            ) {
                Text(text = stringResource(R.string.save))
            }
        },
    ) { paddingValues ->

        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(PaddingLarge),
        ) {
            Box(
                modifier =
                    Modifier
                        .size(CameraPreviewSize)
                        .clip(RoundedCornerShape(CornerRadius))
                        .border(BorderWidth, primaryDark),
                contentAlignment = Alignment.Center,
            ) {
                CameraPreview(
                    analyzer = colorAnalyzer,
                    cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA,
                )

                Ring(
                    modifier =
                        Modifier
                            .size(RingSize)
                            .background(Color.Transparent),
                )
            }

            Text(
                text = stringResource(R.string.detected_color),
                fontSize = FontLarge,
            )
            SelectedColor(
                modifier = Modifier.size(SelectedColorSize),
                color = uiState,
                drawBorder = true,
            )
        }
    }
}

@Composable
private fun Ring(modifier: Modifier) {
    Canvas(
        modifier,
    ) {
        drawCircle(
            brush = Brush.sweepGradient(listOf(Color.Blue, Color.Blue)),
            radius = 50f,
            style = Stroke(4f),
        )
    }
}

@PreviewLightDark
@Composable
fun ColorDetectPreview() {
    WallDrillTheme {
        ColorDetection(
            Modifier,
            onColorsClick = {},
            onSaveColor = { },
            uiState = Color.Red,
            colorAnalyzer = ColorAnalyzer(AnalyzerMode.COLOR_DETECTION),
        )
    }
}
