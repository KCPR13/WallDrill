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
    fun getColorObject(): Color = Color(color.toULong())

    companion object {
        const val TABLE_NAME = "color"
    }
}
