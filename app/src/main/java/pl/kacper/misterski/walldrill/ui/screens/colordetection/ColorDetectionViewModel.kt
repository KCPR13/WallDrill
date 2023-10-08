package pl.kacper.misterski.walldrill.ui.screens.colordetection

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.kacper.misterski.walldrill.domain.DetectColorAnalyzer
import pl.kacper.misterski.walldrill.domain.DetectColorListener
import javax.inject.Inject

@HiltViewModel
class ColorDetectionViewModel @Inject constructor(
//    private val colorRepository: ColorRepository TODO K
): ViewModel() {

    private val _uiState = MutableStateFlow(Color.Black)
    val uiState = _uiState.asStateFlow()

    //TODO K dispose
    private val detectColorListener = object:  DetectColorListener{
        override fun onColorDetected(color: Color) {
            _uiState.update { color }
        }

    }

    val detectColorAnalyzer =  DetectColorAnalyzer(detectColorListener)



    fun saveColor(color: String, onColorSaved: () -> Unit){
        viewModelScope.launch(Dispatchers.IO) {
//            colorRepository.insert(pl.kacper.misterski.walldrill.db.color.Color(color = color)) TODO K
            onColorSaved.invoke()
        }

    }

}