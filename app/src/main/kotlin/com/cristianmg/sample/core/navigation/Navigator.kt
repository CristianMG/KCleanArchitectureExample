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

package com.cristianmg.sample.core.navigation

import androidx.navigation.NavController
import com.cristianmg.domain.Authenticator
import com.cristianmg.model.UserRole
import com.cristianmg.sample.features.login.LoginFragmentDirections
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Navigator
@Inject constructor(private val authenticator: Authenticator) {

    fun showMain(navController: NavController) {
        authenticator.userLogged?.let {
            if (it.role == UserRole.ROLE_ADMIN) {
                showAdminScreen(navController)
            } else {
                showTechnicalScreen(navController)
            }
        }
    }

    private fun showTechnicalScreen(navController: NavController) {
        navController.navigate(LoginFragmentDirections.actionLoginFragmentToTechnicalFragment())
    }

    private fun showAdminScreen(navController: NavController) {
        navController.navigate(LoginFragmentDirections.actionLoginFragmentToAdminFragment())
    }

}
