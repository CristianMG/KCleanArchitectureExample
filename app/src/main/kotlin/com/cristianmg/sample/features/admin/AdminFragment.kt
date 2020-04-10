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

package com.cristianmg.sample.features.admin

import android.content.Context
import android.os.Bundle
import android.view.*
import com.cristianmg.sample.core.platform.BaseFragment

import androidx.databinding.DataBindingUtil
import br.com.ilhasoft.support.validation.Validator
import com.cristianmg.sample.R
import com.cristianmg.sample.core.extension.viewModel
import com.cristianmg.sample.core.navigation.Navigator
import com.cristianmg.sample.databinding.FragmentAdminBinding
import javax.inject.Inject
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.cristianmg.common_objects.exception.Failure
import com.cristianmg.model.TypeTask
import com.cristianmg.sample.core.extension.getResource
import com.cristianmg.sample.core.extension.getResourceString
import com.cristianmg.sample.core.extension.observe
import com.cristianmg.sample.features.splash.RouteFragmentDirections
import kotlinx.android.synthetic.main.toolbar.*


class AdminFragment : BaseFragment(), Validator.ValidationListener {

    @Inject
    lateinit var navigator: Navigator

    private lateinit var viewModel: AdminViewModel
    private lateinit var binding: FragmentAdminBinding
    private lateinit var validator: Validator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        appComponent.inject(this)
        viewModel = viewModel(viewModelFactory) {
            observe(failure) {
                if (it is Failure.UserNotFound) {
                    notify(R.string.the_task_cannot_be_assign_to_user)
                } else {
                    notify(R.string.failure_server_error)
                }
            }
            observe(typeNotSelectionError) {
                if (it == true)
                    notify(R.string.type_task_is_required)
            }

            observe(taskAddSuccessful) {
                context?.let { ctx ->
                    AlertDialog.Builder(ctx)
                            .setTitle(R.string.new_task)
                            .setMessage(getString(R.string.new_task_was_added, it?.name ?: ""))
                            .setPositiveButton(R.string.new_task_again
                            ) { _, _ ->
                                viewModel.newTaskAgain()
                            }
                            .setNegativeButton(R.string.close_session
                            ) { _, _ ->
                                viewModel.closeSession()
                                findNavController().navigate(AdminFragmentDirections.actionAdminFragmentToSplashFragment())
                            }.create().show()

                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(
                inflater, layoutId(), container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.handlers = Handlers()
        validator = Validator(binding)
        validator.setValidationListener(this)
        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.admin_menu_fragment, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        when (item.itemId) {
            R.id.goToFarms -> context?.let { navigator.showFarms(it) }
        }
        return true
    }

    override fun layoutId() = R.layout.fragment_admin

    override fun onValidationError() =
            notify(R.string.validation_error_message_login)

    override fun onValidationSuccess() = viewModel.newTask()

    inner class Handlers {
        fun addTask() = validator.toValidate()

        fun showTaskDialog() {
            context?.let { ctx ->
                val items = TypeTask.getTypeTaskList().map { ctx.getString(it.getResourceString()) }
                val itemSelected = viewModel.typeTask.value ?: 0
                AlertDialog.Builder(ctx)
                        .setTitle(getString(R.string.select_type_task_to_select))
                        .setSingleChoiceItems(items.toTypedArray(), itemSelected) { _, selectedIndex ->
                            viewModel.typeTask.value = selectedIndex
                        }
                        .setPositiveButton(getString(R.string.ok), null)
                        .show()
            }
        }

        fun getTypeTask(index: Int?, context: Context): String {
            return index?.let {
                TypeTask.getTypeTaskList().getOrNull(index)?.getResourceString()?.let { context.getString(it) }
                        ?: ""
            } ?: context.getString(R.string.type_task)
        }

    }
}
