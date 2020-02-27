/**
 * Copyright (C) 2018 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fernandocejas.sample.data.repository.task

import com.fernandocejas.sample.core.exception.Failure
import com.fernandocejas.sample.core.functional.Either
import com.fernandocejas.sample.data.AppDatabase
import com.fernandocejas.sample.data.entity.TaskEntity
import com.fernandocejas.sample.data.repository.mapping.DateFormat
import com.fernandocejas.sample.domain.model.Task
import com.fernandocejas.sample.domain.model.User
import javax.inject.Inject

interface TaskRepository {

    fun task(): Either<Failure, List<Task>>
    fun insertTaskToUser(task: Task, user: User): Either<Failure, Unit>

    class Disk @Inject constructor(
            private val appDatabase: AppDatabase,
            private val dateFormat: DateFormat) : TaskRepository {

        private val cache: TaskDao get() = appDatabase.taskDAO()

        override fun task(): Either<Failure, List<Task>> = Either.wrapFunction { cache.getAll().map { it.toTaskModel() } }

        override fun insertTaskToUser(task: Task, user: User) = Either.wrapFunction {
            cache.insert(TaskEntity(task.id, task.typeTask.idTask, user.id, task.secondsToComplete, task.date))
        }
    }

}
