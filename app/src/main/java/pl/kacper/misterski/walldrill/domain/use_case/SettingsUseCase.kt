package pl.kacper.misterski.walldrill.domain.use_case

import kotlinx.coroutines.flow.flow
import pl.kacper.misterski.walldrill.R
import pl.kacper.misterski.walldrill.domain.ResourceProvider
import pl.kacper.misterski.walldrill.ui.screens.settings.SettingsAction
import pl.kacper.misterski.walldrill.ui.screens.settings.SettingsModel

class SettingsUseCase(private val resourceProvider: ResourceProvider,) {

    operator fun invoke() = flow {
        val colorDetection =
            SettingsModel(
                resourceProvider.getString(R.string.colors),
                SettingsAction.COLORS,
            )
        val calibration =
            SettingsModel(
                resourceProvider.getString(R.string.calibration),
                SettingsAction.CALIBRATION,
            )

        val result = buildList {
            add(colorDetection)
            add(calibration)
        }

        emit(result)

    }
}