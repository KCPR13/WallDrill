package pl.kacper.misterski.walldrill.db.color

import androidx.compose.ui.graphics.Color
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import pl.kacper.misterski.walldrill.db.color.Color.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class Color(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "color") val color: String = "",
    @ColumnInfo(name = "selected") val selected: Boolean = false,
) {

    fun getColorObject(): Color{
       return Color(color.toULong())
    }

    companion object {
        const val TABLE_NAME = "color"
    }
}