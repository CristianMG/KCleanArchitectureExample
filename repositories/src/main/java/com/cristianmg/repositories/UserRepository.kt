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

import com.cristianmg.model.exception.Failure
import com.cristianmg.common_objects.functional.Either
import com.cristianmg.database.dao.UserDao
import com.cristianmg.database.converter.DateFormat
import com.cristianmg.model.TypeTask
import com.cristianmg.model.User
import com.cristianmg.repositories.mapper.UserMapper
import java.util.*
import javax.inject.Inject

interface UserRepository {

    fun users(): Either<Failure, List<User>>
    fun login(email: String, password: String): Either<Failure, User>
    fun getUserBySkillLessWorkloadToday(calendar: Calendar, typeTask: TypeTask): Either<Failure, User>


    class Disk @Inject constructor(private val cache: UserDao,
                                   private val mapper:UserMapper,
                                   private val dateFormat: DateFormat) : UserRepository {

        override fun users(): Either<Failure, List<User>> =
                Either.wrapFunction {
                    mapper.mapListToModel(cache.getAll())
                }

        override fun login(email: String, password: String): Either<Failure, User> {
            return try {
                cache.getByLogin(email, password)?.let {
                    Either.Right(mapper.mapToModel(it))
                } ?: Either.Left(Failure.UserNotFound)
            } catch (exception: Throwable) {
                Either.Left(Failure.MappingError(exception))
            }
        }

        override fun getUserBySkillLessWorkloadToday(calendar: Calendar, typeTask: TypeTask): Either<Failure, User> =
                cache.getUserBySkillLessWorkloadToday(dateFormat.parseCalendarToDatabaseFormat(calendar), typeTask.idTask)
                        ?.let {
                            Either.Right(mapper.mapToModel(it))
                        } ?: Either.Left(Failure.UserNotFound)
    }

}
