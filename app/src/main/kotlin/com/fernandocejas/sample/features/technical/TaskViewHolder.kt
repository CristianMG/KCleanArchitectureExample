package com.fernandocejas.sample.features.technical

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.fernandocejas.sample.R
import com.fernandocejas.sample.core.imageloader.ImageLoader
import com.fernandocejas.sample.domain.model.Task
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
