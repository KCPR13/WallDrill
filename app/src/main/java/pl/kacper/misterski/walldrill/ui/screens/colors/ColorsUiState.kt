package pl.kacper.misterski.walldrill.ui.screens.colors

data class ColorsUiState(val progress: Boolean = false, val colors: List<ColorModel> = emptyList()){
    fun showProgress() = this.copy(progress = true)

    fun updateList(colors: List<ColorModel>) = this.copy(progress = false, colors = colors)
}