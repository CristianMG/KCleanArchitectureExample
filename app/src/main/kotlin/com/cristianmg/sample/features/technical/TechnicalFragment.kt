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

package com.cristianmg.sample.features.technical

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
import com.cristianmg.sample.domain.model.Task
import kotlinx.android.synthetic.main.fragment_technical.*
import javax.inject.Inject
import android.view.MenuInflater
import android.view.Menu
import android.view.MenuItem


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
        setHasOptionsMenu(true)
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
        rvList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvList.adapter = adapter
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.technical_menu_fragment, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        when (item.itemId) {
            R.id.goToFarms -> context?.let { navigator.showFarms(it) }
        }
        return true
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
