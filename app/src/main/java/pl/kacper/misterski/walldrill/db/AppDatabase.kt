package pl.kacper.misterski.walldrill.db

import android.content.Context
import androidx.room.Room
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class AppDatabase @Inject constructor(@ApplicationContext context: Context) {

    companion object {
        const val DB_NAME = "WALL_DRILL_DN"
    }

    val db = Room.databaseBuilder(
        context,
        AppRoom::class.java, DB_NAME
    ).build()
}