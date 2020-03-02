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

package com.cristianmg.sample.data.repository

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.test.core.app.ApplicationProvider
import com.cristianmg.sample.BuildConfig
import com.cristianmg.sample.data.AppDatabase
import com.cristianmg.sample.data.repository.mapping.DateFormat
import org.junit.After
import org.junit.Before
import org.junit.Rule

open class DatabaseTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    protected lateinit var db: AppDatabase
    protected lateinit var context: Context
    val dateFormat: DateFormat = DateFormat()

    @Before
    open fun before() {
        context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(
                context, AppDatabase::class.java).build()

        setInMemoryRoomDatabases(db.openHelper.readableDatabase)
    }

    @After
    open fun after() {
        db.close()
    }


    private fun setInMemoryRoomDatabases(vararg database: SupportSQLiteDatabase) {
        if (BuildConfig.DEBUG) {
            try {
                val debugDB = Class.forName("com.amitshekhar.DebugDB")
                val argTypes = arrayOf<Class<*>>(HashMap::class.java)
                val inMemoryDatabases = mutableMapOf<String, SupportSQLiteDatabase>()
                // set your inMemory databases
                inMemoryDatabases["InMemoryOne.db"] = database[0]
                val setRoomInMemoryDatabase = debugDB.getMethod("setInMemoryRoomDatabases", *argTypes)
                setRoomInMemoryDatabase.invoke(null, inMemoryDatabases)
            } catch (ignore: Exception) {

            }

        }
    }

}