package com.fernandocejas.sample.features.login

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fernandocejas.sample.core.platform.BaseViewModel
import com.fernandocejas.sample.domain.Authenticator
import com.fernandocejas.sample.domain.Login
import com.fernandocejas.sample.domain.model.User
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