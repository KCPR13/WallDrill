package pl.kacper.misterski.walldrill.ui.screens.calibration

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CalibrationViewModel @Inject constructor(): ViewModel()  {

    private val _uiState = MutableStateFlow(CalibrationUiState())
    val uiState = _uiState.asStateFlow()

    fun updatePoints(detectedLocations: List<Pair<Int, Int>>) {
        _uiState.update { CalibrationUiState(detectedLocations.toMutableList()) }
    }

}