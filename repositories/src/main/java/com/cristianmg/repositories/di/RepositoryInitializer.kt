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
import com.cristianmg.api.di.ApiInitializer
import com.cristianmg.database.di.DatabaseInitializer
import javax.inject.Singleton

@Singleton
class RepositoryInitializer {

    private val apiInitializer: ApiInitializer by lazy { ApiInitializer() }
    private val databaseInitializer: DatabaseInitializer by lazy { DatabaseInitializer() }

    fun initialize(appContext: Context): RepositoryComponent {
        return initializeRepositoryComponent(appContext)
    }

    private fun initializeRepositoryComponent(appContext: Context): RepositoryComponent {

        val apiComponent = apiInitializer.initialize(appContext)
        val databaseComponent = databaseInitializer.initialize(appContext)

        val repositoryComponent =  DaggerInternalRepositoryComponent.factory()
                .create(appContext,apiComponent,databaseComponent)

        RepositoryComponent.INSTANCE = repositoryComponent

        return repositoryComponent
    }

}