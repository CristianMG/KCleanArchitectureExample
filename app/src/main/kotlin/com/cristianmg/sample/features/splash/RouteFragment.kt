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

package com.cristianmg.sample.features.splash

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.cristianmg.common_objects.exception.Failure
import com.cristianmg.sample.core.platform.BaseFragment
import com.cristianmg.sample.R
import com.cristianmg.sample.core.extension.failure
import com.cristianmg.sample.core.extension.observe
import com.cristianmg.sample.core.extension.viewModel
import com.cristianmg.sample.core.navigation.Navigator
import javax.inject.Inject


class RouteFragment : BaseFragment() {

    @Inject
    lateinit var navigator: Navigator

    lateinit var routeViewModel: RouteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

        routeViewModel = viewModel(viewModelFactory) {
            observe(navigateToLogin, ::handleDelaySuccess)
            failure(failure, ::handleFailure)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        routeViewModel.start()
    }

    private fun handleDelaySuccess(b: Boolean?) {
        findNavController().navigate(RouteFragmentDirections.actionSplashFragmentToLoginFragment())
    }


    private fun handleFailure(failure: Failure?) {
        notify(R.string.unknown_error)
    }


    override fun layoutId() = R.layout.route_splash

}
