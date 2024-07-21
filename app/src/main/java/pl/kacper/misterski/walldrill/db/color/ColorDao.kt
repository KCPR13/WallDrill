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

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import pl.kacper.misterski.walldrill.db.base.BaseDao
import pl.kacper.misterski.walldrill.db.color.Color.Companion.TABLE_NAME

@Dao
interface ColorDao : BaseDao<Color> {
    @Query("SELECT * FROM $TABLE_NAME")
    fun getColors(): Flow<List<Color>?>

    @Query("UPDATE $TABLE_NAME SET selected = :selectedValue")
    suspend fun uncheckSelectedColor(selectedValue: Boolean = false)

    @Query("UPDATE $TABLE_NAME SET selected = :selectedValue WHERE id = :colorId")
    suspend fun setColorChecked(
        colorId: Int,
        selectedValue: Boolean = true,
    )

    @Query("SELECT COUNT(*) FROM $TABLE_NAME")
    suspend fun getSavedColorsSize(): Int

    @Query("SELECT * FROM $TABLE_NAME WHERE selected = :selectedValue LIMIT 1")
    fun getSelectedColor(selectedValue: Boolean = true): Flow<Color?>

    @Query("DELETE FROM $TABLE_NAME WHERE color = :color")
    fun removeColor(color: String)
}
