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

import android.content.Context
import com.cristianmg.sample.domain.Authenticator
import com.cristianmg.sample.domain.model.UserRole
import com.cristianmg.sample.features.admin.AdminActivity
import com.cristianmg.sample.features.farm.FarmActivity
import com.cristianmg.sample.features.login.LoginActivity
import com.cristianmg.sample.features.technical.TechnicalActivity
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class Navigator
@Inject constructor(private val authenticator: Authenticator) {

    fun showLogin(context: Context) = context.startActivity(LoginActivity.callingIntent(context))

    fun showMain(context: Context) {
        when (authenticator.userLoggedIn()) {
            true -> {
                authenticator.userLogged?.let {
                    if (it.role == UserRole.ROLE_ADMIN) {
                        showAdminScreen(context)
                    } else {
                        showTechnicalScreen(context)
                    }
                } ?: run {
                    showLogin(context)
                }
            }
            false -> showLogin(context)
        }
    }

    fun showFarms(context: Context) {
        context.startActivity(FarmActivity.callingIntent(context))
    }

    private fun showTechnicalScreen(context: Context) {
        context.startActivity(TechnicalActivity.callingIntent(context))

    }

    private fun showAdminScreen(context: Context) {
        context.startActivity(AdminActivity.callingIntent(context))
    }

}
