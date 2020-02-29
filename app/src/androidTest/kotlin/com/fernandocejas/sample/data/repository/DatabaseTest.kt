package com.fernandocejas.sample.data.repository

import android.content.Context
import androidx.room.Room
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.test.core.app.ApplicationProvider
import com.fernandocejas.sample.data.AppDatabase
import com.fernandocejas.sample.test.BuildConfig
import org.junit.After
import org.junit.Before

open class DatabaseTest {

    protected lateinit var db: AppDatabase
    protected lateinit var context: Context

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
                val inMemoryDatabases = mutableMapOf<String, SupportSQLiteDatabase> ()
                // set your inMemory databases
                inMemoryDatabases["InMemoryOne.db"] = database[0]
                val setRoomInMemoryDatabase = debugDB.getMethod("setInMemoryRoomDatabases", *argTypes)
                setRoomInMemoryDatabase.invoke(null, inMemoryDatabases)
            } catch (ignore: Exception) {

            }

        }
    }

}