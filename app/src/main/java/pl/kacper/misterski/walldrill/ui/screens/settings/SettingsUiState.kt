package pl.kacper.misterski.walldrill.ui.screens.settings

//TODO K separate files
enum class SettingsAction{
    COLORS,
    CALIBRATION
}

data class SettingsModel(val title: String, val action: SettingsAction)
data class SettingsUiState(val models: List< SettingsModel> = emptyList())