package com.fernandocejas.sample.features.login

import com.fernandocejas.sample.core.platform.BaseViewModel
import com.fernandocejas.sample.domain.Login
import javax.inject.Inject

class LoginViewModel @Inject constructor(val login: Login) : BaseViewModel() {
}