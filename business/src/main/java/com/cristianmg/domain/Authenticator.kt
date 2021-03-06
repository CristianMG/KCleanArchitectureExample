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

package com.cristianmg.domain

import com.cristianmg.model.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Authenticator
@Inject constructor() {

    /**
     * the user must be saved in shared preferences to avoid problems with recreates's activitys
     */
    var userLogged: User? = null

    fun userLoggedIn() = userLogged != null

    fun closeSession() {
        userLogged = null
    }
}
