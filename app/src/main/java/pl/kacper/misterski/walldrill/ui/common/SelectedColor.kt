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
package pl.kacper.misterski.walldrill.ui.common

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import pl.kacper.misterski.walldrill.domain.extensions.isColorDark
import pl.kacper.misterski.walldrill.ui.theme.CornerRadius
import pl.kacper.misterski.walldrill.ui.theme.Mili
import pl.kacper.misterski.walldrill.ui.theme.PaddingSmall

@Composable
fun SelectedColor(
    modifier: Modifier = Modifier,
    color: Color,
    drawBorder: Boolean,
    onRemove: (() -> Unit)? = null,
) {
    var mod = modifier

    if (drawBorder) {
        mod = mod.border(PaddingSmall, Mili)
    }

    Box(
        modifier =
            mod
                .clip(RoundedCornerShape(CornerRadius))
                .drawBehind {
                    drawRect(size = size, color = color)
                },
    ) {
        onRemove?.let { removeColor ->
            Icon(
                tint = if (color.isColorDark()) Color.White else Color.Black,
                modifier =
                    Modifier
                        .align(Alignment.TopEnd)
                        .padding(PaddingSmall)
                        .clickable { removeColor.invoke() },
                imageVector = Icons.Outlined.Clear,
                contentDescription = null,
            )
        }
    }
}
