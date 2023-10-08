package pl.kacper.misterski.walldrill.ui.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(): ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
     val uiState = _uiState.asStateFlow()

    fun updatePermissionState(permissionGranted: Boolean) = _uiState.update { it.copy(
        permissionGranted = permissionGranted
    ) }

}