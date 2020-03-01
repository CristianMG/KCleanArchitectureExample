package com.fernandocejas.sample.data.repository


import androidx.test.ext.junit.runners.AndroidJUnit4
import assertk.all
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import assertk.assertions.isNull
import com.fernandocejas.sample.data.SampleData
import com.fernandocejas.sample.data.entity.TaskEntity
import com.fernandocejas.sample.data.entity.UserEntity
import com.fernandocejas.sample.data.repository.task.TaskDao
import com.fernandocejas.sample.data.repository.user.UserDao
import com.fernandocejas.sample.domain.model.TypeTask
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


/*
    @Query("""
            SELECT * FROM task WHERE task.user_id = :userId
            """)
    fun getTaskByUser(userId: String): Flow<List<TaskEntity>>

    fun getTaskByUserUntilChanged(userId: String) =
            getTaskByUser(userId).distinctUntilChanged()

    @Query("""
            SELECT * FROM task
            """)
    fun getAll(): List<TaskEntity>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: TaskEntity)

    @Update
    fun updateTask(task: TaskEntity)*/

   /* @Test
    fun getAllTest() {
        val userOne = SampleData.getUser("465c120a-148f-457f-9082-4408e499ff2f", mutableListOf(TypeTask.WRAPPER, TypeTask.COLLECTOR), UserEntity.ROLE_TECHNICAL)
        val userTwo = SampleData.getUser("fc2ce8fa-d168-4ced-851f-bd7dfcc1c6d3", mutableListOf(TypeTask.WRAPPER), UserEntity.ROLE_TECHNICAL)
        val dateNow = Calendar.getInstance()

        taskDao.insert(TaskEntity(typeTask = TypeTask.COLLECTOR.idTask, userId = userOne.id, duration = 30, date = dateNow, complete = false))
        taskDao.insert(TaskEntity(typeTask = TypeTask.COLLECTOR.idTask, userId = userTwo.id, duration = 30, date = dateNow, complete = false))

    }
*/

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
        val userOne = SampleData.getUser("465c120a-148f-457f-9082-4408e499ff2f", mutableListOf(TypeTask.WRAPPER, TypeTask.COLLECTOR), UserEntity.ROLE_TECHNICAL)
        val userTwo = SampleData.getUser("fc2ce8fa-d168-4ced-851f-bd7dfcc1c6d3", mutableListOf(TypeTask.WRAPPER), UserEntity.ROLE_TECHNICAL)
        val userThree = SampleData.getUser("dd047be2-3731-44cd-ac2d-45e11badf842", mutableListOf(TypeTask.COLLECTOR), UserEntity.ROLE_TECHNICAL)
        val userFour = SampleData.getUser("c3991a08-22b5-4926-aa81-faccf20138b4", mutableListOf(), UserEntity.ROLE_ADMIN)

        userDao.insert(userOne)
        userDao.insert(userTwo)
        userDao.insert(userThree)
        userDao.insert(userFour)

        val dateNow = Calendar.getInstance()

        /**
         * We has user with properly skills or not
         */
        assertThat(userDao.getUserBySkillLessWorkloadToday(dateNow, TypeTask.COLLECTOR.idTask)).isNotNull()
        assertThat(userDao.getUserBySkillLessWorkloadToday(dateNow, TypeTask.WRAPPER.idTask)).isNotNull()
        assertThat(userDao.getUserBySkillLessWorkloadToday(dateNow, TypeTask.PRODUCT_SUPPLIER.idTask)).isNull()

        /** Test choose the user with less work load **/
        taskDao.insert(TaskEntity(typeTask = TypeTask.COLLECTOR.idTask, userId = userOne.id, duration = 30, date = dateNow, complete = false))
        taskDao.insert(TaskEntity(typeTask = TypeTask.WRAPPER.idTask, userId = userTwo.id, duration = 600, date = dateNow, complete = false))
        taskDao.insert(TaskEntity(typeTask = TypeTask.COLLECTOR.idTask, userId = userThree.id, duration = 1000, date = dateNow, complete = false))
        assertThat(userDao.getUserBySkillLessWorkloadToday(dateNow, TypeTask.COLLECTOR.idTask)?.id).isEqualTo(userOne.id)

        /** Test choose the user with less work and only with properly skill **/
        taskDao.insert(TaskEntity(typeTask = TypeTask.COLLECTOR.idTask, userId = userOne.id, duration = 2000, date = dateNow, complete = false))
        assertThat(userDao.getUserBySkillLessWorkloadToday(dateNow, TypeTask.COLLECTOR.idTask)?.id).isEqualTo(userThree.id)

        /** The workload is being filtering properly by date **/
        val dateTomorrow = Calendar.getInstance()
        dateTomorrow.add(Calendar.DAY_OF_YEAR, 1)

        taskDao.insert(TaskEntity(typeTask = TypeTask.COLLECTOR.idTask, userId = userThree.id, duration = 5000, date = dateTomorrow, complete = false))
        assertThat(userDao.getUserBySkillLessWorkloadToday(dateNow, TypeTask.COLLECTOR.idTask)?.id).isEqualTo(userThree.id)
    }


}

