package com.qazstudy.ui.adapter

import android.content.Context
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.qazstudy.R
import com.qazstudy.model.Firebase
import com.qazstudy.model.Message
import com.qazstudy.ui.activity.ActivityNavigation
import kotlinx.android.synthetic.main.view_holder_message.view.chat_city
import kotlinx.android.synthetic.main.view_holder_message.view.chat_country
import kotlinx.android.synthetic.main.view_holder_message.view.chat_message
import kotlinx.android.synthetic.main.view_holder_message.view.chat_time
import kotlinx.android.synthetic.main.view_holder_message.view.chat_user
import kotlinx.android.synthetic.main.view_holder_message.view.ic_chat

class AdapterChat(options: FirebaseRecyclerOptions<Message>, val context: Context) : FirebaseRecyclerAdapter<Message, AdapterChat.MessageViewHolder>(options) {

    val firebase = Firebase()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = if (ActivityNavigation.isDark)
            LayoutInflater.from(parent.context).inflate(R.layout.view_holder_message_dark, parent, false)
        else
            LayoutInflater.from(parent.context).inflate(R.layout.view_holder_message, parent, false)

        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int, model: Message) {
        holder.bind(model)
    }

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(model: Message) {

            if (model.userID.photo.isNotEmpty()) {
                firebase.mStorage.child("users/${model.id}/photo").downloadUrl.addOnSuccessListener {
                    Glide.with(context).load(it.toString()).into(itemView.ic_chat)
                }
            }

            itemView.chat_message.text = model.message
            itemView.chat_city.text = model.userID.city
            itemView.chat_user.text = model.userID.name
            itemView.chat_country.text = model.userID.country
            itemView.chat_time.text = DateFormat.format("dd-MM-yyyy HH:mm", model.time)
        }
    }
}