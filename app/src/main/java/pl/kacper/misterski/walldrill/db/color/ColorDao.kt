package pl.kacper.misterski.walldrill.db.color

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import pl.kacper.misterski.walldrill.db.base.BaseDao
import pl.kacper.misterski.walldrill.db.color.Color.Companion.TABLE_NAME


@Dao
interface ColorDao: BaseDao<Color> {
    @Query("SELECT * FROM $TABLE_NAME")
    fun getColors() : Flow<List<Color>?>

    @Query("UPDATE $TABLE_NAME SET selected = :selectedValue")
    suspend fun uncheckSelectedColor(selectedValue: Boolean = false)

    @Query("UPDATE $TABLE_NAME SET selected = :selectedValue WHERE id = :colorId")
    suspend fun setColorChecked(colorId: Int, selectedValue: Boolean = true)

    @Query("SELECT COUNT(*) FROM $TABLE_NAME")
    suspend fun getSavedColorsSize(): Int

    @Query("SELECT * FROM $TABLE_NAME WHERE selected = :selectedValue LIMIT 1")
    fun getSelectedColor(selectedValue: Boolean = true) : Flow<Color?>

}