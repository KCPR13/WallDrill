package pl.kacper.misterski.walldrill.domain

import android.content.Context
import androidx.annotation.StringRes

class ResourceProvider(
    private val context: Context,
) {
    fun getString(
        @StringRes colorResId: Int,
    ) = context.getString(colorResId)
}
