/*
 *
 *  * Copyright 2020 Cristian Menárguez González
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *    http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 *
 */

package com.cristianmg.common_objects

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
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
        val locationEntity: FarmerLocationEntity? = null)