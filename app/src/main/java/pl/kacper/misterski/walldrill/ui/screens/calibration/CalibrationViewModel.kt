package pl.kacper.misterski.walldrill.ui.screens.calibration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.kacper.misterski.walldrill.db.color.ColorRepository
import pl.kacper.misterski.walldrill.domain.ColorAnalyzer
import javax.inject.Inject

@HiltViewModel
class CalibrationViewModel @Inject constructor(private val colorRepository: ColorRepository, val colorAnalyzer: ColorAnalyzer): ViewModel(), ColorAnalyzer.ColorDetectionListener  {

    private val _uiState = MutableStateFlow(CalibrationUiState())
    val uiState = _uiState.asStateFlow()
    init {
        viewModelScope.launch {
            colorRepository.getSelectedColor()
                .onEach {color -> // TODO K handle null
                    color?.let {
                        colorAnalyzer.init(this@CalibrationViewModel, it.getColorObject())
                    }

                }
                .catch {
                    //TODO K handle catch

                }.collect() // TODO K not inside init
        }
    }

    override fun onColorDetected(isDetected: Boolean, detectedLocations: List<Pair<Int, Int>>) {
        _uiState.update { CalibrationUiState(detectedLocations.toMutableList()) }
    }


}