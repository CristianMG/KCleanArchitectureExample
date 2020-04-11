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

package com.cristianmg.sample.features

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.cristianmg.sample.R
import com.cristianmg.sample.core.platform.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val appBarConfiguration =  AppBarConfiguration(
                setOf(
                        R.id.login_fragment,
                        R.id.technical_fragment,
                        R.id.admin_fragment
                )
        )

        toolbar.setupWithNavController(findNavController(R.id.nav_host_fragment),appBarConfiguration)
        findNavController(R.id.nav_host_fragment).addOnDestinationChangedListener { controller, destination, arguments ->
            when(destination.id){
                R.id.splash_fragment -> {
                    toolbar.visibility = View.GONE

                }
                R.id.login_fragment -> {
                    toolbar.visibility = View.VISIBLE
                }
            }
        }
    }

}