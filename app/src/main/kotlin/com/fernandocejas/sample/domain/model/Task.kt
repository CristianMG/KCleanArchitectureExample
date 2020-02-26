package com.fernandocejas.sample.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Task(
        val id: String,
        val typeTask: TypeTask,
        val secondsToComplete:Int,
        val date:Calendar
) : Parcelable