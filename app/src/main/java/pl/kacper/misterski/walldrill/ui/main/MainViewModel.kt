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

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import pl.kacper.misterski.walldrill.core.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor() : BaseViewModel() {
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState = _uiState.asStateFlow()

    fun updatePermissionState(permissionGranted: Boolean) =
        _uiState.update {
            it.copy(
                permissionGranted = permissionGranted,
            )
        }

    fun updateBottomBarVisibility(showBottomBar: Boolean) = _uiState.update {
        it.copy(showBottomBar = showBottomBar)
    }
}
