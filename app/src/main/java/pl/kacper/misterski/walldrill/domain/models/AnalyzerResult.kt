package pl.kacper.misterski.walldrill.domain.models

import androidx.compose.ui.graphics.Color

data class AnalyzerResult(val color: Color, val detectedPoints: List<Pair<Int, Int>>)