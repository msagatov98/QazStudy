package com.qazstudy.ui.adapter

import com.qazstudy.R
import android.util.Log
import android.view.View
import android.graphics.Color
import android.content.Intent
import android.view.ViewGroup
import android.content.Context
import android.widget.TextView
import android.widget.ImageView
import com.qazstudy.model.Lesson
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.qazstudy.ui.activity.ActivityLecture
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_holder_lesson.view.*
import com.qazstudy.ui.activity.ActivityNavigation.Companion.isDark

open class AdapterLesson(var context: Context, private val lesson: Lesson) : RecyclerView.Adapter<AdapterLesson.LessonViewHolder>() {

    val TAG = "AdapterLesson"

    val TYPE_FIRST_ITEM = 1
    val TYPE_ITEM = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_lesson, parent, false)
        return LessonViewHolder(view)
    }

    override fun getItemCount(): Int {
        return lesson.header.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) TYPE_FIRST_ITEM
               else TYPE_ITEM
    }

    override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {
        holder.bind(position)
    }


    inner class LessonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var header : TextView = itemView.header
        var icTask: ImageView = itemView.ic_task

        fun bind(position: Int) {

            if (isDark) {
                header.setTextColor(Color.rgb(60, 90, 188))
            }

            if (position < lesson.header.size-1)
                icTask.setImageResource(lesson.icon[position])

            header.text = lesson.header[position]

            icTask.setOnClickListener {
                val intent = Intent(context, ActivityLecture::class .java)
                intent.putExtra("numLesson", position)
                context.startActivity(intent)
            }
        }
    }
}