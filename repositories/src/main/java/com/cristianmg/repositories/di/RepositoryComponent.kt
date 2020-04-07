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

package com.cristianmg.repositories.di

import android.content.Context
import com.cristianmg.api.di.ApiComponent
import com.cristianmg.database.di.DatabaseComponent
import com.cristianmg.repositories.FarmRepository
import com.cristianmg.repositories.SessionRepository
import com.cristianmg.repositories.TaskRepository
import com.cristianmg.repositories.UserRepository
import com.cristianmg.repositories.di.qualifiers.Cloud
import com.cristianmg.repositories.di.qualifiers.Disk
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

interface RepositoryComponent {

    @Singleton
    @Disk
    fun providesUserRepository(): UserRepository

    @Singleton
    @Disk
    fun providesTaskRepository(): TaskRepository

    @Singleton
    @Disk
    fun providesSessionRepository(): SessionRepository

    @Singleton
    @Disk
    fun provideFarmRepositoryDisk(): FarmRepository

    @Singleton
    @Cloud
    fun provideFarmRepositoryNetwork(): FarmRepository


    companion object {
        @Volatile
        @JvmStatic
        lateinit var INSTANCE: RepositoryComponent
    }

}


@Singleton
@Component(
        modules = [RepositoryModule::class],
        dependencies = [ApiComponent::class, DatabaseComponent::class]
)
internal interface InternalRepositoryComponent:RepositoryComponent{

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance applicationContext: Context,
                   apiComponent: ApiComponent,
                   databaseComponent: DatabaseComponent): RepositoryComponent
    }
}