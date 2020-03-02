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
package com.fernandocejas.sample.data.repository.user

import com.fernandocejas.sample.core.exception.Failure
import com.fernandocejas.sample.core.functional.Either
import com.fernandocejas.sample.data.AppDatabase
import com.fernandocejas.sample.data.repository.mapping.DateFormat
import com.fernandocejas.sample.domain.model.TypeTask
import com.fernandocejas.sample.domain.model.User
import java.util.*
import javax.inject.Inject

interface UserRepository {

    fun users(): Either<Failure, List<User>>
    fun login(email: String, password: String): Either<Failure, User>
    fun getUserBySkillLessWorkloadToday(calendar: Calendar, typeTask: TypeTask): Either<Failure, User>


    class Disk @Inject constructor(private val appDatabase: AppDatabase,
                                   private val dateFormat: DateFormat) : UserRepository {


        private val cache: UserDao get() = appDatabase.userDAO()

        override fun users(): Either<Failure, List<User>> =
                Either.wrapFunction {
                    cache.getAll().map { it.toUserModel() }
                }

        override fun login(email: String, password: String): Either<Failure, User> {
            return try {
                cache.getByLogin(email, password)?.toUserModel()?.let {
                    Either.Right(it)
                } ?: Either.Left(Failure.UserNotFound)
            } catch (exception: Throwable) {
                Either.Left(Failure.MappingError(exception))
            }
        }

        override fun getUserBySkillLessWorkloadToday(calendar: Calendar, typeTask: TypeTask): Either<Failure, User> =
                cache.getUserBySkillLessWorkloadToday( dateFormat.parseCalendarToDatabaseFormat(calendar), typeTask.idTask)
                        ?.toUserModel()
                        ?.let {
                             Either.Right(it)
                        } ?: Either.Left(Failure.UserNotFound)
    }

}
