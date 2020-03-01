package com.fernandocejas.sample.data.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fernandocejas.sample.domain.model.Farm
import com.google.gson.annotations.SerializedName


@Entity(tableName = "farm")
data class FarmEntity(
        @PrimaryKey
        @SerializedName("farmer_id")
        @ColumnInfo(name = "farmer_id")
        val farmerID: String,

        @SerializedName("category")
        @ColumnInfo(name = "category")
        val category: String? = null,

        @SerializedName("item")
        @ColumnInfo(name = "item")
        val item: String? = null,

        @SerializedName("zipcode")
        @ColumnInfo(name = "zipcode")
        val zipcode: String? = null,

        @SerializedName("phone1")
        @ColumnInfo(name = "phone1")
        val phone: String? = null,

        @SerializedName("location_1")
        @Embedded
        val location: FarmerLocation? = null) {

    fun toFarm(): Farm =
            Farm(farmerID, category ?: "", item ?: "", zipcode ?: "", phone ?: "", location)

}