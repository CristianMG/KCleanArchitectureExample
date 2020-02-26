package com.fernandocejas.sample.features.admin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fernandocejas.sample.core.platform.BaseViewModel
import com.fernandocejas.sample.domain.AssignTaskLessWorkloadTechnical
import com.fernandocejas.sample.domain.model.Task
import com.fernandocejas.sample.domain.model.TypeTask
import java.util.*
import javax.inject.Inject

class AdminViewModel @Inject constructor(
        private val assignTaskLessWorkloadTechnical: AssignTaskLessWorkloadTechnical
) : BaseViewModel() {

    var durationHours: MutableLiveData<String> = MutableLiveData()
    var durationMinutes: MutableLiveData<String> = MutableLiveData()


    var description: MutableLiveData<String> = MutableLiveData()
    var typeTask: MutableLiveData<Int> = MutableLiveData()

    private var _typeNotSelectionError: MutableLiveData<Boolean> = MutableLiveData()
    var typeNotSelectionError: LiveData<Boolean> = _typeNotSelectionError


    fun newTask() {
        typeTask.value?.let { typeTaskId ->
            val task = Task(UUID.randomUUID().toString(), TypeTask.values()[typeTaskId], getTotalDuration(), Calendar.getInstance())
            assignTaskLessWorkloadTechnical(AssignTaskLessWorkloadTechnical.Params(task)) {
                it.fold(::handleFailure, ::handleSuccessful)
            }
        } ?: run {
            _typeNotSelectionError.value = true
        }
    }


    private fun handleSuccessful(unit: Unit) {

    }



    private fun getTotalDuration(): Int {
        var sum = 0
        sum += durationHours.value?.toIntOrNull() ?: 0 * 60 * 60
        sum += durationMinutes.value?.toIntOrNull() ?: 0 * 60
        return sum
    }

}