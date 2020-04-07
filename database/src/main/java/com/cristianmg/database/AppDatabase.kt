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

package com.cristianmg.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.cristianmg.common_objects.FarmEntity
import com.cristianmg.common_objects.TaskEntity
import com.cristianmg.common_objects.UserEntity
import com.cristianmg.database.converter.Converters
import com.cristianmg.database.dao.FarmDao
import com.cristianmg.database.dao.TaskDao
import com.cristianmg.database.dao.UserDao
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(entities = [UserEntity::class, TaskEntity::class, FarmEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDAO(): UserDao
    abstract fun taskDAO(): TaskDao
    abstract fun farmDAO(): FarmDao

    companion object {

        private const val DATABASE_NAME = "sdos_test"
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                    .addCallback(object : RoomDatabase.Callback() {

                        /***
                         * Pre-populate all users in database
                         * @param db SupportSQLiteDatabase
                         */
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            GlobalScope.launch {
                                getInstance(context).runInTransaction {
                                    SampleData.getSampleData(context).forEach {
                                        getInstance(context).userDAO().insert(it)
                                    }
                                }
                            }
                        }

                    })
                    .build()
        }
    }
}