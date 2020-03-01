package com.fernandocejas.sample.data.repository.farm

import androidx.paging.DataSource
import com.fernandocejas.sample.core.exception.Failure
import com.fernandocejas.sample.core.functional.Either
import com.fernandocejas.sample.core.platform.NetworkHandler
import com.fernandocejas.sample.data.AppDatabase
import com.fernandocejas.sample.data.entity.FarmEntity
import com.fernandocejas.sample.data.repository.farm.api.FarmService
import com.fernandocejas.sample.domain.model.Farm
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