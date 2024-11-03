package pl.kacper.misterski.walldrill.ui.screens.colors

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import pl.kacper.misterski.walldrill.R
import pl.kacper.misterski.walldrill.ui.common.getIconTint
import pl.kacper.misterski.walldrill.ui.theme.PaddingLarge
import pl.kacper.misterski.walldrill.ui.theme.WallDrillTheme

@Composable
fun EmptyColorsPlaceHolder(modifier: Modifier) {
    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_paint), contentDescription = null,
            colorFilter = getIconTint(),
        )
        Text(
            modifier = Modifier.padding(vertical = PaddingLarge),
            text = stringResource(R.string.no_colors_set),
        )
    }
}

@PreviewLightDark
@Composable
fun EmptyColorsPlaceHolderPreview() {
    WallDrillTheme {
        EmptyColorsPlaceHolder(
            modifier = Modifier,
        )
    }
}
