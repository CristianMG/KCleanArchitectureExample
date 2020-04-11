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

package com.cristianmg.repositories.ext

import com.cristianmg.api.functional.RetrofitCall
import com.cristianmg.model.exception.Failure
import com.cristianmg.common_objects.functional.Either

fun <T> RetrofitCall<T>.toEither(): Either<Failure, T> {
    return try {
        val response = call.execute()
        when (response.isSuccessful) {
            true -> {
                Either.Right(response.body()?: throw Failure.NullBodyPetition)
            }
            false -> Either.Left(Failure.ServerError)
        }
    } catch (exception: Throwable) {
        Either.Left(Failure.ServerError)
    }
}
