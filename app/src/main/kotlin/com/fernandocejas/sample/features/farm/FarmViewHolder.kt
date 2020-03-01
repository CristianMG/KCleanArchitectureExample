package com.fernandocejas.sample.features.farm

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.fernandocejas.sample.R
import com.fernandocejas.sample.core.imageloader.ImageLoader
import com.fernandocejas.sample.domain.model.Farm
import com.fernandocejas.sample.domain.model.Task
import kotlinx.android.synthetic.main.farm_row.view.*
import kotlinx.android.synthetic.main.task_row.view.*

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
