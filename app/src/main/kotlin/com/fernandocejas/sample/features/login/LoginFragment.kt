/**
 * Copyright (C) 2018 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fernandocejas.sample.features.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fernandocejas.sample.core.platform.BaseFragment

import androidx.databinding.DataBindingUtil
import br.com.ilhasoft.support.validation.Validator
import com.fernandocejas.sample.R
import com.fernandocejas.sample.core.exception.Failure
import com.fernandocejas.sample.core.extension.failure
import com.fernandocejas.sample.core.extension.observe
import com.fernandocejas.sample.core.extension.viewModel
import com.fernandocejas.sample.core.navigation.Navigator
import com.fernandocejas.sample.databinding.FragmentLoginBinding
import com.fernandocejas.sample.domain.model.User
import javax.inject.Inject


class LoginFragment : BaseFragment(), Validator.ValidationListener {

    @Inject
    lateinit var navigator: Navigator

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: FragmentLoginBinding
    private lateinit var validator: Validator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        loginViewModel = viewModel(viewModelFactory) {
            observe(loginSuccessful, ::handleLoginSuccessful)
            failure(failure, ::handleFailure)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(
                inflater, layoutId(), container, false)
        binding.loginViewModel = loginViewModel
        binding.handlers = Handlers()
        validator = Validator(binding)
        validator.setValidationListener(this)
        return binding.root
    }

    //TODO IMPORTANT hardcoded login to test
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginViewModel.email.set("ruben.garcia@gmail.com")
        loginViewModel.password.set("12345")
    }

    private fun handleLoginSuccessful(user: User?) {
        notify(R.string.login_successful)
        context?.let { navigator.showMain(it) }
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is Failure.UserNotFound -> notify(R.string.user_not_found)
        }
    }

    override fun layoutId() = R.layout.fragment_login

    override fun onValidationError() =
            notify(R.string.validation_error_message_login)

    override fun onValidationSuccess() = loginViewModel.login()

    inner class Handlers {
        fun login() = validator.toValidate()
    }
}
