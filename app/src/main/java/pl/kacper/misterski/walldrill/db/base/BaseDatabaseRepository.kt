package pl.kacper.misterski.walldrill.db.base

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

abstract class BaseDatabaseRepository<Entity, Dao> @Inject constructor() {

    protected abstract fun getDao(): Dao

    protected  val dao: Dao by lazy { getDao() }

    abstract suspend fun insert(toInsert: Entity)

    abstract fun getAll(): Flow<List<Entity>?>

}