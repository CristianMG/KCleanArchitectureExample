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

package com.cristianmg.sample.data

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.cristianmg.sample.core.extension.loadJSONFromAsset
import com.cristianmg.common_value_object.UserEntity
import com.cristianmg.sample.domain.model.TypeTask
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class SampleData {

    companion object {

        fun getSampleData(context: Context): List<UserEntity> {
            val turnsType = object : TypeToken<List<UserEntity>>() {}.type
            return Gson().fromJson(context.loadJSONFromAsset("UserSampleData.json"), turnsType)
        }


        fun getSingleUser(context: Context): UserEntity {
            val turnsType = object : TypeToken<List<UserEntity>>() {}.type
            return Gson().fromJson<List<UserEntity>>(context.loadJSONFromAsset("UserSampleData.json"), turnsType).random()
        }


        @VisibleForTesting
        fun getUser(uuid:String = UUID.randomUUID().toString(),taskAvailable: List<TypeTask>, role: Int): UserEntity =
                UserEntity(uuid, "example@example.com", "example", "example", role, taskAvailable.map { it.idTask })


    }
}