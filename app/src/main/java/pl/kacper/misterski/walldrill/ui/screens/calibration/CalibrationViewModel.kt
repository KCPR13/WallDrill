package pl.kacper.misterski.walldrill.ui.screens.calibration

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.kacper.misterski.walldrill.R
import pl.kacper.misterski.walldrill.core.BaseViewModel
import pl.kacper.misterski.walldrill.core.di.AimAnalyzer
import pl.kacper.misterski.walldrill.db.color.ColorRepository
import pl.kacper.misterski.walldrill.domain.ColorAnalyzer
import javax.inject.Inject

@HiltViewModel
class CalibrationViewModel @Inject constructor(
    private val colorRepository: ColorRepository,
    @AimAnalyzer val colorAnalyzer: ColorAnalyzer
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(CalibrationUiState())
    val uiState = _uiState.asStateFlow()

    init {
        initAnalyzer()
    }

    private fun initAnalyzer() {
        Log.d(TAG, "initAnalyzer")
        viewModelScope.launch {
            _uiState.update { it.showProgress() }
            colorRepository.getSelectedColor()
                .onEach { color ->
                    color?.let { colorNotNull ->
                        colorAnalyzer.init(colorToDetect = colorNotNull.getColorObject()){ analyzerResult ->
                            _uiState.update { CalibrationUiState(detectedPoints = analyzerResult.detectedPoints) }
                        }
                        _uiState.update { it.hideProgress() }
                    } ?: kotlin.run {
                        _uiState.update { it.showError(R.string.no_color_selected) }
                    }

                }
                .catch { error ->
                    Log.e(TAG, "initAnalyzer error: ${error.message}")
                    error.printStackTrace()
                    _uiState.update { it.showError(R.string.no_color_selected) }
                }
                .collect()
        }
    }

}