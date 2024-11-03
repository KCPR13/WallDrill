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
package pl.kacper.misterski.walldrill.ui.main

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import pl.kacper.misterski.walldrill.R
import pl.kacper.misterski.walldrill.ui.common.getIconTint
import pl.kacper.misterski.walldrill.ui.theme.BottomBarDefaultIconSize
import pl.kacper.misterski.walldrill.ui.theme.BottomBarSelectedIconSize
import pl.kacper.misterski.walldrill.ui.theme.WallDrillTheme

//TODO K separate uistate
@Composable
fun AnimatedBottomBar(
    modifier: Modifier = Modifier,
    selectedBottomBarOption: MainUiState.BottomBarOption,
    show: Boolean,
    onSettingsClick: () -> Unit,
    onFolderClick: () -> Unit,
    onAimClick: () -> Unit,
) {
    AnimatedVisibility(
        visible = show,
    ) {
        BottomAppBar(
            modifier = modifier,
            actions = {
                BottomBarAction(
                    icon = R.drawable.ic_settings,
                    testTag = stringResource(R.string.test_tag_settings_icon),
                    onClick = onSettingsClick,
                    isSelected = selectedBottomBarOption == MainUiState.BottomBarOption.SETTINGS,
                )
                BottomBarAction(
                    icon = R.drawable.ic_folder,
                    testTag = stringResource(R.string.test_tag_folder_icon),
                    onClick = onFolderClick,
                    isSelected = selectedBottomBarOption == MainUiState.BottomBarOption.FOLDER,
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    elevation = FloatingActionButtonDefaults.elevation(0.dp),
                    onClick = onAimClick,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_shooting),
                        contentDescription = null,
                    )
                }
            },
        )
    }
}

@Composable
fun BottomBarAction(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    testTag: String,
    onClick: () -> Unit,
    isSelected: Boolean,
) {
    IconButton(
        modifier = modifier.testTag(testTag),
        onClick = {
            if (!isSelected) onClick.invoke()
        },
    ) {

        Image(
            modifier = if (isSelected) Modifier.size(BottomBarSelectedIconSize) else Modifier
                .size(
                    BottomBarDefaultIconSize,
                ),
            painter = painterResource(id = icon),
            contentDescription = null,
            colorFilter = getIconTint(),
        )

    }
}

@PreviewLightDark
@Composable
fun AnimatedBottomBarPreview() {
    WallDrillTheme {
        AnimatedBottomBar(
            modifier = Modifier,
            MainUiState.BottomBarOption.SETTINGS,
            true,
            {},
            {},
            {},
        )
    }
}
