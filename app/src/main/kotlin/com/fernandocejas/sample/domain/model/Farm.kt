package com.fernandocejas.sample.domain.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.PrimaryKey
import com.fernandocejas.sample.data.entity.FarmerLocation
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Farm(
        val farmerID: String,
        val category: String,
        val item: String,
        val zipcode: String,
        val phone: String,
        val location: FarmerLocation?
) : Parcelable