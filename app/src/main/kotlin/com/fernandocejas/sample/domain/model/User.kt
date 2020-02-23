package com.fernandocejas.sample.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
        val id: String,
        val email: String,
        val password: String,
        val name: String,
        val role: UserRole,
        val availableTasks: List<TypeTask> = mutableListOf()
) : Parcelable