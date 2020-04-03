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
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

import java.util.*


/**
 *  PRODUCT_SUPPLIER (R.string.product_supplier), COLLECTOR(R.string.collector), WRAPPER(R.string.wrapper);
 */
@Entity(
        tableName = "task",
        foreignKeys = [ForeignKey(
                entity = UserEntity::class,
                parentColumns = arrayOf("id"),
                childColumns = arrayOf("user_id"))]
)
data class TaskEntity(
        @PrimaryKey
        @ColumnInfo(name = "id")
        val id: String = UUID.randomUUID().toString(),
        @ColumnInfo(name = "type_task")
        val typeTask: Int,
        @ColumnInfo(name = "description")
        val description: String = "",
        @ColumnInfo(name = "user_id")
        val userId: String,
        @ColumnInfo(name = "duration")
        val duration: Int,
        @ColumnInfo(name = "date")
        val date: String,
        @ColumnInfo(name = "complete")
        var complete: Boolean
)