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

package com.cristianmg.sample.domain.model

import android.content.Context
import com.cristianmg.sample.R

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