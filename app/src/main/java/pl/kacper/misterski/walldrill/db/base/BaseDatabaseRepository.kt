package pl.kacper.misterski.walldrill.db.base

import kotlinx.coroutines.flow.Flow

abstract class BaseDatabaseRepository<Entity, Dao> {

    abstract fun getDao(): Dao

    val daoInstance: Dao by lazy { getDao() }

    abstract suspend fun insert(toInsert: Entity)

    abstract fun getAll(): Flow<List<Entity>?>

    abstract suspend fun remove(entity: Entity)

}