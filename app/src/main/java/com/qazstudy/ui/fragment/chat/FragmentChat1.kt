package com.qazstudy.ui.fragment.chat

import com.qazstudy.R
import android.os.Bundle
import android.text.format.DateFormat
import android.view.View
import android.view.ViewGroup
import com.qazstudy.model.Message
import com.qazstudy.util.showToast
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import com.qazstudy.util.displayChat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseListAdapter
import com.firebase.ui.database.FirebaseListOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.qazstudy.model.User
import com.qazstudy.ui.activity.ActivityNavigation
import kotlinx.android.synthetic.main.fragment_chat1.*
import com.qazstudy.ui.activity.ActivityNavigation.Companion.mUser
import com.qazstudy.ui.activity.ActivityNavigation.Companion.isDark
import com.qazstudy.ui.adapter.ValueEventListenerAdapter
import kotlinx.android.synthetic.main.navigation_header.*
import kotlinx.android.synthetic.main.view_holder_message.view.*
import moxy.InjectViewState
import moxy.MvpAppCompatFragment
import moxy.MvpPresenter
import moxy.MvpView
import moxy.presenter.InjectPresenter

class FragmentChat1 : MvpAppCompatFragment(), ChatView {

    @InjectPresenter
    lateinit var mChatPresenter: ChatPresenter


    lateinit var mUser : User
    var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    var mStorage: StorageReference = FirebaseStorage.getInstance().reference
    var mDatabase: DatabaseReference = FirebaseDatabase.getInstance().reference

    init {
        mDatabase.child("users/${mAuth.currentUser!!.uid}")
            .addListenerForSingleValueEvent(ValueEventListenerAdapter {
                mUser = it.getValue(User::class.java)!!
            })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (isDark) {
            input_chat_message.setTextColor(requireContext().getColor(R.color.white))
            input_chat_message.background = ContextCompat.getDrawable(requireContext(), R.drawable.bg_input_chat_message_dark)
        }


        ic_send.setOnClickListener {
            if (input_chat_message.text.isNotEmpty()) {
                mDatabase.child("messages/lecture1").push().setValue(
                    Message(mUser, input_chat_message.text.toString())
                )
                input_chat_message.setText("")
            } else {
                requireContext().showToast("You can't send empty message")
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_chat1, container, false)
    }

    override fun displayChat() {
        val adapter: FirebaseListAdapter<Message>
        val options: FirebaseListOptions<Message>

        val query = mDatabase.child("messages/lecture1")

        options = if (isDark) {
            FirebaseListOptions.Builder<Message>()
                .setQuery(query, Message::class.java)
                .setLayout(R.layout.view_holder_message_dark)
                .build()
        } else {
            FirebaseListOptions.Builder<Message>()
                .setQuery(query, Message::class.java)
                .setLayout(R.layout.view_holder_message)
                .build()
        }

        adapter = object : FirebaseListAdapter<Message>(options) {
            override fun populateView(v: View, model: Message, position: Int) {
                if (model.userID.photo.isNotEmpty()) {
                    mStorage.child("users/${model.id}/photo").downloadUrl.addOnSuccessListener {
                        Glide.with(requireActivity()).load(it.toString()).into(v.ic_chat)
                    }
                }
                v.chat_message.text = model.message
                v.chat_user.text = model.userID.name
                v.chat_time.text = DateFormat.format("dd-MM-yyyy HH:mm", model.time)
            }
        }
        chat_list_view.adapter = adapter

    }
}

interface ChatView: MvpView {
    fun displayChat()
}

@InjectViewState
class ChatPresenter() : MvpPresenter<ChatView>() {

    init {
        viewState.displayChat()
    }
}