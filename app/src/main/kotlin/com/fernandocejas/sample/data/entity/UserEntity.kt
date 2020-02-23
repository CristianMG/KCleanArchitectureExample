package com.fernandocejas.sample.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.fernandocejas.sample.domain.model.User
import com.fernandocejas.sample.domain.model.UserRole
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
        val typeTaskAvailabe: List<Int>

) {

    /**
     * To provide a good layer's separation
     * @return User
     */
    fun toUserModel(): User =
            User(id, email, password, name, if (role == ROLE_ADMIN) UserRole.ROLE_ADMIN else UserRole.ROLE_TECHNICAL)


    companion object {
        const val ROLE_ADMIN = 1
        const val ROLE_TECHNICAL = 2
    }
}