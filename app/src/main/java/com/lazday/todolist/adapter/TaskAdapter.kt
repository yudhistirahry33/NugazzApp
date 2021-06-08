package com.lazday.todolist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lazday.todolist.database.TaskModel
import com.lazday.todolist.databinding.AdapterTaskBinding

class TaskAdapter(
    var items: ArrayList<TaskModel>,
    var listener: AdapterListener
): RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    class ViewHolder(val binding: AdapterTaskBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        AdapterTaskBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.textTask.text = item.task
        holder.binding.imageCheck.setOnClickListener {
            listener.onCompleted(item)
        }
    }

    fun addList(list: List<TaskModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    interface AdapterListener {
        fun onCompleted(taskModel: TaskModel)
        fun onDetail(taskModel: TaskModel)
    }
}