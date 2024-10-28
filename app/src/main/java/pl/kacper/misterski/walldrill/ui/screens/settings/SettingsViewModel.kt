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
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.kacper.misterski.walldrill.core.BaseViewModel
import pl.kacper.misterski.walldrill.domain.constants.Constants.FLOW_STOP_TIMEOUT
import pl.kacper.misterski.walldrill.domain.use_case.SettingsUseCase
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel
@Inject
constructor(
    private val settingsUseCase: SettingsUseCase,
) : BaseViewModel() {
    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState =
        _uiState
            .onStart {
                fetchModels()
            }.stateIn(
                viewModelScope,
                started = SharingStarted.WhileSubscribed(FLOW_STOP_TIMEOUT),
                initialValue = SettingsUiState(),
            )

    private fun fetchModels() {
        viewModelScope.launch {
            settingsUseCase.invoke().onEach { items ->
                _uiState.update { SettingsUiState(items) }
            }.catch { error ->
                _uiState.update { SettingsUiState(emptyList()) }
            }.collect()
        }
    }
}
