package com.qazstudy.ui.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.qazstudy.R
import com.qazstudy.ui.activity.ActivityBook
import com.qazstudy.ui.activity.ActivityNavigation.Companion.isDark
import kotlinx.android.synthetic.main.view_holder_book.view.*

class AdapterBook(private val context: Context, private val arr: Array<Int>) :
    RecyclerView.Adapter<AdapterBook.BookViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder =
        BookViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.view_holder_book, parent, false)
        )

    override fun getItemCount(): Int = arr.size

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var text: TextView = itemView.view_holder_book__text
        var image: ImageView = itemView.view_holder_book__image

        fun bind(position: Int) {

            if (isDark) text.setTextColor(Color.rgb(255, 255, 255))

            image.setImageResource(arr[position])

            image.setOnClickListener {
                val intent = Intent(context, ActivityBook::class.java).putExtra("bookNum", position)
                context.startActivity(intent)
            }
        }
    }
}