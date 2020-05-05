package com.qazstudy.ui.adapter

import android.content.Context
import android.content.Intent
import com.qazstudy.R
import android.view.View
import android.view.ViewGroup
import android.graphics.Color
import android.widget.TextView
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import androidx.constraintlayout.widget.ConstraintLayout
import com.qazstudy.model.Task
import com.qazstudy.ui.activity.ActivityNavigation.Companion.isDark
import com.qazstudy.ui.activity.ActivityTask
import kotlinx.android.synthetic.main.view_holder_lesson.view.header
import kotlinx.android.synthetic.main.view_holder_lesson.view.container
import kotlinx.android.synthetic.main.view_holder_lesson.view.description

class AdapterTask(var context: Context, var task: Task) : RecyclerView.Adapter<AdapterTask.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder =
        TaskViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.view_holder_task, parent, false)
        )

    override fun getItemCount(): Int = task.header.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
            holder.textViewHeader.text = task.header[position]
            holder.textViewDescription.text = task.description[position]

            holder.constraintLayout!!.setOnClickListener {
                val intent = Intent(context, ActivityTask::class.java).putExtra("numTask", position)
                context.startActivity(intent)
            }
    }

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewHeader : TextView = itemView.header
        var textViewDescription : TextView = itemView.description
        var constraintLayout : ConstraintLayout? = itemView.container

        init {
            if (isDark) {
                constraintLayout!!.setBackgroundResource(R.drawable.card_bg_dark)
                textViewHeader.setTextColor(Color.rgb(60, 90, 188))
            }
        }
    }
}