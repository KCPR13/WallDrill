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
package pl.kacper.misterski.walldrill.ui.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.PreviewLightDark
import pl.kacper.misterski.walldrill.R
import pl.kacper.misterski.walldrill.ui.common.AppToolbar
import pl.kacper.misterski.walldrill.ui.theme.CardElevation
import pl.kacper.misterski.walldrill.ui.theme.FontMedium
import pl.kacper.misterski.walldrill.ui.theme.PaddingLarge
import pl.kacper.misterski.walldrill.ui.theme.WallDrillTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    onColorsClick: () -> Unit = {},
    onCalibrationClick: () -> Unit = {},
    uiState: SettingsUiState,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier,
        topBar = {
            AppToolbar(
                R.string.settings,
                Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                scrollBehavior,
            )
        },
    ) { paddingValues ->
        LazyColumn(Modifier.padding(top = paddingValues.calculateTopPadding())) {
            items(uiState.models) { model ->
                SettingsItem(
                    modifier =
                        Modifier
                            .clickable {
                                when (model.action) {
                                    SettingsAction.COLORS ->
                                        onColorsClick.invoke()

                                    SettingsAction.CALIBRATION ->
                                        onCalibrationClick.invoke()
                                }
                            }.padding(PaddingLarge)
                            .fillMaxWidth(),
                    model = model,
                )
            }
        }
    }
}

@Composable
private fun SettingsItem(
    modifier: Modifier,
    model: SettingsModel,
) {
    Card(
        modifier,
        shape = RectangleShape,
        elevation =
            CardDefaults.cardElevation(
                defaultElevation = CardElevation,
            ),
//        colors = TODO K remove?
//            CardDefaults.cardColors(
//                containerColor = Color.White,
//            ),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                modifier =
                    Modifier
                        .padding(PaddingLarge)
                        .align(Alignment.CenterVertically),
                fontSize = FontMedium,
                text = model.title,
            )
            Icon(
                modifier =
                    Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = PaddingLarge),
                imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = null,
            )
        }
    }
}

@PreviewLightDark
@Composable
fun SettingsScreenPreview() {
    WallDrillTheme {
        SettingsScreen(
            onColorsClick = {},
            onCalibrationClick = {},
            modifier = TODO(),
            uiState = TODO(),
        )
    }
}
