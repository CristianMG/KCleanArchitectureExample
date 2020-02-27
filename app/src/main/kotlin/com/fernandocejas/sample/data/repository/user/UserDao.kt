/*
 * Copyright 2019 Cristian Menárguez González
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fernandocejas.sample.data.repository.user

import androidx.room.*
import com.fernandocejas.sample.data.entity.UserEntity
import java.util.*

@Dao
interface UserDao {

    @Query(
            """
            SELECT * FROM user
            """)
    fun getAll(): List<UserEntity>

    @Query("""
            SELECT * FROM user WHERE user.email = :email AND user.password = :password LIMIT 1
            """)
    fun getByLogin(email: String, password: String): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: UserEntity)


    @Query("""
        SELECT * FROM user WHERE user.id =(
        SELECT userID FROM (
        SELECT task.user_id as userID, SUM(task.duration) as amount FROM task WHERE task.date = :date GROUP BY task.user_id HAVING instr(task.type_task,:skill) ORDER BY task.duration ASC LIMIT 1)
        )
    """)
    fun getUserBySkillLessWorkloadToday(date: Calendar, skill: Int): UserEntity?


}