package com.fernandocejas.sample.features.technical

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fernandocejas.sample.R
import com.fernandocejas.sample.core.imageloader.ImageLoader
import com.fernandocejas.sample.domain.model.Task

class TaskAdapter(
        private val context: Context,
        private val imageLoader: ImageLoader,
        private val callback: (objectEvent: Task) -> Unit
) : RecyclerView.Adapter<TaskViewHolder>() {

    private val items = mutableListOf<Task>()

    fun setData(newItems: List<Task>) {
        val diffCallback = TaskDiffCallback(items, newItems)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        items.clear()
        items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(TaskViewHolder.LAYOUT_ID, parent, false)
        return TaskViewHolder(view, callback, imageLoader, context)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) =
            holder.bind(items[position])

    override fun getItemCount(): Int =
            items.size

    class TaskDiffCallback(private val oldList: List<Task>, private val newList: List<Task>) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size
        override fun getNewListSize(): Int = newList.size
        override fun areItemsTheSame(oldPosition: Int, newPosition: Int): Boolean =
                oldList[oldPosition].id == newList[newPosition].id
        override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean =
                oldList[oldPosition] == newList[newPosition]
    }
}
