package com.fernandocejas.sample.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
        val id: String,
        val email: String,
        val password: String,
        val name: String,
        val availableTasks: List<AvailableTasks> = mutableListOf()
) : Parcelable