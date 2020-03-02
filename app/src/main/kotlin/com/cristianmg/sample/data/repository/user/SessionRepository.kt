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

package com.cristianmg.sample.data.repository.user


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.cristianmg.sample.domain.model.User
import com.google.gson.Gson
import javax.inject.Inject

interface SessionRepository {


    fun getSessionSaved(): User?
    fun setSesionSaved(user:User)
    fun closeSession()


    class Disk @Inject constructor(val context: Context,
                                   val gson:Gson) : SessionRepository {

        private val mSharedPrefs: SharedPreferences by lazy {
            context.getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE)
        }


        override fun getSessionSaved(): User? =
                mSharedPrefs.getString(USER_SAVED, null)?.let {
                    gson.fromJson(it, User::class.java)
                }

        /**
         * This is executed in background it does not matter if we excuted commit or apply
         * but is better has the control if the key was saved properly
         * @param user User
         */
        @SuppressLint("ApplySharedPref")
        override fun closeSession() {
            mSharedPrefs.edit().remove(USER_SAVED).commit()
        }


        /**
         * This is executed in background it does not matter if we excuted commit or apply
         * but is better has the control if the key was saved properly
         * @param user User
         */
        @SuppressLint("ApplySharedPref")
        override fun setSesionSaved(user: User) {
            mSharedPrefs.edit().putString(USER_SAVED,gson.toJson(user)).commit()
        }

        companion object {
            private const val PREFS_NAME = "iVoox"
            private const val USER_SAVED = "USER_SAVED"
        }
    }


}
