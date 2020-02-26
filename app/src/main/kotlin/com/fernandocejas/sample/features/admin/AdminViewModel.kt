package com.fernandocejas.sample.features.admin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fernandocejas.sample.core.platform.BaseViewModel
import javax.inject.Inject

class AdminViewModel @Inject constructor() : BaseViewModel() {

    var durationHours: MutableLiveData<String> = MutableLiveData()
    var durationMinutes: MutableLiveData<String> = MutableLiveData()


    var description: MutableLiveData<String> = MutableLiveData()
    var typeTask: MutableLiveData<Int> = MutableLiveData()

    private var _typeNotSelectionError: MutableLiveData<Boolean> = MutableLiveData()
    var typeNotSelectionError: LiveData<Boolean> = _typeNotSelectionError


    fun getTotalDuration(): Int {
        var sum = 0
        sum += durationHours.value?.toIntOrNull() ?: 0 * 60 * 60
        sum += durationMinutes.value?.toIntOrNull() ?: 0 * 60
        return sum
    }

    fun newTask() {
        if (typeTask.value != null) {

        } else {
            _typeNotSelectionError.value = true
        }
    }

}