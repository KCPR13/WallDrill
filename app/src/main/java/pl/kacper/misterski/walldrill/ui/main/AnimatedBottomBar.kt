package pl.kacper.misterski.walldrill.ui.main

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import pl.kacper.misterski.walldrill.R
import pl.kacper.misterski.walldrill.ui.theme.BottomBarIconSize
import pl.kacper.misterski.walldrill.ui.theme.WallDrillTheme

@Composable
fun AnimatedBottomBar(
    modifier: Modifier = Modifier,
    show: Boolean,
    onSettingsClick: () -> Unit,
    onFolderClick: () -> Unit,
    onAimClick: () -> Unit
) {
    AnimatedVisibility(
        visible = show,
    ) {
        BottomAppBar(
            modifier = modifier,
            actions = {
                BottomBarAction(icon = R.drawable.ic_settings, onClick = onSettingsClick)
                BottomBarAction(icon = R.drawable.ic_folder, onClick = onFolderClick)
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = onAimClick,
                    containerColor = colorResource(id = R.color.mili)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_shooting),
                        contentDescription = null
                    )
                }

            },
        )
    }
}

@Composable
fun BottomBarAction(modifier: Modifier = Modifier, @DrawableRes icon: Int, onClick: () -> Unit) {
    IconButton(modifier = modifier,onClick = { onClick.invoke() }) {
        Image(modifier= Modifier.size(BottomBarIconSize),painter = painterResource(id = icon), contentDescription = null,
            colorFilter = ColorFilter.tint(Color.Black))

    }

}


@Preview
@Composable
fun AnimatedBottomBarPreview() {
    WallDrillTheme {
        AnimatedBottomBar(modifier = Modifier,
            true,
            {},
            {},
            {})
    }
}
