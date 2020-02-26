package com.fernandocejas.sample.data.repository.mapping

import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

class DateFormat @Singleton @Inject constructor() {

    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())


    fun parseCalendarToDatabaseFormat(date: Calendar) =
            simpleDateFormat.format(date.time)

}