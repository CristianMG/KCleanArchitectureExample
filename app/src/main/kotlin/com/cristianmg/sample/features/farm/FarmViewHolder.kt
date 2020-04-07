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
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.cristianmg.model.Farm
import com.cristianmg.sample.R
import com.cristianmg.sample.core.imageloader.ImageLoader
import kotlinx.android.synthetic.main.farm_row.view.*

class FarmViewHolder(private val view: View,
                     private val callback: (objectEvent: Farm) -> Unit,
                     private val imageLoader: ImageLoader,
                     private val context: Context) : RecyclerView.ViewHolder(view) {

    fun bind(toBind: Farm?) {
        toBind?.let {
            view.tvResultCategory.text = it.category
            view.tvResultItem.text = it.item
            view.tvResultPhone.text = it.phone
            view.tvResultZipCode.text = it.zipcode
            view.setOnClickListener { _ ->
                callback(it)
            }
        }

    }


    companion object {
        const val LAYOUT_ID = R.layout.farm_row
    }
}
