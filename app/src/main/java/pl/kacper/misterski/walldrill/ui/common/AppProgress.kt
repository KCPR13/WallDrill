package pl.kacper.misterski.walldrill.ui.common

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import pl.kacper.misterski.walldrill.ui.theme.Mili

@Composable
fun AppProgress(modifier: Modifier) {
    CircularProgressIndicator(
        modifier = modifier,
        color = Mili
    )
}


@Preview
@Composable
fun AppProgressPreview() {
    MaterialTheme{
        AppProgress(Modifier)
    }

}