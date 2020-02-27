package com.fernandocejas.sample.data.repository

import androidx.room.TypeConverter
import com.fernandocejas.sample.data.repository.mapping.DateFormat
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