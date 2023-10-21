package pl.kacper.misterski.walldrill.domain.interfaces

import pl.kacper.misterski.walldrill.domain.models.AnalyzerResult


interface ColorListener {
    fun onColorDetected(analyzerResult: AnalyzerResult)
}
