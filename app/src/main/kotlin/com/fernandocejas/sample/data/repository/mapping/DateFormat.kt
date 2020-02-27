package com.fernandocejas.sample.data.repository.mapping

import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

class DateFormat {

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