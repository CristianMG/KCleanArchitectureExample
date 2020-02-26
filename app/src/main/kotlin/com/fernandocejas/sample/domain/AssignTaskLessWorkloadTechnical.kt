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
package com.fernandocejas.sample.domain

import com.fernandocejas.sample.core.functional.flatMap
import com.fernandocejas.sample.core.interactor.UseCase
import com.fernandocejas.sample.data.repository.task.TaskRepository
import com.fernandocejas.sample.data.repository.user.UserRepository
import com.fernandocejas.sample.domain.model.Task
import com.fernandocejas.sample.domain.model.User
import java.util.*
import javax.inject.Inject

class AssignTaskLessWorkloadTechnical
@Inject constructor(
        private val userRepository: UserRepository,
        private val taskRepository: TaskRepository) : UseCase<Unit, AssignTaskLessWorkloadTechnical.Params>() {

    override suspend fun run(params: Params) =
            userRepository.getUserBySkillLessWorkloadToday(Calendar.getInstance(), params.task.typeTask)
                    .flatMap {
                        taskRepository.insertTaskToUser(params.task, it)
                    }

    data class Params(val task: Task)
}
