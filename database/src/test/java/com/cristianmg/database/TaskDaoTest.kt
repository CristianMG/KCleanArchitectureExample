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


import androidx.test.ext.junit.runners.AndroidJUnit4
import assertk.all
import assertk.assertThat
import assertk.assertions.*
import com.cristianmg.common_objects.*
import com.cristianmg.database.dao.TaskDao
import com.cristianmg.database.dao.UserDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*


@RunWith(AndroidJUnit4::class)
class TaskDaoTest : DatabaseTest() {

    private lateinit var taskDao: TaskDao
    private lateinit var userDao: UserDao

    override fun before() {
        super.before()
        taskDao = db.taskDAO()
        userDao = db.userDAO()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getTaskByUserTest() = runBlockingTest {
        val dateNow = dateFormat.parseCalendarToDatabaseFormat(Calendar.getInstance())
        val userOne = SampleData.getUser("465c120a-148f-457f-9082-4408e499ff2f", mutableListOf(WRAPPER, COLLECTOR), UserEntity.ROLE_TECHNICAL)

        userDao.insert(userOne)
        assertThat(taskDao.getTaskByUserUntilChanged(userOne.id).take(1).toList().first()).isEmpty()

        val entityList = mutableListOf(
                TaskEntity(typeTask = COLLECTOR, userId = userOne.id, duration = 30, date = dateNow, complete = false),
                TaskEntity(typeTask = WRAPPER, userId = userOne.id, duration = 60, date = dateNow, complete = true),
                TaskEntity(typeTask = PRODUCT_SUPPLIER, userId = userOne.id, duration = 120, date = dateNow, complete = false))

        taskDao.insert(entityList)

        assertThat(taskDao.getTaskByUserUntilChanged(userOne.id).take(1).toList().first()).all {
            hasSize(3)
            isEqualTo(entityList)
        }

    }

    @ExperimentalCoroutinesApi
    @Test
    fun getAll() = runBlockingTest {
        val dateNow = dateFormat.parseCalendarToDatabaseFormat(Calendar.getInstance())
        val userOne = SampleData.getUser("465c120a-148f-457f-9082-4408e499ff2f", mutableListOf(WRAPPER, COLLECTOR), UserEntity.ROLE_TECHNICAL)
        val userTwo = SampleData.getUser("fc2ce8fa-d168-4ced-851f-bd7dfcc1c6d3", mutableListOf(WRAPPER), UserEntity.ROLE_TECHNICAL)

        userDao.insert(userOne)
        userDao.insert(userTwo)

        assertThat(taskDao.getAll()).isEmpty()

        val entitys = mutableListOf(
                TaskEntity(typeTask = COLLECTOR, userId = userOne.id, duration = 30, date = dateNow, complete = false),
                TaskEntity(typeTask = PRODUCT_SUPPLIER, userId = userTwo.id, duration = 120, date = dateNow, complete = false))

        taskDao.insert(entitys)

        assertThat(taskDao.getAll()).all {
            hasSize(2)
            isEqualTo(entitys)
        }

    }

    @ExperimentalCoroutinesApi
    @Test
    fun updateTask() = runBlockingTest {
        val dateNow = dateFormat.parseCalendarToDatabaseFormat(Calendar.getInstance())
        val userOne = SampleData.getUser("465c120a-148f-457f-9082-4408e499ff2f", mutableListOf(WRAPPER, COLLECTOR), UserEntity.ROLE_TECHNICAL)

        userDao.insert(userOne)

        val entity = TaskEntity(typeTask = COLLECTOR, userId = userOne.id, duration = 30, date = dateNow, complete = false)

        taskDao.insert(entity)

        assertThat(taskDao.getById(entity.id)).all {
            isNotNull()
            isEqualTo(entity)
        }

        entity.complete = true

        taskDao.updateTask(entity)

        assertThat(taskDao.getById(entity.id)).all {
            isNotNull()
            isEqualTo(entity)
        }

    }



}

