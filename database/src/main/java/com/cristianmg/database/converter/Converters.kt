/*
 *
 *  * Copyright 2020 Cristian Menárguez González
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *    http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 *
 */

package com.cristianmg.database.converter

import androidx.room.TypeConverter
import com.cristianmg.sample.data.repository.mapping.DateFormat
import java.util.*


class Converters {

    @TypeConverter
    fun fromString(value: String): List<Int> {
        return if (value.isNotBlank()) value.split(",").map { it.toInt() } else listOf()
    }

    @TypeConverter
    fun fromArrayList(list: List<Int>): String {
        return list.joinToString(",")
    }


    @TypeConverter
    fun fromString(value: Calendar): String =
            DateFormat.instance.parseCalendarToDatabaseFormat(value)


    @TypeConverter
    fun fromArrayList(value:String): Calendar =
            DateFormat.instance.parseDatabaseFormatToCalendar(value)


}