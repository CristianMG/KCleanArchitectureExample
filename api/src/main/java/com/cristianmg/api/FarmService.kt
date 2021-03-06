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

package com.cristianmg.api

import com.cristianmg.api.functional.RetrofitCall
import com.cristianmg.common_objects.FarmEntity
import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Inject

class FarmService @Inject constructor(retrofit: Retrofit) {

    private val farmApi by lazy { ApiImpl(retrofit) }

    fun farms() = RetrofitCall(farmApi.farms())

    inner class ApiImpl(retrofit: Retrofit) : FarmApi {
        private val farmApi by lazy { retrofit.create(FarmApi::class.java) }

        override fun farms(): Call<List<FarmEntity>> = farmApi.farms()

    }

}
