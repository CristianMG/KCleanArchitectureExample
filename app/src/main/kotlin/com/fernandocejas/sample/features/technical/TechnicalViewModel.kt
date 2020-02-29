package com.fernandocejas.sample.features.technical

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fernandocejas.sample.core.platform.BaseViewModel
import com.fernandocejas.sample.data.repository.task.TaskRepository
import com.fernandocejas.sample.domain.Authenticator
import com.fernandocejas.sample.domain.CompleteTaskCase
import com.fernandocejas.sample.domain.model.Task
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class TechnicalViewModel @Inject constructor(
        private val authenticator: Authenticator,
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