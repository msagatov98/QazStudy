package com.qazstudy.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.qazstudy.R
import com.qazstudy.model.Message
import com.qazstudy.ui.activity.ActivityNavigation.Companion.isDark
import com.qazstudy.util.showToast
import com.qazstudy.presentation.view.ChatView
import com.qazstudy.ui.activity.ActivityNavigation.Companion.mDatabase
import com.qazstudy.ui.activity.ActivityNavigation.Companion.mUser
import kotlinx.android.synthetic.main.fragment_chat.*
import kotlinx.android.synthetic.main.view_holder_message.view.*
import moxy.MvpAppCompatFragment

class FragmentChat : MvpAppCompatFragment(), ChatView {

    val KEY_CHAT_PATH = "chatPath"

    lateinit var adapter: FirebaseRecyclerAdapter<Message, MessageViewHolder>
    private lateinit var mChatPath: String

    companion object {
        fun newInstance(chatPath: String) : FragmentChat {
            val args = Bundle()
            val fragmentChat = FragmentChat()
            args.putString("chatPath", chatPath)
            fragmentChat.arguments = args
            return fragmentChat
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null)
            mChatPath = savedInstanceState.getString(KEY_CHAT_PATH, "")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        displayChat()

        if (isDark) {
            input_chat_message.setTextColor(requireContext().getColor(R.color.white))
            input_chat_message.background = ContextCompat.getDrawable(
                requireContext(),
                R.drawable.bg_input_chat_message_dark
            )
        }

        adapter.startListening()

        ic_send.setOnClickListener {
            if (input_chat_message.text.isNotEmpty()) {
                mDatabase.child(mChatPath).push().setValue(
                    Message(mUser, input_chat_message.text.toString())
                )
                input_chat_message.setText("")
                displayChat()
            } else {
                requireContext().showToast("You can't send empty message")
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun displayChat() {

        val options: FirebaseRecyclerOptions<Message>

        val query = mDatabase.child(mChatPath)

        options = FirebaseRecyclerOptions.Builder<Message>()
                .setQuery(query, Message::class.java)
                .build()

        adapter = object : FirebaseRecyclerAdapter<Message, MessageViewHolder>(options) {
            /*override fun populateView(v: View, model: Message, position: Int) {
                /*if (model.userID.photo.isNotEmpty()) {
                    mStorage.child("users/${model.id}/photo").downloadUrl.addOnSuccessListener {
                        Glide.with(requireActivity()).load(it.toString()).into(v.ic_chat)
                    }
                }*/
                v.chat_message.text = model.message
                v.chat_user.text = model.userID.name
                v.chat_time.text = DateFormat.format("dd-MM-yyyy HH:mm", model.time)
            }*/

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                val view: View = LayoutInflater.from(parent.context)
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
        requireContext().showToast("Chat displayed")
    }

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(model: Message) {
            itemView.chat_message.text = model.message
            itemView.chat_time.text = model.time.toString()
            itemView.chat_user.text = model.userID.name
            itemView.chat_country.text = model.userID.country
            itemView.chat_city.text = model.userID.city
        }
    }
}