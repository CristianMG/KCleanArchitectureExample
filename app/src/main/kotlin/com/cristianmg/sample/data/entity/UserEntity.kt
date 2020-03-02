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

package com.cristianmg.sample.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.cristianmg.sample.domain.model.TypeTask
import com.cristianmg.sample.domain.model.User
import com.cristianmg.sample.domain.model.UserRole
import com.google.gson.annotations.SerializedName

@Entity(
        tableName = "user",
        indices = [Index("id")]
)
data class UserEntity(

        @SerializedName("id")
        @PrimaryKey
        @ColumnInfo(name = "id")
        val id: String,

        @SerializedName("email")
        @ColumnInfo(name = "email")
        val email: String,

        @SerializedName("password")
        @ColumnInfo(name = "password")
        val password: String,

        @SerializedName("name")
        @ColumnInfo(name = "name")
        val name: String,

        @SerializedName("role")
        @ColumnInfo(name = "role")
        val role: Int,

        @SerializedName("typeTaskAvailable")
        @ColumnInfo(name = "typeTaskAvailable")
        val typeTaskAvailable: List<Int>

) {

    /**
     * To provide a good layer's separation
     * @return User
     */
    fun toUserModel(): User =
            User(id, email, password, name, if (role == ROLE_ADMIN) UserRole.ROLE_ADMIN else UserRole.ROLE_TECHNICAL, TypeTask.getTasksFromInt(typeTaskAvailable))


    companion object {
        const val ROLE_ADMIN = 1
        const val ROLE_TECHNICAL = 2
    }
}