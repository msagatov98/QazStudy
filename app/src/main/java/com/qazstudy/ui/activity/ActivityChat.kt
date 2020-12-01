package com.qazstudy.ui.activity

import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.qazstudy.R
import com.qazstudy.model.Message
import com.qazstudy.presentation.presenter.ChatPresenter
import com.qazstudy.presentation.view.ChatView
import com.qazstudy.ui.activity.ActivityNavigation.Companion.mUser
import com.qazstudy.util.NODE_MESSAGE
import com.qazstudy.util.showToast
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.view_holder_message.view.*
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class ActivityChat : MvpAppCompatActivity(), ChatView {


    private lateinit var mChatPath: String

    private lateinit var AUTH: FirebaseAuth
    private lateinit var STORAGE: StorageReference
    private lateinit var DATABASE: DatabaseReference


    @InjectPresenter
    lateinit var mChatPresenter: ChatPresenter

    @ProvidePresenter
    fun provideChatPresenter(): ChatPresenter {
        return ChatPresenter()
    }


    private lateinit var adapter: FirebaseRecyclerAdapter<Message, ActivityChat.MessageViewHolder>
    private lateinit var options: FirebaseRecyclerOptions<Message>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        setMode()

        val position = intent.getIntExtra("numChat", -1)

        AUTH = FirebaseAuth.getInstance()
        STORAGE = FirebaseStorage.getInstance().reference
        DATABASE = FirebaseDatabase.getInstance().reference

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

                    val view = if (mUser.isDark)
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


        val manager = LinearLayoutManager(this)
        manager.orientation = LinearLayoutManager.VERTICAL
        manager.stackFromEnd = true

        rv_chat.layoutManager = manager
        rv_chat.adapter = adapter

        if (mUser.isDark) {
            input_chat_message.setTextColor(getColor(R.color.white))
            input_chat_message.background = ContextCompat.getDrawable(
                this,
                R.drawable.bg_input_chat_message_dark
            )
        }

        ic_send.setOnClickListener {
            if (input_chat_message.text.isNotEmpty() && mChatPath != "") {
                DATABASE.child(mChatPath).push().setValue(
                    Message(mUser, input_chat_message.text.toString())
                )
                input_chat_message.setText("")
                adapter.notifyDataSetChanged()
            } else {
                showToast(R.string.empty_message_send)
            }
        }


        activity_chat__ic_back.setOnClickListener { finish() }
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onPause() {
        super.onPause()
        adapter.stopListening()
    }

    override fun setMode() {
        if (mUser.isDark) {
            bg_chat.setBackgroundColor(getColor(R.color.dark))
            this.window.statusBarColor = getColor(R.color.black)
            activity_chat__toolbar.setBackgroundColor(getColor(R.color.light_blue))
        }
    }

    override fun displayChat(i: Int) {
        mChatPresenter.displayChat(i)
    }

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(model: Message) {

            if (model.userID.photo.isNotEmpty()) {
                STORAGE.child("users/${model.id}/photo").downloadUrl.addOnSuccessListener {
                    Glide.with(this@ActivityChat).load(it.toString()).into(itemView.ic_chat)
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