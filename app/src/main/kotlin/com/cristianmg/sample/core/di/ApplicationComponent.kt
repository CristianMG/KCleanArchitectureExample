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

package com.cristianmg.sample.core.di

import android.content.Context
import com.cristianmg.repositories.FarmRepository
import com.cristianmg.repositories.SessionRepository
import com.cristianmg.repositories.TaskRepository
import com.cristianmg.repositories.UserRepository
import com.cristianmg.repositories.di.RepositoryComponent
import com.cristianmg.repositories.di.qualifiers.Cloud
import com.cristianmg.repositories.di.qualifiers.Disk
import com.cristianmg.sample.AndroidApplication
import com.cristianmg.sample.core.di.viewmodel.ViewModelModule
import com.cristianmg.sample.core.navigation.RouteActivity
import com.cristianmg.sample.features.admin.AdminFragment
import com.cristianmg.sample.features.farm.FarmFragment
import com.cristianmg.sample.features.login.LoginFragment
import com.cristianmg.sample.features.technical.TechnicalFragment
import dagger.BindsInstance
import dagger.Component
import dagger.Provides
import javax.inject.Singleton

@Singleton
@Component(
        modules = [ApplicationModule::class, ViewModelModule::class],
        dependencies = [RepositoryComponent::class]
)
interface ApplicationComponent {


    fun inject(application: AndroidApplication)
    fun inject(routeActivity: RouteActivity)
    fun inject(loginFragment: LoginFragment)
    fun inject(adminFragment: AdminFragment)
    fun inject(technicalFragment: TechnicalFragment)
    fun inject(farmFragment: FarmFragment)


    @Component.Factory
    interface Factory {
        fun create(
                @BindsInstance applicationContext: Context,
                repositoryComponent: RepositoryComponent
        ): ApplicationComponent

    }
    

    companion object {
        /**
         * The singleton instance for [ApplicationComponent].
         * This is initialised by the `presentation` layer itself and primarily used to inject dependencies.
         * The instance can be replaced with a mock for testing when necessary.
         */
        @Volatile
        @JvmStatic
        lateinit var INSTANCE: ApplicationComponent
    }

}
