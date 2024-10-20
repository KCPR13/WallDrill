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
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import pl.kacper.misterski.walldrill.R
import pl.kacper.misterski.walldrill.ui.theme.AimPointSize
import pl.kacper.misterski.walldrill.ui.theme.PaddingExtraLarge
import pl.kacper.misterski.walldrill.ui.theme.WallDrillTheme

@Composable
fun AimScreen(
    modifier: Modifier = Modifier,
    onFolderClick: () -> Unit = {},
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Scaffold(
            modifier = modifier,
            floatingActionButton =
                {
                    FloatingActionButton(
                        onClick = {
                            onFolderClick.invoke()
                            // showBottomBar.invoke(true) TODO K needed?
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.KeyboardArrowLeft,
                            contentDescription = null,
                        )
                    }
                },
        ) { paddingValues ->
            Box(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
            ) {
                Icon(
                    modifier =
                        Modifier
                            .align(Alignment.TopStart)
                            .padding(PaddingExtraLarge)
                            .clickable { },
                    // TODO K
                    imageVector = Icons.Outlined.Info,
                    contentDescription = null,
                )

                AimPoint(Modifier.align(Alignment.Center).size(AimPointSize), AimPointSize)
            }
        }
    }
}

@Composable
private fun AimPoint(
    modifier: Modifier,
    radius: Dp,
) {
    Canvas(
        modifier = modifier,
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val circleRadius = radius.toPx()

        val centerX = canvasWidth / 2
        val centerY = canvasHeight / 2

        drawCircle(
            color = Color.Red,
            radius = circleRadius,
            center = Offset(centerX, centerY),
        )
    }
}

@PreviewLightDark
@Composable
fun AimScreenPreview() {
    WallDrillTheme {
        AimScreen(modifier = Modifier, onFolderClick = {})
    }
}
