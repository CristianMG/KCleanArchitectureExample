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

import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DateFormat @Inject constructor(){

    private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    fun parseCalendarToDatabaseFormat(date: Calendar): String =
            simpleDateFormat.format(date.time)

    fun parseDatabaseFormatToCalendar(value: String): Calendar {
        val calendar = Calendar.getInstance()
        calendar.time = simpleDateFormat.parse(value)
        return calendar
    }

    companion object {
        val instance = DateFormat()
    }


}