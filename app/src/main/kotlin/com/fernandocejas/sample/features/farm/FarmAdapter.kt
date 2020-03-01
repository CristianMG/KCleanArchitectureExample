package com.fernandocejas.sample.features.farm

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fernandocejas.sample.R
import com.fernandocejas.sample.core.imageloader.ImageLoader
import com.fernandocejas.sample.domain.model.Farm
import com.fernandocejas.sample.domain.model.Task

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
