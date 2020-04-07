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

package com.cristianmg.model


enum class TypeTask(val idTask: Int) {

    PRODUCT_SUPPLIER(1),
    COLLECTOR(2),
    WRAPPER(3),
    UNKNOWN(-1);


    companion object {

        fun getTypeTaskById(idTask: Int): TypeTask =
                values().toList().firstOrNull { it.idTask == idTask } ?: UNKNOWN

        fun getTypeTaskById(list: List<Int>): List<TypeTask> =
                list.mapNotNull { id -> values().firstOrNull { it.idTask == id } }

        fun getTypeTaskList(): List<TypeTask> = values().filterNot { it == UNKNOWN }


    }
}