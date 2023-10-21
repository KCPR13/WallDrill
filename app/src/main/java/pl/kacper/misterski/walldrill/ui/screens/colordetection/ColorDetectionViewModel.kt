package pl.kacper.misterski.walldrill.ui.screens.colordetection

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pl.kacper.misterski.walldrill.core.BaseViewModel
import pl.kacper.misterski.walldrill.core.di.DetectColorAnalyzer
import pl.kacper.misterski.walldrill.db.color.ColorRepository
import pl.kacper.misterski.walldrill.domain.ColorAnalyzer
import pl.kacper.misterski.walldrill.domain.interfaces.ColorListener
import pl.kacper.misterski.walldrill.domain.models.AnalyzerResult
import javax.inject.Inject

@HiltViewModel
class ColorDetectionViewModel @Inject constructor(
    private val colorRepository: ColorRepository,
    @DetectColorAnalyzer val colorAnalyzer: ColorAnalyzer
): BaseViewModel(), ColorListener {

    private val _uiState = MutableStateFlow(Color.Black)
    val uiState = _uiState.asStateFlow()

    init {
        colorAnalyzer.init(this)
    }

    fun saveColor(onColorSaved: () -> Unit){
        viewModelScope.launch(Dispatchers.IO) {
            colorRepository.insert(pl.kacper.misterski.walldrill.db.color.Color(color = _uiState.value.value.toString(), selected = !colorRepository.hasAnyColorSaved()))
            withContext(Dispatchers.Main){
                onColorSaved.invoke()
            }
        }
    }

    override fun onColorDetected(analyzerResult: AnalyzerResult) {
        _uiState.update { analyzerResult.color }
    }

}