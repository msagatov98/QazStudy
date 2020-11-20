package com.qazstudy.ui.fragment

import com.qazstudy.R
import android.view.View
import android.os.Bundle
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.qazstudy.model.Message
import com.qazstudy.util.showToast
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.text.format.DateFormat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_chat.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.view_holder_message.view.*
import com.qazstudy.ui.activity.ActivityNavigation.Companion.mUser

class FragmentChat : Fragment() {

    private val AUTH = FirebaseAuth.getInstance()
    private var STORAGE = FirebaseStorage.getInstance().reference
    private var DATABASE = FirebaseDatabase.getInstance().reference

    private lateinit var mChatPath: String

    private lateinit var adapter: FirebaseRecyclerAdapter<Message, MessageViewHolder>
    private lateinit var options: FirebaseRecyclerOptions<Message>

    companion object {

        const val KEY_CHAT_PATH = "KEY_CHAT_PATH"

        fun newInstance(chatPath: String) : FragmentChat {
            val args = Bundle()
            args.putString(KEY_CHAT_PATH, chatPath)

            val fragmentChat = FragmentChat()
            fragmentChat.arguments = args

            return fragmentChat
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mChatPath = arguments?.getString(KEY_CHAT_PATH, "").toString()

        if (mChatPath != "") {

            val query = DATABASE.child(mChatPath)

            options = FirebaseRecyclerOptions.Builder<Message>()
                .setQuery(query, Message::class.java)
                .build()

            adapter = object : FirebaseRecyclerAdapter<Message, MessageViewHolder>(options) {
                override fun onCreateViewHolder(
                    parent: ViewGroup,
                    viewType: Int
                ): MessageViewHolder {

                    val view = if (mUser.isDark)
                                    LayoutInflater.from(parent.context).inflate(R.layout.view_holder_message_dark, parent, false)
                               else
                                    LayoutInflater.from(parent.context).inflate(R.layout.view_holder_message, parent, false)

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
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val manager =  LinearLayoutManager(requireContext())
        manager.orientation = LinearLayoutManager.VERTICAL
        manager.stackFromEnd = true

        rv_chat.layoutManager = manager
        rv_chat.adapter = adapter

        if (mUser.isDark) {
            input_chat_message.setTextColor(requireContext().getColor(R.color.white))
            input_chat_message.background = ContextCompat.getDrawable(
                requireContext(),
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
                requireContext().showToast(R.string.empty_message_send)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onPause() {
        super.onPause()
        adapter.stopListening()
    }

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(model: Message) {

            if (model.userID.photo.isNotEmpty()) {
                STORAGE.child("users/${model.id}/photo").downloadUrl.addOnSuccessListener {
                    Glide.with(requireActivity()).load(it.toString()).into(itemView.ic_chat)
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