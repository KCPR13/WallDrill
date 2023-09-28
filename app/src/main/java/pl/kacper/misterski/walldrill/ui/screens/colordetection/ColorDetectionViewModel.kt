package pl.kacper.misterski.walldrill.ui.screens.colordetection

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.kacper.misterski.walldrill.db.color.ColorRepository
import javax.inject.Inject

@HiltViewModel
class ColorDetectionViewModel @Inject constructor(private val colorRepository: ColorRepository): ViewModel() {

    val _uiState = MutableStateFlow(Color.Black)
    val uiState = _uiState.asStateFlow()

    fun saveColor(color: String, onColorSaved: () -> Unit){
        viewModelScope.launch(Dispatchers.IO) {
            colorRepository.insert(pl.kacper.misterski.walldrill.db.color.Color(color = color))
            onColorSaved.invoke()
        }

    }

}