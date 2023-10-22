package pl.kacper.misterski.walldrill.ui.screens.calibration

import androidx.compose.material3.SnackbarHostState

data class CalibrationUiState(val detectedPoints: List<Pair<Int,Int>> = mutableListOf(), val snackbarHostState: SnackbarHostState = SnackbarHostState())