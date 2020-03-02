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

package com.cristianmg.sample.features.farm

import androidx.paging.toLiveData
import com.cristianmg.sample.core.di.qualifiers.Disk
import com.cristianmg.sample.core.platform.BaseViewModel
import com.cristianmg.sample.data.repository.farm.FarmRepository
import com.cristianmg.sample.domain.UpdateFarmsCase
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