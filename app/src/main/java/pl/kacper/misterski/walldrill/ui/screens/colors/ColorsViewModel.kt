package pl.kacper.misterski.walldrill.ui.screens.colors

import androidx.lifecycle.ViewModel
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
import pl.kacper.misterski.walldrill.db.color.Color
import pl.kacper.misterski.walldrill.db.color.ColorRepository
import javax.inject.Inject


@HiltViewModel
class ColorsViewModel @Inject constructor(private val colorsRepository: ColorRepository): ViewModel() {

    private val _uiState = MutableStateFlow(ColorsUiState())
    val uiState = _uiState.asStateFlow()

    fun fetchColors(){
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.showProgress() } // TODO K usage
            colorsRepository.getAll().onEach {colors ->
                val updatedList = colors ?: emptyList()
                _uiState.update { it.updateList(updatedList) }
            }.catch {
                //TODO K
            }.collect()
        }
    }

    fun onItemClick(color: Color) {
        if (color.selected) return
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.showProgress() }
            colorsRepository.uncheckSelectedColor()
            colorsRepository.setColorChecked(color)
            fetchColors()
        }
    }


}