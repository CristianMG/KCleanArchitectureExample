package com.fernandocejas.sample.domain.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Task(
        val id: String,
        val typeTask: TypeTask,
        val userId: String,
        val description: String,
        val secondsToComplete: Int,
        val date: Calendar,
        var complete: Boolean
) : Parcelable {


}