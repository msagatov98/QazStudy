package com.qazstudy.ui.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.qazstudy.databinding.ViewHolderBookBinding
import com.qazstudy.ui.activity.ActivityBook
import com.qazstudy.ui.activity.ActivityNavigation.Companion.isDark

class AdapterBook(private val context: Context, private val arr: Array<Int>) :
    RecyclerView.Adapter<AdapterBook.BookViewHolder>() {

    val headers = arrayOf("Ұсқынсыз үйрек")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = ViewHolderBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding)
    }

    override fun getItemCount(): Int = headers.size

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class BookViewHolder(val binding: ViewHolderBookBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {

            binding.viewHolderBookText.text = headers[position]

            if (isDark) binding.viewHolderBookText.setTextColor(Color.rgb(255, 255, 255))

            binding.viewHolderBookImage.setImageResource(arr[position])

            binding.viewHolderBookImage.setOnClickListener {
                val intent = Intent(context, ActivityBook::class.java).putExtra("bookNum", position)
                context.startActivity(intent)
            }
        }
    }
}