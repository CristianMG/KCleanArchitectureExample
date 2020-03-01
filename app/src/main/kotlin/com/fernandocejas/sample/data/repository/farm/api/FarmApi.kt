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
package com.fernandocejas.sample.data.repository.farm.api

import com.fernandocejas.sample.data.entity.FarmEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

internal interface FarmApi {
    companion object {
        private const val FARMS_ENDPOINT = "resource/hma6-9xbg.json "
    }

    @GET(FARMS_ENDPOINT) fun farms(): Call<List<FarmEntity>>
}