package com.qazstudy.ui.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.qazstudy.databinding.ViewHolderLessonBinding
import com.qazstudy.model.Lesson
import com.qazstudy.ui.activity.ActivityLecture
import com.qazstudy.ui.activity.ActivityNavigation.Companion.isDark

open class AdapterLesson(var context: Context, private val lesson: Lesson) :
    RecyclerView.Adapter<AdapterLesson.LessonViewHolder>() {

    val TAG: String = this.javaClass.name

    val TYPE_SINGLE_ITEM = 1
    val TYPE_DOUBLE_ITEM = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonViewHolder {
        val binding =
            ViewHolderLessonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LessonViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return lesson.header.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0 || position == 5) TYPE_SINGLE_ITEM
        else TYPE_DOUBLE_ITEM
    }

    override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class LessonViewHolder(val binding: ViewHolderLessonBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {

            if (isDark) {
                binding.header.setTextColor(Color.rgb(60, 90, 188))
            }

            Log.i(TAG, position.toString())

            if (position < lesson.icon.size)
                binding.icTask.setImageResource(lesson.icon[position])

            binding.header.text = lesson.header[position]

            binding.icTask.setOnClickListener {
                val intent = Intent(context, ActivityLecture::class.java)
                intent.putExtra("numLesson", position)
                context.startActivity(intent)
            }
        }
    }
}