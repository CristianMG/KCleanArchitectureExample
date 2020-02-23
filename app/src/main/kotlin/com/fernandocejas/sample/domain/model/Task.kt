package com.fernandocejas.sample.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Task(
        val id: String,
        val typeTask: TypeTask
) : Parcelable