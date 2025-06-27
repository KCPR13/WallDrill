package pl.kacper.misterski.walldrill.ui.common

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import pl.kacper.misterski.walldrill.ui.theme.secondaryContainerDark
import pl.kacper.misterski.walldrill.ui.theme.secondaryContainerLight

@Composable
fun AppFab(
    iconResource: Int,
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    onClick: () -> Unit,
) {

    val colors = if (useDarkTheme) secondaryContainerDark else secondaryContainerLight


    FloatingActionButton(
        containerColor = colors,

        elevation = FloatingActionButtonDefaults.elevation(0.dp),
        onClick = onClick,
    ) {
        Icon(
            painter = painterResource(id = iconResource),
            contentDescription = null,
        )
    }
}

@Composable
fun AppFab(
    imageVector: ImageVector,
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    onClick: () -> Unit,
) {

    val colors = if (useDarkTheme) secondaryContainerDark else secondaryContainerLight

    FloatingActionButton(
        containerColor = colors,
        elevation = FloatingActionButtonDefaults.elevation(0.dp),
        onClick = onClick,
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = null,
        )
    }
}
