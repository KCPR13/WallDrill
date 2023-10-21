package pl.kacper.misterski.walldrill.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.kacper.misterski.walldrill.domain.ColorAnalyzer
import pl.kacper.misterski.walldrill.domain.enums.AnalyzerMode
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DetectColorAnalyzer


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AimAnalyzer

@Module
@InstallIn(SingletonComponent::class)
object AppProvider {

    @DetectColorAnalyzer
    @Provides
    fun provideDetectColorAnalyzer() = ColorAnalyzer(AnalyzerMode.DETECTION)

    @AimAnalyzer
    @Provides
    fun provideAimAnalyzer() = ColorAnalyzer(AnalyzerMode.AIM)

}