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

package com.cristianmg.sample.features.farm

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.cristianmg.sample.R
import com.cristianmg.sample.core.extension.observe
import com.cristianmg.sample.core.extension.viewModel
import com.cristianmg.sample.core.imageloader.ImageLoader
import com.cristianmg.sample.core.navigation.Navigator
import com.cristianmg.sample.core.platform.BaseFragment
import kotlinx.android.synthetic.main.fragment_technical.*
import javax.inject.Inject


class FarmFragment : BaseFragment() {

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var appContext: Context

    @Inject
    lateinit var imageLoader: ImageLoader

    private val adapter: FarmAdapter by lazy {
        FarmAdapter(appContext, imageLoader) {}
    }

    private lateinit var viewModel: FarmViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        viewModel = viewModel(viewModelFactory) {
            observe(failure) {
                notify(R.string.unknown_error)
            }

            observe(farmsDataSource){
                adapter.submitList(it)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvList.adapter = adapter
        viewModel.updateFarms()
    }

    override fun layoutId() = R.layout.fragment_farm

}
