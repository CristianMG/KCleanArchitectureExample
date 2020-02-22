package com.fernandocejas.sample.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.fernandocejas.sample.domain.model.AvailableTasks
import com.fernandocejas.sample.domain.model.User

@Entity(
        tableName = "user",
        indices = [Index("id")]
)
data class UserEntity(

        @PrimaryKey
        @ColumnInfo(name = "id")
        val id: String,

        @ColumnInfo(name = "email")
        val email: String,

        @ColumnInfo(name = "password")
        val password: String,

        @ColumnInfo(name = "name")
        val name: String,

        @ColumnInfo(name = "availableTasks")
        val availableTasks: List<AvailableTasks> = mutableListOf()

) {

    /**
     * To provide the good layer's separation
     * @return User
     */
    fun toUserModel(): User =
            User(id, email, password, name, availableTasks)

}