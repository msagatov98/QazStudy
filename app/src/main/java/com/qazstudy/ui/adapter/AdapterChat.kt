package com.qazstudy.ui.adapter

import android.content.Context
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.qazstudy.databinding.ViewHolderMessageBinding
import com.qazstudy.model.Firebase
import com.qazstudy.model.Message

class AdapterChat(options: FirebaseRecyclerOptions<Message>, val context: Context) : FirebaseRecyclerAdapter<Message, AdapterChat.MessageViewHolder>(options) {

    val firebase = Firebase()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val binding =
            ViewHolderMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MessageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int, model: Message) {
        holder.bind(model)
    }

    inner class MessageViewHolder(private val binding: ViewHolderMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: Message) {
            binding.run {
                chatMessage.text = model.message
                chatCity.text = model.userID.city
                chatUser.text = model.userID.name
                chatCountry.text = model.userID.country
                chatTime.text = DateFormat.format("dd-MM-yyyy HH:mm", model.time)
                if (model.userID.photo.isNotEmpty()) {
                    firebase.mStorage.child("users/${model.id}/photo").downloadUrl.addOnSuccessListener {
                        Glide.with(context)
                            .load(it.toString())
                            .circleCrop()
                            .into(icChat)
                    }
                }
            }
        }
    }
}