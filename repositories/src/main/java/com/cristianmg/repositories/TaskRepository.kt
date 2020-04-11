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

package com.cristianmg.repositories

import com.cristianmg.model.exception.Failure
import com.cristianmg.common_objects.functional.Either
import com.cristianmg.database.dao.TaskDao
import com.cristianmg.model.Task
import com.cristianmg.model.User
import com.cristianmg.repositories.mapper.TaskMapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface TaskRepository {

    fun task(): Either<Failure, List<Task>>
    fun insertTaskToUser(task: Task, user: User): Either<Failure, Unit>
    fun observeTaskUserChange(userId: String): Flow<Either<Failure, List<Task>>>
    fun completeTask(task: Task): Either<Failure, Unit>

    class Disk @Inject constructor(
            private val taskMapper: TaskMapper,
            private val cache: TaskDao) : TaskRepository {

        override fun task(): Either<Failure, List<Task>> =
                Either.wrapFunction { taskMapper.mapListToModel(cache.getAll()) }

        override fun insertTaskToUser(task: Task, user: User) = Either.wrapFunction {
            task.userId = user.id
            cache.insert(taskMapper.mapToEntity(task))
        }

        override fun completeTask(task: Task): Either<Failure, Unit> =
                Either.wrapFunction {
                    cache.updateTask(taskMapper.mapToEntity(task))
                }

        /**
         * One of them is deprecated and another is Experimental O_O
         */
        @ExperimentalCoroutinesApi
        override fun observeTaskUserChange(userId: String): Flow<Either<Failure, List<Task>>> {
            return cache.getTaskByUserUntilChanged(userId)
                    .map { Either.Right(taskMapper.mapListToModel(it)) }
                    .catch { Either.Left(it) }
        }

    }

}
