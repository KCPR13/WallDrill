package pl.kacper.misterski.walldrill.ui.screens.colors

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.kacper.misterski.walldrill.core.BaseViewModel
import pl.kacper.misterski.walldrill.db.color.Color
import pl.kacper.misterski.walldrill.db.color.ColorRepository
import javax.inject.Inject


@HiltViewModel
class ColorsViewModel @Inject constructor(private val colorsRepository: ColorRepository): BaseViewModel() {

    private val _uiState = MutableStateFlow(ColorsUiState())
    val uiState = _uiState.asStateFlow()

    fun fetchColors(){
        viewModelScope.launch(Dispatchers.IO) {
            colorsRepository.getAll().onEach {colors ->
                val updatedList = colors ?: emptyList()
                _uiState.update { it.updateList(updatedList) }
            }.catch {error ->
                Log.e(TAG, "fetchColors exception")
                error.printStackTrace()
                _uiState.update { it.updateList(emptyList()) }
            }.collect()
        }
    }

    fun onItemClick(color: Color) {
        if (color.selected) return
        viewModelScope.launch(Dispatchers.IO) {
            colorsRepository.uncheckSelectedColor()
            colorsRepository.setColorChecked(color)
            fetchColors()
        }
    }

    fun onRemoveItem(color: Color){
        viewModelScope.launch(Dispatchers.IO) {
            colorsRepository.remove(color)
            fetchColors()
        }
    }


}