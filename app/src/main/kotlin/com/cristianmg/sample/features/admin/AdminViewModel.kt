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

package com.cristianmg.sample.features.admin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cristianmg.sample.core.platform.BaseViewModel
import com.cristianmg.sample.domain.AssignTaskLessWorkloadTechnical
import com.cristianmg.sample.domain.Authenticator
import com.cristianmg.sample.domain.model.Task
import com.cristianmg.sample.domain.model.TypeTask
import com.cristianmg.sample.domain.model.User
import java.util.*
import javax.inject.Inject

class AdminViewModel @Inject constructor(
        private val authenticator: Authenticator,
        private val assignTaskLessWorkloadTechnical: AssignTaskLessWorkloadTechnical
) : BaseViewModel() {

    var durationHours: MutableLiveData<String> = MutableLiveData()
    var durationMinutes: MutableLiveData<String> = MutableLiveData()


    var description: MutableLiveData<String> = MutableLiveData()
    var typeTask: MutableLiveData<Int> = MutableLiveData()


    private var _typeNotSelectionError: MutableLiveData<Boolean> = MutableLiveData()
    var typeNotSelectionError: LiveData<Boolean> = _typeNotSelectionError

    private var _taskAddSuccessful: MutableLiveData<User> = MutableLiveData()
    var taskAddSuccessful: LiveData<User> = _taskAddSuccessful


    fun newTask() {
        typeTask.value?.let { typeTaskId ->
            val task = Task(UUID.randomUUID().toString(), TypeTask.values()[typeTaskId], "", description.value ?: "", getTotalDuration(), Calendar.getInstance(), false)
            assignTaskLessWorkloadTechnical(AssignTaskLessWorkloadTechnical.Params(task)) {
                it.fold(::handleFailure, ::handleSuccessful)
            }
        } ?: run {
            _typeNotSelectionError.value = true
        }
    }


    private fun handleSuccessful(user: User) {
        _taskAddSuccessful.value = user
    }

    fun newTaskAgain() {
        description.value = ""
        typeTask.value = null
        durationHours.value = null
        durationMinutes.value = null
    }


    private fun getTotalDuration(): Int {
        var sum = durationHours.value?.toInt() ?: 0
        sum *= 60 * 60
        return sum
    }

    fun closeSession() {
        authenticator.closeSession()
    }


}