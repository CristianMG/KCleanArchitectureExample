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

package com.cristianmg.domain


import com.cristianmg.model.exception.Failure
import com.cristianmg.common_objects.functional.Either
import com.cristianmg.common_objects.functional.flatMap
import com.cristianmg.model.Task
import com.cristianmg.model.User
import com.cristianmg.repositories.TaskRepository
import com.cristianmg.repositories.UserRepository
import com.cristianmg.repositories.di.qualifiers.Disk
import javax.inject.Inject

class AssignTaskLessWorkloadTechnical
@Inject constructor(
        @Disk
        private val userRepository: UserRepository,
        @Disk
        private val taskRepository: TaskRepository) : UseCase<User, AssignTaskLessWorkloadTechnical.Params>() {

    override suspend fun run(params: Params): Either<Failure, User> =
            userRepository.getUserBySkillLessWorkloadToday(params.task.date, params.task.typeTask)
                    .flatMap { user ->
                        taskRepository.insertTaskToUser(params.task, user)
                                .flatMap { Either.wrapFunction { user } }
                    }

    data class Params(val task: Task)
}
