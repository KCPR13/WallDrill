package pl.kacper.misterski.walldrill.ui.main

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import pl.kacper.misterski.walldrill.core.BaseViewModel
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(): BaseViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
     val uiState = _uiState.asStateFlow()

    fun updatePermissionState(permissionGranted: Boolean) = _uiState.update { it.copy(
        permissionGranted = permissionGranted
    ) }

    fun updateBottomBarVisibility(showBottomBar: Boolean) = _uiState.update { it.copy(showBottomBar = showBottomBar) }

}
