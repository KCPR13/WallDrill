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

}