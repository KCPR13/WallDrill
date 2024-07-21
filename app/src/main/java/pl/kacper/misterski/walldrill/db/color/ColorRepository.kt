/*
 * Copyright 2024 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pl.kacper.misterski.walldrill.db.color

import pl.kacper.misterski.walldrill.db.AppDatabase
import pl.kacper.misterski.walldrill.db.base.BaseDatabaseRepository
import javax.inject.Inject

class ColorRepository
@Inject
constructor(private val appDatabase: AppDatabase) :
    BaseDatabaseRepository<Color, ColorDao>() {
    override fun getDao() = appDatabase.db.configurationDao()

    override fun getAll() = daoInstance.getColors()

    override suspend fun remove(entity: Color) = daoInstance.removeColor(entity.color)

    override suspend fun insert(toInsert: Color) = daoInstance.insert(toInsert)

    suspend fun uncheckSelectedColor() = daoInstance.uncheckSelectedColor()

    suspend fun setColorChecked(color: Color) = daoInstance.setColorChecked(color.id)

    suspend fun hasAnyColorSaved() = daoInstance.getSavedColorsSize() > 0

    fun getSelectedColor() = daoInstance.getSelectedColor()
}
