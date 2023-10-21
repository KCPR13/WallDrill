package pl.kacper.misterski.walldrill.ui.screens.colors

import pl.kacper.misterski.walldrill.db.color.Color

data class ColorsUiState( val colors: List<Color> = emptyList()){

    fun updateList(colors: List<Color>) = this.copy(colors = colors)
}