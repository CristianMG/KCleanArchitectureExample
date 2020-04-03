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

package com.cristianmg.sample.data.repository.task

import androidx.room.*
import com.cristianmg.common_value_object.TaskEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Dao
interface TaskDao {


    @Query("""
            SELECT * FROM task WHERE task.user_id = :userId
            """)
    fun getTaskByUser(userId: String): Flow<List<TaskEntity>>

    fun getTaskByUserUntilChanged(userId: String) =
            getTaskByUser(userId).distinctUntilChanged()

    @Query("""
            SELECT * FROM task
            """)
    fun getAll(): List<TaskEntity>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: TaskEntity)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: List<TaskEntity>)

    @Update
    fun updateTask(task: TaskEntity)

    @Query("""
        SELECT * FROM task WHERE id = :id
    """)
    fun getById(id: String): TaskEntity?
}