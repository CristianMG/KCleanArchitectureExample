package com.fernandocejas.sample.data.repository

import androidx.room.TypeConverter


class Converters {

    @TypeConverter
    fun fromString(value: String): List<Int> {
        return if (value.isNotBlank()) value.split(",").map { it.toInt() } else listOf()
    }

    @TypeConverter
    fun fromArrayList(list: List<Int>): String {
        return list.joinToString(",")
    }

}