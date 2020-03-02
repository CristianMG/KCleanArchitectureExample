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

package com.cristianmg.sample.domain

import com.cristianmg.sample.core.exception.Failure
import com.cristianmg.sample.core.functional.Either
import com.cristianmg.sample.data.repository.task.TaskRepository
import com.cristianmg.sample.domain.model.Task
import javax.inject.Inject

class CompleteTaskCase
@Inject constructor(
        private val taskRepository: TaskRepository) : UseCase<Unit, CompleteTaskCase.Params>() {

    override suspend fun run(params: Params):Either<Failure,Unit> =
            taskRepository.completeTask(params.task)


    data class Params(val task: Task)
}
