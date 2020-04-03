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

package com.cristianmg.database.dao

import androidx.room.*
import com.cristianmg.common_objects.UserEntity

@Dao
interface UserDao {

    @Query("""
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
        SELECT userID FROM(
        
        SELECT user.id as userID,
            (SELECT SUM(task.duration) from task WHERE task.date=:date AND task.user_id = user.id) AS total 
             FROM user
             WHERE instr(user.typeTaskAvailable,:skill) AND user.role = ${UserEntity.ROLE_TECHNICAL}) ORDER BY total ASC LIMIT 1
        ) 
     
                 """)
    fun getUserBySkillLessWorkloadToday(date: String, skill: Int): UserEntity?

}