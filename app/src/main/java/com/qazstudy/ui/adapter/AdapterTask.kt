package com.qazstudy.ui.adapter

import android.content.Intent
import com.qazstudy.R
import android.view.View
import android.view.ViewGroup
import android.graphics.Color
import android.widget.TextView
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.view_holder_lesson.view.header
import kotlinx.android.synthetic.main.view_holder_lesson.view.container
import kotlinx.android.synthetic.main.view_holder_lesson.view.description

class AdapterTask(private val header: Array<String>, private val description: Array<String>, private var isDark : Boolean) : RecyclerView.Adapter<AdapterTask.LessonAndTaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonAndTaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_task, parent, false)
        return LessonAndTaskViewHolder(
            view,
            isDark
        )
    }

    override fun getItemCount(): Int {
        return header.size
    }

    override fun onBindViewHolder(holder: LessonAndTaskViewHolder, position: Int) {
        holder.textViewHeader.text = header[position]
        holder.textViewDescription.text = description[position]
        holder.constraintLayout!!.setOnClickListener {

        }

    }

    class LessonAndTaskViewHolder(itemView: View, isDark : Boolean) : RecyclerView.ViewHolder(itemView) {
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
