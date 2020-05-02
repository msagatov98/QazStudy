package com.qazstudy.ui.fragment.chat

import com.qazstudy.R
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.qazstudy.model.Message
import com.qazstudy.util.showToast
import android.view.LayoutInflater
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.firebase.ui.database.FirebaseListAdapter
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.fragment_chat1.*
import kotlinx.android.synthetic.main.view_holder_message.view.*
import com.qazstudy.ui.activity.ActivityNavigation.Companion.isDark

class FragmentChat2 : Fragment() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDatabase: DatabaseReference
    private lateinit var adapter : FirebaseListAdapter<Message>

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance().reference

        if (isDark) {
            ic_send.background = requireContext().getDrawable(R.drawable.bg_btn_send_dark)
            //ic_send.setImageDrawable(requireContext().getDrawable(R.drawable.ic_send_dark))
            input_chat_message.background = requireContext().getDrawable(R.drawable.bg_input_chat_message_dark)
        }

        displayChat()

        ic_send.setOnClickListener {
            if (input_chat_message.text.isNotEmpty()) {
                mDatabase.child("messages/lecture2/").push().setValue(
                    Message(
                        input_chat_message.text.toString(),
                        mAuth.currentUser!!.email.toString()
                    )
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

    private fun displayChat() {
        if (isDark) {
            adapter = object : FirebaseListAdapter<Message>(activity, Message::class.java, R.layout.view_holder_message_dark, mDatabase.child("messages/lecture2/")) {
                override fun populateView(v: View?, model: Message?, position: Int) {
                    v!!.chat_message.text = model!!.message
                    v.chat_user.text = model.user
                    v.chat_time.text = DateFormat.format("dd-MM-yyyy HH:mm", model.time)
                }
            }
        } else {
            adapter = object : FirebaseListAdapter<Message>(activity, Message::class.java, R.layout.view_holder_message, mDatabase.child("messages/lecture2/")) {
                override fun populateView(v: View?, model: Message?, position: Int) {
                    v!!.chat_message.text = model!!.message
                    v.chat_user.text = model.user
                    v.chat_time.text = DateFormat.format("dd-MM-yyyy HH:mm", model.time)
                }
            }
        }

        chat_list_view.adapter = adapter
    }
}
