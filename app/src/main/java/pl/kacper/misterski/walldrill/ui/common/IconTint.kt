package pl.kacper.misterski.walldrill.ui.common

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter

@Composable
fun getIconTint(): ColorFilter {
    return if (isSystemInDarkTheme()) {
        ColorFilter.tint(Color.White)
    } else {
        ColorFilter.tint(Color.Black)
    }
}

@Composable
fun getIconTintColor(): Color {
    return if (isSystemInDarkTheme()) {
        Color.White
    } else {
        Color.Black
    }
}