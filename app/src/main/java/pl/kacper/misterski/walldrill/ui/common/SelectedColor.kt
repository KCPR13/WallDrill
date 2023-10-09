package pl.kacper.misterski.walldrill.ui.common

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import pl.kacper.misterski.walldrill.ui.theme.Mili

@Composable
fun SelectedColor(modifier: Modifier = Modifier,color: Color, drawBorder: Boolean) {
   var mod = modifier

    if (drawBorder){
        mod = mod.border(4.dp, Mili)
    }

    Box(modifier = mod
        .clip(RoundedCornerShape(4.dp))
        .drawBehind {
            drawRect(size = size, color = color)
        }
    )

}