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

package com.cristianmg.sample.data.repository.farm

import androidx.paging.DataSource
import com.cristianmg.sample.core.exception.Failure
import com.cristianmg.sample.core.functional.Either
import com.cristianmg.sample.core.platform.NetworkHandler
import com.cristianmg.sample.data.AppDatabase
import com.cristianmg.sample.data.entity.FarmEntity
import com.cristianmg.sample.data.repository.farm.api.FarmService
import com.cristianmg.sample.domain.model.Farm
import retrofit2.Call
import javax.inject.Inject

interface FarmRepository {

    fun farms(): DataSource.Factory<Int, Farm>
    fun getAll(): Either<Failure, List<Farm>>
    fun save(it: List<Farm>): Either<Failure, Unit>
    fun removeAll(): Either<Failure, Unit>

    class Disk @Inject constructor(
            private val appDatabase: AppDatabase) : FarmRepository {

        override fun removeAll(): Either<Failure, Unit> =
                Either.wrapFunction {
                    cache.delete()
                }


        override fun save(it: List<Farm>) =
                Either.wrapFunction {
                    val mapEntity = it.map {
                        FarmEntity(it.farmerID, it.category, it.item, it.zipcode, it.phone, it.location)
                    }
                    cache.insert(mapEntity)
                }


        private val cache: FarmDao get() = appDatabase.farmDAO()


        override fun getAll(): Either<Failure, List<Farm>> =
                Either.wrapFunction {
                    cache.get().map { it.toFarm() }
                }

        override fun farms(): DataSource.Factory<Int, Farm> =
                cache.getAll().map { it.toFarm() }

    }

    class Network @Inject constructor(
            private val service: FarmService,
            private val networkHandler: NetworkHandler
    ) : FarmRepository {


        override fun getAll(): Either<Failure, List<Farm>> {
            return when (networkHandler.isConnected) {
                true -> request(service.farms(), { list ->
                    list.map { it.toFarm() }
                }, emptyList())
                else -> Either.Left(Failure.NetworkConnection)
            }
        }

        private fun <T, R> request(call: Call<T>, transform: (T) -> R, default: T): Either<Failure, R> {
            return try {
                val response = call.execute()
                when (response.isSuccessful) {
                    true -> Either.Right(transform((response.body() ?: default)))
                    false -> Either.Left(Failure.ServerError)
                }
            } catch (exception: Throwable) {
                Either.Left(Failure.ServerError)
            }
        }


        /**
         * Unused functions
         */
        override fun removeAll() =
                throw IllegalAccessException("Network can't provide a data-source")

        override fun save(it: List<Farm>) =
                throw IllegalAccessException("Network can't provide a data-source")


        override fun farms(): DataSource.Factory<Int, Farm> =
                throw IllegalAccessException("Network can't provide a data-source")


    }


}