package pl.kacper.misterski.walldrill.db

import androidx.room.Database
import androidx.room.RoomDatabase
import pl.kacper.misterski.walldrill.db.color.Color
import pl.kacper.misterski.walldrill.db.color.ColorDao

@Database(entities = [Color::class], version = 1)
abstract class AppRoom : RoomDatabase() {
    abstract fun configurationDao(): ColorDao
}