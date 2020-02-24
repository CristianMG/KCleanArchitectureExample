package com.fernandocejas.sample.data.repository


import androidx.test.ext.junit.runners.AndroidJUnit4
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.fernandocejas.sample.data.SampleData
import com.fernandocejas.sample.data.repository.user.UserDao
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class UserDaoTest : DatabaseTest() {

    private lateinit var userDao: UserDao

    override fun before() {
        super.before()
        userDao = db.userDAO()
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
}
