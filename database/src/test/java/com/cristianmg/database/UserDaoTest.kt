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
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import assertk.assertions.isNull
import com.cristianmg.common_objects.COLLECTOR
import com.cristianmg.common_objects.PRODUCT_SUPPLIER
import com.cristianmg.common_objects.TaskEntity
import com.cristianmg.common_objects.UserEntity.Companion.ROLE_ADMIN
import com.cristianmg.common_objects.UserEntity.Companion.ROLE_TECHNICAL
import com.cristianmg.common_objects.WRAPPER
import com.cristianmg.database.dao.TaskDao
import com.cristianmg.database.dao.UserDao
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*


@RunWith(AndroidJUnit4::class)
class UserDaoTest : DatabaseTest() {

    private lateinit var userDao: UserDao
    private lateinit var taskDao: TaskDao

    override fun before() {
        super.before()
        userDao = db.userDAO()
        taskDao = db.taskDAO()
    }

    @Test
    fun saveUserInDatabaseTest() {
        val user = SampleData.getSingleUser(context)
        userDao.insert(user)
        assertThat(userDao.getAll().first()).isEqualTo(user)
    }


    @Test
    fun getLoginUserTest() {
        val data = SampleData.getSampleData(context)
        data.forEach {
            userDao.insert(it)
        }

        val userToLogin = data.random()
        assertThat(userDao.getByLogin(userToLogin.email, userToLogin.password)).isEqualTo(userToLogin)
    }


    @Test
    fun getAllTest() {
        val data = SampleData.getSampleData(context)
        data.forEach {
            userDao.insert(it)
        }

        assertThat(userDao.getAll()).isEqualTo(data)
    }


    @Test
    fun getUserBySkillLessWorkloadTodayTest() {
        val userOne = SampleData.getUser("465c120a-148f-457f-9082-4408e499ff2f", mutableListOf(WRAPPER, COLLECTOR), ROLE_TECHNICAL)
        val userTwo = SampleData.getUser("fc2ce8fa-d168-4ced-851f-bd7dfcc1c6d3", mutableListOf(WRAPPER), ROLE_TECHNICAL)
        val userThree = SampleData.getUser("dd047be2-3731-44cd-ac2d-45e11badf842", mutableListOf(COLLECTOR), ROLE_TECHNICAL)
        val userFour = SampleData.getUser("c3991a08-22b5-4926-aa81-faccf20138b4", mutableListOf(), ROLE_ADMIN)

        userDao.insert(userOne)
        userDao.insert(userTwo)
        userDao.insert(userThree)
        userDao.insert(userFour)

        val dateNow = dateFormat.parseCalendarToDatabaseFormat(Calendar.getInstance())

        /**
         * We has user with properly skills or not
         */
        assertThat(userDao.getUserBySkillLessWorkloadToday(dateNow, COLLECTOR)).isNotNull()
        assertThat(userDao.getUserBySkillLessWorkloadToday(dateNow, WRAPPER)).isNotNull()
        assertThat(userDao.getUserBySkillLessWorkloadToday(dateNow, PRODUCT_SUPPLIER)).isNull()

        /** Test choose the user with less work load **/
        taskDao.insert(TaskEntity(typeTask = COLLECTOR, userId = userOne.id, duration = 30, date = dateNow, complete = false))
        taskDao.insert(TaskEntity(typeTask = WRAPPER, userId = userTwo.id, duration = 600, date = dateNow, complete = false))
        taskDao.insert(TaskEntity(typeTask = COLLECTOR, userId = userThree.id, duration = 1000, date = dateNow, complete = false))
        assertThat(userDao.getUserBySkillLessWorkloadToday(dateNow, COLLECTOR)?.id).isEqualTo(userOne.id)

        /** Test choose the user with less work and only with properly skill **/
        taskDao.insert(TaskEntity(typeTask = COLLECTOR, userId = userOne.id, duration = 2000, date = dateNow, complete = false))
        assertThat(userDao.getUserBySkillLessWorkloadToday(dateNow, COLLECTOR)?.id).isEqualTo(userThree.id)

        /** The workload is being filtering properly by date **/
        val dateTomorrow = dateFormat.parseCalendarToDatabaseFormat(
                Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, 1) }
        )

        taskDao.insert(TaskEntity(typeTask = COLLECTOR, userId = userThree.id, duration = 5000, date = dateTomorrow, complete = false))
        assertThat(userDao.getUserBySkillLessWorkloadToday(dateNow, COLLECTOR)?.id).isEqualTo(userThree.id)
    }


}

