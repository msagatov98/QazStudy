package com.qazstudy.ui


import android.content.res.ColorStateList
import android.graphics.Color
import com.qazstudy.R
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_holder_lesson.view.*

class LessonAdapter(private val header: Array<String>, private val description: Array<String>, var isDark : Boolean) : RecyclerView.Adapter<LessonAdapter.LessonAndTaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonAndTaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_lesson, parent, false)
        return LessonAndTaskViewHolder(view, isDark)
    }

    override fun getItemCount(): Int {
        return header.size
    }

    override fun onBindViewHolder(holder: LessonAndTaskViewHolder, position: Int) {
        holder.textViewHeader.text = header[position]
        holder.textViewDescription.text = description[position]
    }

    class LessonAndTaskViewHolder(itemView: View, isDark: Boolean) : RecyclerView.ViewHolder(itemView) {
        var textViewHeader : TextView = itemView.header
        var textViewDescription : TextView = itemView.description
        var constraintLayout : ConstraintLayout? = itemView.container

        init {
            if (isDark) {
                constraintLayout!!.setBackgroundResource(R.drawable.card_bg_dark)
                textViewHeader.setTextColor(Color.rgb(254, 90, 19))
            } else {
                constraintLayout!!.setBackgroundResource(R.drawable.card_bg_light)
            }
        }
    }
}