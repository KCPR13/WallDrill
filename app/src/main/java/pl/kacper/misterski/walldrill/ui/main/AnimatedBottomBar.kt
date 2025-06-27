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

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import pl.kacper.misterski.walldrill.R
import pl.kacper.misterski.walldrill.ui.common.AppFab
import pl.kacper.misterski.walldrill.ui.common.getIconTint
import pl.kacper.misterski.walldrill.ui.theme.BottomBarDefaultIconSize
import pl.kacper.misterski.walldrill.ui.theme.WallDrillTheme

// TODO main screen
// TODO BottomBar visible only on main screen
// TODO K separate uistate
@Composable
fun AnimatedBottomBar(
    modifier: Modifier = Modifier,
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
                    imageVector = Icons.Outlined.Settings,
                    testTag = stringResource(R.string.test_tag_settings_icon),
                    onClick = onSettingsClick,
                )

                BottomBarAction(
                    imageVector = Icons.Outlined.Folder,
                    testTag = stringResource(R.string.test_tag_folder_icon),
                    onClick = onFolderClick,
                )
            },
            floatingActionButton = {
                AppFab(
                    iconResource = R.drawable.ic_shooting,
                    onClick = onAimClick,
                )
            },
        )
    }
}

@Composable
fun BottomBarAction(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    testTag: String,
    onClick: () -> Unit,
) {
    IconButton(
        modifier = modifier.testTag(testTag),
        onClick = onClick,
    ) {
        Image(
            modifier =
                Modifier.size(
                    BottomBarDefaultIconSize,
                ),
            imageVector = imageVector,
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
            true,
            {},
            {},
            {},
        )
    }
}
