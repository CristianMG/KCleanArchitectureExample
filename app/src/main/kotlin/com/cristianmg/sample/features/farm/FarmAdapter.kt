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
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.cristianmg.sample.core.imageloader.ImageLoader
import com.cristianmg.sample.domain.model.Farm

class FarmAdapter(
        private val context: Context,
        private val imageLoader: ImageLoader,
        private val callback: (objectEvent: Farm) -> Unit
) : PagedListAdapter<Farm,FarmViewHolder>(POST_COMPARATOR) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FarmViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(FarmViewHolder.LAYOUT_ID, parent, false)
        return FarmViewHolder(view, callback, imageLoader, context)
    }

    override fun onBindViewHolder(holder: FarmViewHolder, position: Int) =
            holder.bind(getItem(position))

    companion object{

        val POST_COMPARATOR = object : DiffUtil.ItemCallback<Farm>() {
            override fun areContentsTheSame(oldItem: Farm, newItem: Farm): Boolean  = oldItem == newItem
            override fun areItemsTheSame(oldItem: Farm, newItem: Farm): Boolean = oldItem.farmerID == newItem.farmerID
        }

    }
}
