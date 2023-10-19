package pl.kacper.misterski.walldrill.db.color

import pl.kacper.misterski.walldrill.db.AppDatabase
import pl.kacper.misterski.walldrill.db.base.BaseDatabaseRepository
import javax.inject.Inject

class ColorRepository @Inject constructor(private val appDatabase: AppDatabase)
    : BaseDatabaseRepository<Color, ColorDao>()
{

    override fun getDao() = appDatabase.db.configurationDao()

    override fun getAll() = daoInstance.getColors()

    override suspend fun remove(color: Color) = daoInstance.removeColor(color.color) // TODO K color.color

    override suspend fun insert(toInsert: Color) = daoInstance.insert(toInsert)

    suspend fun uncheckSelectedColor() =daoInstance.uncheckSelectedColor()

    suspend fun setColorChecked(color: Color) = daoInstance.setColorChecked(color.id)

    suspend fun hasAnyColorSaved() = daoInstance.getSavedColorsSize() > 0

    fun getSelectedColor() = daoInstance.getSelectedColor()
}