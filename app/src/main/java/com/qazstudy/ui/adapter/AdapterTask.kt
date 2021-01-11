package com.qazstudy.ui.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.qazstudy.databinding.ViewHolderTaskBinding
import com.qazstudy.model.Task
import com.qazstudy.ui.activity.ActivityNavigation.Companion.isDark
import com.qazstudy.ui.activity.ActivityTask

class AdapterTask(var context: Context, var task: Task) :
    RecyclerView.Adapter<AdapterTask.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding =
            ViewHolderTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun getItemCount(): Int = task.header.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class TaskViewHolder(val binding: ViewHolderTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            if (isDark)
                binding.header.setTextColor(Color.rgb(60, 90, 188))

            binding.header.text = task.header[position]

            binding.icTask.setOnClickListener {
                val intent = Intent(context, ActivityTask::class.java).putExtra("numTask", position)
                context.startActivity(intent)
            }
        }
    }
}