package pl.kacper.misterski.walldrill.ui.common

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import pl.kacper.misterski.walldrill.domain.extensions.isColorDark
import pl.kacper.misterski.walldrill.ui.theme.Mili

@Composable
fun SelectedColor(
    modifier: Modifier = Modifier, color: Color, drawBorder: Boolean,
    onRemove: (() -> Unit)? = null
) {
    var mod = modifier

    if (drawBorder) {
        mod = mod.border(4.dp, Mili)
    }

    Box(modifier = mod
        .clip(RoundedCornerShape(4.dp))
        .drawBehind {
            drawRect(size = size, color = color)
        }
    ) {
        onRemove?.let { removeColor ->
            Icon(
                tint = if (color.isColorDark()) Color.White else Color.Black,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(4.dp)
                    .clickable { removeColor.invoke() },
                imageVector = Icons.Outlined.Clear, contentDescription = null
            )
        }

    }

}


