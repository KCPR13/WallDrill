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
package pl.kacper.misterski.walldrill.domain.usecase

import kotlinx.coroutines.flow.flow
import pl.kacper.misterski.walldrill.R
import pl.kacper.misterski.walldrill.domain.ResourceProvider
import pl.kacper.misterski.walldrill.ui.screens.settings.SettingsAction
import pl.kacper.misterski.walldrill.ui.screens.settings.SettingsModel

class SettingsUseCase(
    private val resourceProvider: ResourceProvider,
) {
    operator fun invoke() =
        flow {
            val colorDetection =
                SettingsModel(
                    resourceProvider.getString(R.string.colors),
                    SettingsAction.COLORS,
                )
            val calibration =
                SettingsModel(
                    resourceProvider.getString(R.string.calibration),
                    SettingsAction.CALIBRATION,
                )

            val result =
                buildList {
                    add(colorDetection)
                    add(calibration)
                }

            emit(result)
        }
}
