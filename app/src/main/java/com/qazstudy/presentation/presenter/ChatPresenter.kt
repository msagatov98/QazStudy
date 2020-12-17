package com.qazstudy.presentation.presenter

import android.content.Context
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.qazstudy.R
import com.qazstudy.model.Message
import com.qazstudy.presentation.view.ChatView
import com.qazstudy.ui.activity.ActivityNavigation.Companion.isDark
import com.qazstudy.ui.activity.LoginActivity.Companion.mUser
import com.qazstudy.util.NODE_MESSAGE
import kotlinx.android.synthetic.main.view_holder_message.view.chat_city
import kotlinx.android.synthetic.main.view_holder_message.view.chat_country
import kotlinx.android.synthetic.main.view_holder_message.view.chat_message
import kotlinx.android.synthetic.main.view_holder_message.view.chat_time
import kotlinx.android.synthetic.main.view_holder_message.view.chat_user
import kotlinx.android.synthetic.main.view_holder_message.view.ic_chat
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class ChatPresenter(val context: Context) : MvpPresenter<ChatView>() {

    private lateinit var mChatPath: String

    private var STORAGE: StorageReference = FirebaseStorage.getInstance().reference
    private var DATABASE: DatabaseReference = FirebaseDatabase.getInstance().reference

    private lateinit var adapter: FirebaseRecyclerAdapter<Message, MessageViewHolder>
    private lateinit var options: FirebaseRecyclerOptions<Message>

    fun getAdapter(): FirebaseRecyclerAdapter<Message, MessageViewHolder> {
        return adapter
    }

    fun displayChat(position: Int) {

        mChatPath = when (position) {
            0 -> "$NODE_MESSAGE/lectureIntro"
            1 -> "$NODE_MESSAGE/lecture1"
            2 -> "$NODE_MESSAGE/lecture2"
            3 -> "$NODE_MESSAGE/lecture3"
            4 -> "$NODE_MESSAGE/lecture4"
            5 -> "$NODE_MESSAGE/lecture5"
            6 -> "$NODE_MESSAGE/lecture6"
            7 -> "$NODE_MESSAGE/lecture7"
            8 -> "$NODE_MESSAGE/lecture8"
            9 -> "$NODE_MESSAGE/lecture9"
            else -> ""
        }

        val query = DATABASE.child(mChatPath)

        options = FirebaseRecyclerOptions.Builder<Message>()
            .setQuery(query, Message::class.java)
            .build()

        adapter =
            object : FirebaseRecyclerAdapter<Message, MessageViewHolder>(options) {

                override fun onCreateViewHolder(
                    parent: ViewGroup,
                    viewType: Int
                ): MessageViewHolder {

                    val view = if (isDark)
                        LayoutInflater.from(parent.context)
                            .inflate(R.layout.view_holder_message_dark, parent, false)
                    else
                        LayoutInflater.from(parent.context)
                            .inflate(R.layout.view_holder_message, parent, false)

                    return MessageViewHolder(view)
                }

                override fun onBindViewHolder(
                    holder: MessageViewHolder,
                    position: Int,
                    model: Message
                ) {
                    holder.bind(model)
                }

            }
    }

    fun sendMessage(message: String) {
        DATABASE.child(mChatPath).push().setValue(
            Message(mUser, message)
        )
    }

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(model: Message) {

            if (model.userID.photo.isNotEmpty()) {
                STORAGE.child("users/${model.id}/photo").downloadUrl.addOnSuccessListener {
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