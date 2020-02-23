package com.fernandocejas.sample.data.entity

import androidx.room.*

@Entity(
        tableName = "task",
        foreignKeys = [ForeignKey(
                entity = UserEntity::class,
                parentColumns = arrayOf("id"),
                childColumns = arrayOf("user_id"))]
)
data class TaskEntity(
        @PrimaryKey
        @ColumnInfo(name = "user_id")
        val userId: String,
        @ColumnInfo(name = "name")
        val name: String
)