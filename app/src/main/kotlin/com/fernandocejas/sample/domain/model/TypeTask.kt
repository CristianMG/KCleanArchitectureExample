package com.fernandocejas.sample.domain.model

import android.content.Context
import com.fernandocejas.sample.R

enum class TypeTask(val idTask: Int,
                    val resourceSkill: Int,
                    val taskTypeResource: Int) {

    PRODUCT_SUPPLIER(1, R.string.product_supplier, R.string.replenish_supplier),
    COLLECTOR(2, R.string.collector, R.string.collector),
    WRAPPER(3, R.string.wrapper, R.string.wrapper),
    UNKNOWN(-1, 0, 0);

    companion object {


        fun getTaskTypeStringFromIndex(index: Int, context: Context): String = context.getString(TypeTask.values().get(index).taskTypeResource)


        fun getValuesResources(): List<Int> =
                values().filter { it != UNKNOWN }.map { it.resourceSkill }

        fun getTaskTypeResource(): List<Int> =
                values().filter { it != UNKNOWN }.map { it.taskTypeResource }
    }
}