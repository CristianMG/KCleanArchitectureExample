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
import com.cristianmg.common_objects.functional.flatMap
import com.cristianmg.repositories.FarmRepository
import com.cristianmg.repositories.di.qualifiers.Cloud
import com.cristianmg.repositories.di.qualifiers.Disk
import javax.inject.Inject

class UpdateFarmsCase
@Inject constructor(
        @Cloud private val networkFarmRepository: FarmRepository,
        @Disk private val diskFarmRepository: FarmRepository) : UseCase<Unit, UpdateFarmsCase.Params>() {

    override suspend fun run(params: Params): Either<Failure, Unit> =
            networkFarmRepository.getAll()
                    .flatMap { list ->
                        diskFarmRepository.removeAll()
                                .flatMap {
                                    diskFarmRepository.save(list)
                                }
                    }

    data class Params(val empty: Boolean = true)
}
