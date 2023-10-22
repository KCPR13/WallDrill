package pl.kacper.misterski.walldrill.ui.screens.calibration

import androidx.annotation.StringRes
import androidx.compose.material3.SnackbarHostState

data class CalibrationUiState(
    val progress: Boolean = false, val detectedPoints: List<Pair<Int, Int>> = mutableListOf(),
    val snackbarHostState: SnackbarHostState = SnackbarHostState(),
    @StringRes val snackbarMessage: Int? = null
) {

    fun showProgress() = this.copy(progress = true)

    fun hideProgress() = this.copy(progress = false)

    fun showError(@StringRes message: Int) = this.copy(snackbarMessage = message)
}