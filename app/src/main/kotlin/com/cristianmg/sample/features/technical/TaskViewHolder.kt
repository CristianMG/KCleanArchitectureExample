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
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.cristianmg.sample.R
import com.cristianmg.sample.core.imageloader.ImageLoader
import com.cristianmg.sample.domain.model.Task
import kotlinx.android.synthetic.main.task_row.view.*

class TaskViewHolder(private val view: View,
                     private val callback: (objectEvent: Task) -> Unit,
                     private val imageLoader: ImageLoader,
                     private val context: Context) : RecyclerView.ViewHolder(view) {

    fun bind(toBind: Task) {

        view.tvDescription.text = toBind.description

        imageLoader.resource {
            resource { toBind.typeTask.resourceImage }
        }.load(view.ivTask)

        view.mbAction.setBackgroundColor(ContextCompat.getColor(context, if(toBind.complete) R.color.grey else  R.color.kcleanArchitecture_blue))
        view.mbAction.isEnabled = !toBind.complete
        view.mbAction.text = context.getString(if(toBind.complete) R.string.complete else R.string.to_complete)
        view.mbAction.setOnClickListener {
            callback.invoke(toBind)
        }
    }


    companion object{
        const val LAYOUT_ID = R.layout.task_row
    }
}
