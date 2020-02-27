package com.fernandocejas.sample.data.repository


import androidx.test.ext.junit.runners.AndroidJUnit4
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import com.fernandocejas.sample.data.SampleData
import com.fernandocejas.sample.data.entity.TaskEntity
import com.fernandocejas.sample.data.entity.UserEntity
import com.fernandocejas.sample.data.repository.task.TaskDao
import com.fernandocejas.sample.data.repository.user.UserDao
import com.fernandocejas.sample.domain.model.TypeTask
import org.junit.Test
import org.junit.runner.RunWith
import java.text.SimpleDateFormat
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
        /**
         * Prepare the state
         */

        val userOne = SampleData.getUser(mutableListOf(TypeTask.WRAPPER, TypeTask.COLLECTOR), UserEntity.ROLE_TECHNICAL)
        val userTwo = SampleData.getUser(mutableListOf(TypeTask.WRAPPER), UserEntity.ROLE_TECHNICAL)
        val userThree = SampleData.getUser(mutableListOf(TypeTask.COLLECTOR), UserEntity.ROLE_TECHNICAL)
        val userFour = SampleData.getUser(mutableListOf(), UserEntity.ROLE_ADMIN)

        userDao.insert(userOne)
        userDao.insert(userTwo)
        userDao.insert(userThree)
        userDao.insert(userFour)

        val dateNow = Calendar.getInstance()

        /** Any user can do the work **/
        assertThat(userDao.getUserBySkillLessWorkloadToday(dateNow, TypeTask.PRODUCT_SUPPLIER.idTask)).isNull()


        /** Test choose the user with less work load **/
        taskDao.insert(TaskEntity(typeTask = TypeTask.COLLECTOR.idTask, userId = userOne.id, duration = 30, date = dateNow))
        taskDao.insert(TaskEntity(typeTask = TypeTask.WRAPPER.idTask, userId = userTwo.id, duration = 600, date = dateNow))
        taskDao.insert(TaskEntity(typeTask = TypeTask.COLLECTOR.idTask, userId = userThree.id, duration = 1000, date = dateNow))
        assertThat(userDao.getUserBySkillLessWorkloadToday(dateNow, TypeTask.COLLECTOR.idTask)?.id).isEqualTo(userOne.id)

        /** Test choose the user with less work and only with properly skill **/
        taskDao.insert(TaskEntity(typeTask = TypeTask.COLLECTOR.idTask, userId = userOne.id, duration = 2000, date = dateNow))
        assertThat(userDao.getUserBySkillLessWorkloadToday(dateNow, TypeTask.COLLECTOR.idTask)?.id).isEqualTo(userThree.id)


        /** The workload is being filtering properly by date **/
        val dateTomorrow = Calendar.getInstance()
        dateTomorrow.add(Calendar.DAY_OF_YEAR, 1)

        taskDao.insert(TaskEntity(typeTask = TypeTask.COLLECTOR.idTask, userId = userThree.id, duration = 5000, date = dateTomorrow))
        assertThat(userDao.getUserBySkillLessWorkloadToday(dateNow, TypeTask.COLLECTOR.idTask)?.id).isEqualTo(userThree.id)

    }
}

