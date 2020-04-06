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

package com.cristianmg.database.di

import android.content.Context
import com.cristianmg.database.AppDatabase
import com.cristianmg.database.dao.FarmDao
import com.cristianmg.database.dao.TaskDao
import com.cristianmg.database.dao.UserDao
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

interface DatabaseComponent {
    @Singleton
    fun provideDatabase(): AppDatabase
    @Singleton
    fun provideUserDao(): UserDao
    @Singleton
    fun provideTaskDao(): TaskDao
    @Singleton
    fun provideFarmDao(): FarmDao

    companion object {

        @Volatile
        @JvmStatic
        lateinit var INSTANCE: DatabaseComponent
    }


}


@Singleton
@Component(
        modules = [DatabaseModule::class]
)
internal interface InternalDatabaseComponent : DatabaseComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): InternalDatabaseComponent
    }

}
