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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import pl.kacper.misterski.walldrill.R
import pl.kacper.misterski.walldrill.ui.theme.BottomBarIconSize
import pl.kacper.misterski.walldrill.ui.theme.WallDrillTheme

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
                    icon = R.drawable.ic_settings,
                    testTag = stringResource(R.string.test_tag_settings_icon),
                    onClick = onSettingsClick,
                )
                BottomBarAction(
                    icon = R.drawable.ic_folder,
                    testTag = stringResource(R.string.test_tag_folder_icon),
                    onClick = onFolderClick,
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = onAimClick,
                    containerColor = colorResource(id = R.color.mili),
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
) {
    IconButton(modifier = modifier.testTag(testTag), onClick = { onClick.invoke() }) {
        Image(
            modifier = Modifier.size(BottomBarIconSize),
            painter = painterResource(id = icon),
            contentDescription = null,
            colorFilter = ColorFilter.tint(Color.Black),
        )
    }
}

@Preview
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
