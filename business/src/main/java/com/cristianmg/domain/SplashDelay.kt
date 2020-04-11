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
package com.cristianmg.domain


import com.cristianmg.model.exception.Failure
import com.cristianmg.common_objects.functional.Either
import kotlinx.coroutines.delay
import javax.inject.Inject

class SplashDelay
@Inject constructor() : UseCase<Unit, SplashDelay.Params>() {

    override suspend fun run(params: Params): Either<Failure, Unit> {
        delay(TIME_TO_DELAY_SPLASH)
        return Either.Right(Unit)
    }


    data class Params(val empty: Boolean = true)

    companion object {
        const val TIME_TO_DELAY_SPLASH = 1000L
    }
}
