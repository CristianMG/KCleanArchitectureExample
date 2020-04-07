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

package com.cristianmg.sample.features.technical

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cristianmg.domain.Authenticator
import com.cristianmg.domain.CompleteTaskCase
import com.cristianmg.model.Task
import com.cristianmg.repositories.TaskRepository
import com.cristianmg.repositories.di.qualifiers.Disk
import com.cristianmg.sample.core.platform.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class TechnicalViewModel @Inject constructor(
        private val authenticator: Authenticator,
        @Disk
        private val taskRepository: TaskRepository,
        private val completeTaskCaseCase: CompleteTaskCase
) : BaseViewModel() {

    private val _taskUsers = MutableLiveData<List<Task>>()
    val taskUsers: LiveData<List<Task>> = _taskUsers

    fun observeMyTask() {
        authenticator.userLogged?.id
        viewModelScope.launch {
            taskRepository.observeTaskUserChange(authenticator.userLogged?.id ?: "")
                    .collect {
                        it.fold(::handleFailure, ::handleSucessfull)
                    }
        }
    }

    fun handleSucessfull(list: List<Task>) {
        _taskUsers.value = list
    }

    fun completeTask(task: Task) {
        completeTaskCaseCase(CompleteTaskCase.Params(task))
    }

}