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

package com.cristianmg.repositories

import androidx.paging.DataSource
import com.cristianmg.api.FarmService
import com.cristianmg.common_objects.exception.Failure
import com.cristianmg.common_objects.functional.Either
import com.cristianmg.database.dao.FarmDao
import com.cristianmg.api.functional.NetworkHandler
import com.cristianmg.model.Farm
import com.cristianmg.repositories.mapper.FarmMapper
import javax.inject.Inject

interface FarmRepository {

    fun farms(): DataSource.Factory<Int, Farm>
    fun getAll(): Either<Failure, List<Farm>>
    fun save(it: List<Farm>): Either<Failure, Unit>
    fun removeAll(): Either<Failure, Unit>

    class Disk @Inject constructor(
            private val cache: FarmDao,
            private val mapper: FarmMapper
    ) : FarmRepository {

        override fun removeAll(): Either<Failure, Unit> =
                Either.wrapFunction {
                    cache.delete()
                }


        override fun save(it: List<Farm>) =
                Either.wrapFunction {
                    cache.insert(it.map { mapper.mapToEntity(it) })
                }

        override fun getAll(): Either<Failure, List<Farm>> =
                Either.wrapFunction {
                    mapper.mapListToModel(cache.get())
                }

        override fun farms(): DataSource.Factory<Int, Farm> =
                cache.getAll().map { mapper.mapToModel(it) }

    }

    class Network @Inject constructor(
            private val service: FarmService,
            private val networkHandler: NetworkHandler,
            private val mapper: FarmMapper
    ) : FarmRepository {


        override fun getAll(): Either<Failure, List<Farm>> {
            return when (networkHandler.isConnected) {
                true -> Either.wrapFunction { mapper.mapListToModel(service.farms()) }
                else -> Either.Left(Failure.NetworkConnection)
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