package com.qazstudy.ui.adapter

import android.content.Intent
import android.content.res.ColorStateList
import com.qazstudy.R
import android.view.View
import android.view.ViewGroup
import android.graphics.Color
import android.widget.TextView
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.constraintlayout.widget.ConstraintLayout
import com.qazstudy.ui.activity.ActivityNavigation.Companion.isDark
import kotlinx.android.synthetic.main.view_holder_book.view.*
import kotlinx.android.synthetic.main.view_holder_lesson.view.header
import kotlinx.android.synthetic.main.view_holder_lesson.view.container
import kotlinx.android.synthetic.main.view_holder_lesson.view.description

class AdapterBook(private val arr: Array<Int>) : RecyclerView.Adapter<AdapterBook.BookViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_book, parent, false)
        return BookViewHolder(view)
    }

    override fun getItemCount(): Int {
        return arr.size
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.image.setImageResource(arr[position])

    }

    class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var text: TextView = itemView.view_holder_book__text
        var image: ImageView = itemView.view_holder_book__image
        var container: ConstraintLayout = itemView.view_holder_book__container

        init {
            if (isDark) {
                container.setBackgroundResource(R.drawable.card_bg_dark)
                text.setTextColor(Color.rgb(255, 255, 255))
            }
        }
    }
}
