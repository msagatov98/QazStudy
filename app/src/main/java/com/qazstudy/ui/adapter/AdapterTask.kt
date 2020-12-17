package com.qazstudy.ui.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.florent37.shapeofview.shapes.PolygonView
import com.qazstudy.R
import com.qazstudy.model.Task
import com.qazstudy.ui.activity.ActivityNavigation.Companion.isDark
import com.qazstudy.ui.activity.ActivityTask
import kotlinx.android.synthetic.main.view_holder_lesson.view.header
import kotlinx.android.synthetic.main.view_holder_task.view.*

class AdapterTask(var context: Context, var task: Task) :
    RecyclerView.Adapter<AdapterTask.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder =
        TaskViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.view_holder_task, parent, false)
        )

    override fun getItemCount(): Int = task.header.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewHeader: TextView = itemView.header
        val icon: PolygonView = itemView.ic_task

        fun bind(position: Int) {
            if (isDark)
                textViewHeader.setTextColor(Color.rgb(60, 90, 188))

            textViewHeader.text = task.header[position]

            icon.setOnClickListener {
                val intent = Intent(context, ActivityTask::class.java).putExtra("numTask", position)
                context.startActivity(intent)
            }
        }

    }
}