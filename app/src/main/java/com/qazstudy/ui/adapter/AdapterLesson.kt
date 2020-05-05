package com.qazstudy.ui.adapter

import com.qazstudy.R
import android.view.View
import android.graphics.Color
import android.content.Intent
import android.view.ViewGroup
import android.content.Context
import android.widget.TextView
import android.widget.ImageView
import com.qazstudy.model.Lesson
import android.view.LayoutInflater
import com.qazstudy.ui.activity.ActivityLecture
import androidx.recyclerview.widget.RecyclerView
import androidx.constraintlayout.widget.ConstraintLayout
import com.qazstudy.ui.activity.ActivityNavigation.Companion.isDark
import kotlinx.android.synthetic.main.view_holder_lesson.view.*

class AdapterLesson(var context: Context, private val lesson: Lesson) : RecyclerView.Adapter<AdapterLesson.LessonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_lesson, parent, false)
        return LessonViewHolder(view)
    }

    override fun getItemCount(): Int {
        return lesson.header.size
    }

    override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {
        holder.icTask.setImageResource(lesson.icon[position])
        holder.textViewHeader.text = lesson.header[position]
        holder.textViewDescription.text = lesson.description[position]

        holder.constraintLayout!!.setOnClickListener {
            val intent = Intent(context, ActivityLecture::class .java)
            intent.putExtra("numLesson", position)
            context.startActivity(intent)
        }
    }

    class LessonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var icTask: ImageView = itemView.ic_task
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