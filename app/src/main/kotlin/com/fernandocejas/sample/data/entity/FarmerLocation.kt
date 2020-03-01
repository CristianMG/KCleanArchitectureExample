package com.fernandocejas.sample.data.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FarmerLocation(
        @SerializedName("latitude")
        @ColumnInfo(name = "latitude")
        val latitude: Double,
        @SerializedName("longitude")
        @ColumnInfo(name = "longitude")
        val longitude: Double,
        @SerializedName("human_address")
        @ColumnInfo(name = "human_address")
        val humanAddress: String
) : Parcelable