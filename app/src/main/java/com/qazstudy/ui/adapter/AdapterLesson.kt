package com.qazstudy.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.qazstudy.databinding.ViewHolderLessonBinding
import com.qazstudy.model.Lesson
import com.qazstudy.ui.activity.ActivityLecture

open class AdapterLesson(var context: Context, private val lesson: Lesson) :
    RecyclerView.Adapter<AdapterLesson.LessonViewHolder>() {

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
            binding.run {
                if (position < lesson.icon.size) {
                    Glide.with(icTask)
                        .load(lesson.icon[position])
                        .circleCrop()
                        .into(icTask)
                }
                header.text = lesson.header[position]
                icTask.setOnClickListener {
                    val intent = Intent(context, ActivityLecture::class.java)
                    intent.putExtra("numLesson", position)
                    context.startActivity(intent)
                }
            }
        }
    }
}