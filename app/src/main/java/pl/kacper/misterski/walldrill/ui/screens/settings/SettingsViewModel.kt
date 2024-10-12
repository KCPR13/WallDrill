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

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import pl.kacper.misterski.walldrill.R
import pl.kacper.misterski.walldrill.core.BaseViewModel
import pl.kacper.misterski.walldrill.domain.ResourceProvider
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel
    @Inject
    constructor(
        private val resourceProvider: ResourceProvider,
    ) : BaseViewModel() {
        private val _uiState = MutableStateFlow(SettingsUiState())
        val uiState =
            _uiState
                .onStart {
                    fetchModels() // TODO K check if works
                }.stateIn(
                    viewModelScope,
                    started = SharingStarted.WhileSubscribed(5000), // TODO K constants
                    initialValue = SettingsUiState(),
                )

        private fun fetchModels() {
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

            _uiState.update { SettingsUiState(listOf(calibration, colorDetection)) }
        }
    }
