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

package com.cristianmg.sample.features.login

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.cristianmg.sample.core.platform.BaseViewModel
import com.cristianmg.sample.domain.Authenticator
import com.cristianmg.sample.domain.Login
import com.cristianmg.sample.domain.model.User
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val login: Login,
                                         private val authenticator: Authenticator) : BaseViewModel() {

    val loginSuccessful: MutableLiveData<User> = MutableLiveData()

    var email: ObservableField<String> = ObservableField()
    var password: ObservableField<String> = ObservableField()

    fun login() {
        login(Login.Params(email.get() ?: "", password.get() ?: "")) {
            it.fold(::handleFailure, ::handleLoginSuccessful)
        }
    }

    private fun handleLoginSuccessful(user: User) {
        authenticator.userLogged = user
        loginSuccessful.value = user
    }
}