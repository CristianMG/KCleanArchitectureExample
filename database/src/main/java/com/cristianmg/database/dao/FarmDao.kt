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

import androidx.paging.DataSource
import androidx.room.*
import com.cristianmg.common_objects.FarmEntity

@Dao
interface FarmDao {

    @Query("""
            SELECT * FROM farm
            """)
    fun getAll(): DataSource.Factory<Int, FarmEntity>

    @Query("""
            SELECT * FROM farm
            """)
    fun get(): List<FarmEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: List<FarmEntity>)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: FarmEntity)

    @Query("DELETE FROM farm")
    fun delete()

}