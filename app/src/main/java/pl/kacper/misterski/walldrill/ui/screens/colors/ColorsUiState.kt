package pl.kacper.misterski.walldrill.ui.screens.colors

import pl.kacper.misterski.walldrill.db.color.Color

data class ColorsUiState(val progress: Boolean = false, val colors: List<Color> = emptyList()){
    fun showProgress() = this.copy(progress = true)

    fun updateList(colors: List<Color>) = this.copy(progress = false, colors = colors)
}