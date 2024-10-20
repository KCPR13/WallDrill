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
package pl.kacper.misterski.walldrill.ui.screens.colors

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import pl.kacper.misterski.walldrill.R
import pl.kacper.misterski.walldrill.db.color.Color
import pl.kacper.misterski.walldrill.ui.common.AppToolbar
import pl.kacper.misterski.walldrill.ui.common.SelectedColor
import pl.kacper.misterski.walldrill.ui.theme.MaxGridSize
import pl.kacper.misterski.walldrill.ui.theme.MinGridSize
import pl.kacper.misterski.walldrill.ui.theme.PaddingLarge
import pl.kacper.misterski.walldrill.ui.theme.PaddingMedium
import pl.kacper.misterski.walldrill.ui.theme.WallDrillTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorsScreen(
    modifier: Modifier,
    uiState: ColorsUiState,
    onSettingsClick: () -> Unit,
    onColorDetectionClick: () -> Unit,
    onRemoveItem: (Color) -> Unit,
    onItemClick: (Color) -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier,
        topBar = {
            AppToolbar(
                R.string.colors,
                Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                scrollBehavior,
            ) { onSettingsClick.invoke() }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onColorDetectionClick.invoke() },
                containerColor = colorResource(id = R.color.mili),
            ) {
                Icon(
                    imageVector = Icons.Outlined.Add,
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
            val colors = uiState.colors
            if (colors.isEmpty()) {
                EmptyColorsPlaceHolder(modifier = Modifier.align(Center))
            } else {
                LazyVerticalGrid(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(PaddingLarge),
                    columns = GridCells.Adaptive(minSize = MinGridSize),
                    horizontalArrangement = Arrangement.spacedBy(PaddingMedium),
                    verticalArrangement = Arrangement.spacedBy(PaddingMedium),
                ) {
                    items(colors) { color ->
                        SelectedColor(
                            modifier =
                                Modifier
                                    .size(MaxGridSize)
                                    .clickable { onItemClick.invoke(color) },
                            color = color.getColorObject(),
                            drawBorder = color.selected,
                            onRemove = { onRemoveItem.invoke(color) },
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyColorsPlaceHolder(modifier: Modifier) {
    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(painter = painterResource(id = R.drawable.ic_paint), contentDescription = null)
        Text(
            modifier = Modifier.padding(vertical = PaddingLarge),
            text = stringResource(R.string.no_colors_set),
        )
    }
}

@PreviewLightDark
@Composable
fun ColorsScreenPreview() {
    WallDrillTheme {
        ColorsScreen(
            modifier = Modifier,
            onSettingsClick = {},
            onColorDetectionClick = {},
            uiState = ColorsUiState(emptyList()),
            onRemoveItem = {},
            onItemClick = {},
        )
    }
}
