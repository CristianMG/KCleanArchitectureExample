package com.fernandocejas.sample.features.farm

import androidx.paging.toLiveData
import com.fernandocejas.sample.core.di.qualifiers.Disk
import com.fernandocejas.sample.core.platform.BaseViewModel
import com.fernandocejas.sample.data.repository.farm.FarmRepository
import com.fernandocejas.sample.domain.UpdateFarmsCase
import javax.inject.Inject

class FarmViewModel @Inject constructor(
        @Disk private val cacheFarmRepository: FarmRepository,
        private val updateFarmsCase: UpdateFarmsCase
) : BaseViewModel() {

    val farmsDataSource = cacheFarmRepository.farms().toLiveData(PAGE_SIZE)

    fun updateFarms() =
            updateFarmsCase(UpdateFarmsCase.Params())

    companion object {
        const val PAGE_SIZE = 30
    }

}