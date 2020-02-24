package com.fernandocejas.sample.data.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.fernandocejas.sample.data.AppDatabase
import org.junit.After
import org.junit.Before

open class DatabaseTest {

    protected lateinit var db: AppDatabase
    protected lateinit var context: Context

    @Before
    open fun before() {
        context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
                context, AppDatabase::class.java).build()
    }

    @After
    open fun after() {
        db.close()
    }

}