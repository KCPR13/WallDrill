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
import pl.kacper.misterski.walldrill.core.di.AimAnalyzer
import pl.kacper.misterski.walldrill.core.BaseViewModel
import pl.kacper.misterski.walldrill.db.color.ColorRepository
import pl.kacper.misterski.walldrill.domain.ColorAnalyzer
import pl.kacper.misterski.walldrill.domain.interfaces.ColorListener
import pl.kacper.misterski.walldrill.domain.models.AnalyzerResult
import javax.inject.Inject

@HiltViewModel
class CalibrationViewModel @Inject constructor(
    private val colorRepository: ColorRepository,
    @AimAnalyzer val colorAnalyzer: ColorAnalyzer
) : BaseViewModel(), ColorListener {

    private val _uiState = MutableStateFlow(CalibrationUiState())
    val uiState = _uiState.asStateFlow()

    fun initAnalyzer() {
        viewModelScope.launch {
            colorRepository.getSelectedColor()
                .onEach { color ->
                    color?.let {
                        colorAnalyzer.init(this@CalibrationViewModel, it.getColorObject())
                    } ?: kotlin.run {
                        // TODO K handle null
                    }

                }
                .catch {
                    Log.e(TAG, "initAnalyzer error")
                    it.printStackTrace()

                }.collect()
        }
    }

    fun disposeAnalyzer() = colorAnalyzer.dispose()

    override fun onColorDetected(analyzerResult: AnalyzerResult) {
        _uiState.update { CalibrationUiState(analyzerResult.detectedPoints) }
    }


}