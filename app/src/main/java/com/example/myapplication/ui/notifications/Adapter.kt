package com.example.myapplication.ui.notifications

import android.graphics.Paint
import android.widget.CheckBox
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.example.myapplication.R
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.ui.dashboard.Item

class TaskAdapter(private val tasks: MutableList<Task>) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)
        val taskText: TextView = itemView.findViewById(R.id.taskText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.taskText.text = task.text
        holder.checkBox.setOnCheckedChangeListener(null)
        holder.checkBox.isChecked = task.isDone

        holder.taskText.paintFlags = if (task.isDone)
            holder.taskText.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        else
            holder.taskText.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()

        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            task.isDone = isChecked
            notifyItemChanged(holder.adapterPosition)
        }
    }

    override fun getItemCount() = tasks.size
}
