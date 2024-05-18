package pl.kacper.misterski.walldrill.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import kotlin.coroutines.CoroutineContext

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BackgroundScope

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainScope

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BackgroundDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainDispatcher

@Module
@InstallIn(SingletonComponent::class)
object CoreProvider {
    @BackgroundDispatcher
    @Provides
    fun provideBackgroundDispatcher() = Dispatchers.IO

    @MainDispatcher
    @Provides
    fun provideMainDispatcher() = Dispatchers.Main

}
