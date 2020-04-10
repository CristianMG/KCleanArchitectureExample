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

package com.cristianmg.sample.core.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cristianmg.sample.features.admin.AdminViewModel
import com.cristianmg.sample.features.farm.FarmViewModel
import com.cristianmg.sample.features.login.LoginViewModel
import com.cristianmg.sample.features.splash.RouteViewModel
import com.cristianmg.sample.features.technical.TechnicalViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindsLoginViewModel(loginViewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AdminViewModel::class)
    abstract fun bindsAdminViewModel(adminViewModel: AdminViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TechnicalViewModel::class)
    abstract fun bindsTechnicalViewModel(technicalViewModel: TechnicalViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FarmViewModel::class)
    abstract fun bindsFarmViewModel(farmViewModel: FarmViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RouteViewModel::class)
    abstract fun bindsSplashViewModel(routeViewModel: RouteViewModel): ViewModel

}