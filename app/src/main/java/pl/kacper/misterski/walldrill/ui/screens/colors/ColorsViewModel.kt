package pl.kacper.misterski.walldrill.ui.screens.colors

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class ColorsViewModel @Inject constructor(): ViewModel() {

    private val _uiState = MutableStateFlow(ColorsUiState())
    val uiState = _uiState.asStateFlow()

    fun fetchColors(){
        _uiState.update { it.showProgress() }
        //TODO K here fetch
        _uiState.update { it.updateList(emptyList()) }

    }


}