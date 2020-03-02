package com.fernandocejas.sample.data.repository


import androidx.test.ext.junit.runners.AndroidJUnit4
import assertk.all
import assertk.assertThat
import assertk.assertions.*
import com.fernandocejas.sample.data.SampleData
import com.fernandocejas.sample.data.entity.TaskEntity
import com.fernandocejas.sample.data.entity.UserEntity
import com.fernandocejas.sample.data.repository.task.TaskDao
import com.fernandocejas.sample.data.repository.user.UserDao
import com.fernandocejas.sample.domain.model.TypeTask
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
        val userOne = SampleData.getUser("465c120a-148f-457f-9082-4408e499ff2f", mutableListOf(TypeTask.WRAPPER, TypeTask.COLLECTOR), UserEntity.ROLE_TECHNICAL)

        userDao.insert(userOne)
        assertThat(taskDao.getTaskByUserUntilChanged(userOne.id).take(1).toList().first()).isEmpty()

        val entitys = mutableListOf(
                TaskEntity(typeTask = TypeTask.COLLECTOR.idTask, userId = userOne.id, duration = 30, date = dateNow, complete = false),
                TaskEntity(typeTask = TypeTask.WRAPPER.idTask, userId = userOne.id, duration = 60, date = dateNow, complete = true),
                TaskEntity(typeTask = TypeTask.PRODUCT_SUPPLIER.idTask, userId = userOne.id, duration = 120, date = dateNow, complete = false))

        taskDao.insert(entitys)

        assertThat(taskDao.getTaskByUserUntilChanged(userOne.id).take(1).toList().first()).all {
            hasSize(3)
            isEqualTo(entitys)
        }

    }

    @ExperimentalCoroutinesApi
    @Test
    fun getAll() = runBlockingTest {
        val dateNow = dateFormat.parseCalendarToDatabaseFormat(Calendar.getInstance())
        val userOne = SampleData.getUser("465c120a-148f-457f-9082-4408e499ff2f", mutableListOf(TypeTask.WRAPPER, TypeTask.COLLECTOR), UserEntity.ROLE_TECHNICAL)
        val userTwo = SampleData.getUser("fc2ce8fa-d168-4ced-851f-bd7dfcc1c6d3", mutableListOf(TypeTask.WRAPPER), UserEntity.ROLE_TECHNICAL)

        userDao.insert(userOne)
        userDao.insert(userTwo)

        assertThat(taskDao.getAll()).isEmpty()

        val entitys = mutableListOf(
                TaskEntity(typeTask = TypeTask.COLLECTOR.idTask, userId = userOne.id, duration = 30, date = dateNow, complete = false),
                TaskEntity(typeTask = TypeTask.PRODUCT_SUPPLIER.idTask, userId = userTwo.id, duration = 120, date = dateNow, complete = false))

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
        val userOne = SampleData.getUser("465c120a-148f-457f-9082-4408e499ff2f", mutableListOf(TypeTask.WRAPPER, TypeTask.COLLECTOR), UserEntity.ROLE_TECHNICAL)

        userDao.insert(userOne)

        val entity = TaskEntity(typeTask = TypeTask.COLLECTOR.idTask, userId = userOne.id, duration = 30, date = dateNow, complete = false)

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

