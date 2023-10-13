package pl.kacper.misterski.walldrill.domain.interfaces

import androidx.compose.ui.graphics.Color

interface DetectColorListener{
    fun onColorDetected(color: Color)
}