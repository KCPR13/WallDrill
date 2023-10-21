package pl.kacper.misterski.walldrill.ui.screens.settings

import android.content.Context
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import pl.kacper.misterski.walldrill.R
import pl.kacper.misterski.walldrill.core.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(): BaseViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState = _uiState.asStateFlow()


    fun fetchModels(context: Context){

        val COLORDETECTION = SettingsModel(context.getString(R.string.colors), SettingsAction.COLORS)
        val calibration = SettingsModel(context.getString(R.string.calibration), SettingsAction.CALIBRATION)

        _uiState.update { SettingsUiState(listOf(calibration,COLORDETECTION)) }
    }


}