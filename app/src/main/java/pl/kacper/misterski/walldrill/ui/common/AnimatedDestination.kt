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

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.animatedDestination(
    route: String,
    durationInMillis: Int = 200,
    content: @Composable () -> Unit,
) {
    composable(
        route,
        enterTransition = {
            fadeIn(
                animationSpec =
                    tween(
                        durationInMillis,
                        easing = LinearEasing,
                    ),
            ) +
                slideIntoContainer(
                    animationSpec = tween(durationInMillis, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start,
                )
        },
        exitTransition = {
            fadeOut(
                animationSpec =
                    tween(
                        durationInMillis,
                        easing = LinearEasing,
                    ),
            ) +
                slideOutOfContainer(
                    animationSpec = tween(durationInMillis, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                )
        },
    ) {
        content.invoke()
    }
}
