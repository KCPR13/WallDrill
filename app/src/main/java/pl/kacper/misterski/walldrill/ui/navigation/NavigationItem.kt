package pl.kacper.misterski.walldrill.ui.navigation

sealed class NavigationItem(
    val route: String,
) {
    data object Calibration : NavigationItem(Screen.CALIBRATION.name)

    data object Setup : NavigationItem(Screen.SETUP.name)

    data object ColorDetection : NavigationItem(Screen.COLOR_DETECTION.name)

    data object Settings : NavigationItem(Screen.SETTINGS.name)

    data object Aim : NavigationItem(Screen.AIM.name)

    data object Folder : NavigationItem(Screen.FOLDER.name)

    data object Colors : NavigationItem(Screen.COLORS.name)
}
