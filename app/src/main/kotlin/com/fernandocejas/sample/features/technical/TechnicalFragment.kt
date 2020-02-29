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
package com.fernandocejas.sample.features.technical

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.fernandocejas.sample.R
import com.fernandocejas.sample.core.extension.observe
import com.fernandocejas.sample.core.extension.viewModel
import com.fernandocejas.sample.core.imageloader.ImageLoader
import com.fernandocejas.sample.core.navigation.Navigator
import com.fernandocejas.sample.core.platform.BaseFragment
import com.fernandocejas.sample.domain.model.Task
import com.fernandocejas.sample.domain.model.User
import kotlinx.android.synthetic.main.fragment_technical.*
import javax.inject.Inject


class TechnicalFragment : BaseFragment() {

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var appContext: Context

    @Inject
    lateinit var imageLoader: ImageLoader

    private val adapter: TaskAdapter by lazy {
        TaskAdapter(appContext, imageLoader) {
            viewModel.completeTask(it)
        }
    }

    private lateinit var viewModel: TechnicalViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        viewModel = viewModel(viewModelFactory) {
            observe(failure) {
                notify(R.string.unknown_error)
            }
            observe(taskUsers) {
                it?.let { handleNewTaskUser(it) }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.observeMyTask()
        rvList.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        rvList.adapter = adapter
    }

    private fun handleNewTaskUser(list: List<Task>) {
        if (list.isNotEmpty()) {
            rvList.visibility = View.VISIBLE
            flPlaceholderNoUsers.visibility = View.GONE
            adapter.setData(list)
        } else {
            rvList.visibility = View.GONE
            flPlaceholderNoUsers.visibility = View.VISIBLE
        }
    }

    override fun layoutId() = R.layout.fragment_technical

}
