package pl.kacper.misterski.walldrill.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import pl.kacper.misterski.walldrill.domain.ResourceProvider
import pl.kacper.misterski.walldrill.domain.use_case.SettingsUseCase

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideSettingsUseCase(
        resourceProvider: ResourceProvider,
    ) = SettingsUseCase(resourceProvider)
}
