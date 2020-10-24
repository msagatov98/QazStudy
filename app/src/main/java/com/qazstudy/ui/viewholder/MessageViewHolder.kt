package com.qazstudy.ui.viewholder

import android.text.format.DateFormat
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.qazstudy.model.Message
import com.qazstudy.ui.activity.ActivityNavigation.Companion.mStorage
import kotlinx.android.synthetic.main.view_holder_message.view.*

class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(model: Message) {


        if (model.userID.photo.isNotEmpty()) {
            mStorage.child("users/${model.id}/photo").downloadUrl.addOnSuccessListener {
                //Glide.with(requireActivity()).load(it.toString()).into(itemView.ic_chat)
            }
        }

        itemView.chat_message.text = model.message
        itemView.chat_user.text = model.userID.name
        itemView.chat_time.text = DateFormat.format("dd-MM-yyyy HH:mm", model.time)
    }
}