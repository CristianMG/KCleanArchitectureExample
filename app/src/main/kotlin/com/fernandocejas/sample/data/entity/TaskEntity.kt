package com.fernandocejas.sample.data.entity

import androidx.room.*
import com.fernandocejas.sample.data.repository.mapping.DateFormat
import com.fernandocejas.sample.domain.model.Task
import com.fernandocejas.sample.domain.model.TypeTask

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
        val complete: Boolean
) {

    /**
     * To provide a good layer's separation
     * @return User
     */
    fun toTaskModel(dateFormat: DateFormat): Task =
            Task(id, parseTypetask(), userId, description, duration, dateFormat.parseCalendarToDatabaseFormat(date), complete)

    /**
     * Disregarded parser only add new items to TypeTask enum
     */
    private fun parseTypetask(): TypeTask =
            TypeTask.values().firstOrNull { tp -> tp.idTask == typeTask } ?: TypeTask.UNKNOWN


    companion object {
        const val PRODUCT_SUPPLIER = 1
        const val COLLECTOR = 2
        const val WRAPPER = 3
    }
}