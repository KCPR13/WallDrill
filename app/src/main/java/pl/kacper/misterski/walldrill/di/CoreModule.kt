package pl.kacper.misterski.walldrill.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pl.kacper.misterski.walldrill.data.db.AppRoom
import pl.kacper.misterski.walldrill.domain.ResourceProvider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {
    @Provides
    @Singleton
    fun provideResourceProvider(
        @ApplicationContext context: Context,
    ) = ResourceProvider(context)

    @Provides
    @Singleton
    fun provideAppRoom(
        @ApplicationContext context: Context,
    ) =
        Room
            .databaseBuilder(
                context,
                AppRoom::class.java,
                AppRoom.DB_NAME,
            )
            .build()

    @Provides
    @Singleton
    fun provideConfigurationDao(appRoom: AppRoom) = appRoom.colorDao()
}
