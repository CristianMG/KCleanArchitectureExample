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

package com.cristianmg.sample

import android.app.Application
import com.cristianmg.repositories.di.RepositoryInitializer
import com.cristianmg.sample.core.di.ApplicationComponent
import com.cristianmg.sample.core.di.DaggerApplicationComponent
import com.squareup.leakcanary.LeakCanary

class AndroidApplication : Application() {

    private val repositoryInitializer: RepositoryInitializer by lazy { RepositoryInitializer() }

    lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        initAppInjector()
        this.injectMembers()
        this.initializeLeakDetection()
    }

    private fun initAppInjector() {
        val component = DaggerApplicationComponent
                .factory()
                .create(this, repositoryInitializer.initialize(this))
        ApplicationComponent.INSTANCE = component
        appComponent = component
    }

    private fun injectMembers() = appComponent.inject(this)

    private fun initializeLeakDetection() {
        if (BuildConfig.DEBUG) LeakCanary.install(this)
    }
}
