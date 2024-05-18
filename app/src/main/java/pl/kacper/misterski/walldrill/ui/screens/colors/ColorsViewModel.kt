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
package pl.kacper.misterski.walldrill.ui.screens.colors

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.kacper.misterski.walldrill.core.BaseViewModel
import pl.kacper.misterski.walldrill.core.di.BackgroundDispatcher
import pl.kacper.misterski.walldrill.db.color.Color
import pl.kacper.misterski.walldrill.db.color.ColorRepository
import javax.inject.Inject

@HiltViewModel
class ColorsViewModel
    @Inject
    constructor(
        private val colorsRepository: ColorRepository,
        @BackgroundDispatcher val backgroundDispatcher: CoroutineDispatcher,
    ) :
    BaseViewModel() {
        private val _uiState = MutableStateFlow(ColorsUiState())
        val uiState = _uiState.asStateFlow()

        fun fetchColors() {
            viewModelScope.launch(backgroundDispatcher) {
                colorsRepository.getAll().onEach { colors ->
                    val updatedList = colors ?: emptyList()
                    _uiState.update { it.updateList(updatedList) }
                }.catch { error ->
                    Log.e(tag, "fetchColors exception")
                    error.printStackTrace()
                    _uiState.update { it.updateList(emptyList()) }
                }.collect()
            }
        }

        fun onItemClick(color: Color) {
            if (color.selected) return
            viewModelScope.launch(backgroundDispatcher) {
                colorsRepository.uncheckSelectedColor()
                colorsRepository.setColorChecked(color)
                fetchColors()
            }
        }

        fun onRemoveItem(color: Color) {
            viewModelScope.launch(backgroundDispatcher) {
                colorsRepository.remove(color)
                fetchColors()
            }
        }
    }
