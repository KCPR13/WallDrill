package pl.kacper.misterski.walldrill.ui.screens.colordetection

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ColorDetectionViewModel @Inject constructor(): ViewModel() {

    val _uiState = MutableStateFlow(Color.Black)
    val uiState = _uiState.asStateFlow()

}