package com.fernandocejas.sample.domain.model

import android.content.Context
import com.fernandocejas.sample.R

enum class TypeTask(val idTask: Int,
                    val resourceSkill: Int,
                    val taskTypeResource: Int,
                    val resourceImage: Int) {

    PRODUCT_SUPPLIER(1, R.string.product_supplier, R.string.replenish_supplier, R.drawable.ic_supplier),
    COLLECTOR(2, R.string.collector, R.string.collector, R.drawable.ic_ticket_collector),
    WRAPPER(3, R.string.wrapper, R.string.wrapper, R.drawable.ic_box),
    UNKNOWN(-1, 0, 0, 0);

    companion object {


        fun getTaskTypeStringFromIndex(index: Int, context: Context): String = context.getString(TypeTask.values()[index].taskTypeResource)


        fun getValuesResources(): List<Int> =
                values().filter { it != UNKNOWN }.map { it.resourceSkill }

        fun getTaskTypeResource(): List<Int> =
                values().filter { it != UNKNOWN }.map { it.taskTypeResource }

        fun getTasksFromInt(list: List<Int>): List<TypeTask>  =
                list.mapNotNull { id -> values().firstOrNull { it.idTask == id } }

    }
}