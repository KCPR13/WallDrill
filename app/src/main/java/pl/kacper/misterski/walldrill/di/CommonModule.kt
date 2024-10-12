package pl.kacper.misterski.walldrill.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pl.kacper.misterski.walldrill.domain.ResourceProvider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CommonModule {
    @Provides
    @Singleton
    fun provideResourceProvider(
        @ApplicationContext context: Context,
    ) = ResourceProvider(context)
}
