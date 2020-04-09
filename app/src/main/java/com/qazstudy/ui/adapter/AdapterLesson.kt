package com.qazstudy.ui.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import com.qazstudy.R
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.qazstudy.ui.activity.ActivityLesson
import com.qazstudy.util.Lesson
import kotlinx.android.synthetic.main.view_holder_lesson.view.*

class AdapterLesson(var context: Context, private val lesson: Lesson, var isDark : Boolean) : RecyclerView.Adapter<AdapterLesson.LessonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_lesson, parent, false)
        return LessonViewHolder(
            view,
            isDark
        )
    }

    override fun getItemCount(): Int {
        return lesson.header.size
    }

    override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {
        holder.textViewHeader.text = lesson.header[position]
        holder.textViewDescription.text = lesson.description[position]

        holder.constraintLayout!!.setOnClickListener {
            val intent = Intent(context, ActivityLesson::class .java)
            intent.putExtra("numLesson", position)
            context.startActivity(intent)
        }
    }

    class LessonViewHolder(itemView: View, isDark: Boolean) : RecyclerView.ViewHolder(itemView) {
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