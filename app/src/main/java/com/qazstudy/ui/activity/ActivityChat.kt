package com.qazstudy.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.qazstudy.R
import com.qazstudy.model.Message
import com.qazstudy.util.showToast
import com.firebase.ui.database.FirebaseListAdapter
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_chat.*
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.view_holder_message.view.*
import com.qazstudy.ui.activity.ActivityNavigation.Companion.isDark
import kotlinx.android.synthetic.main.view_holder_message.*
import kotlinx.android.synthetic.main.view_holder_message.view.view_holder__msg

class ActivityChat : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDatabase: DatabaseReference
    private lateinit var adapter : FirebaseListAdapter<Message>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        setMode()
        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance().reference

        displayChat()

        ic_send.setOnClickListener {
            if (input_chat_message.text.isNotEmpty()) {
                mDatabase.child("messages/").push().setValue(
                    Message(
                        input_chat_message.text.toString(),
                        mAuth.currentUser!!.email.toString()
                    )
                )

                input_chat_message.setText("")
            } else {
                showToast("You can't send empty message")
            }
        }
    }

    private fun displayChat() {

        if (isDark) {
            adapter = object : FirebaseListAdapter<Message>(this, Message::class.java, R.layout.view_holder_message_dark, mDatabase.child("messages")) {
                override fun populateView(v: View?, model: Message?, position: Int) {
                    v!!.chat_message.text = model!!.message
                    v.chat_user.text = model.user
                    v.chat_time.text = DateFormat.format("dd-MM-yyyy HH:mm", model.time)
                }
            }
        } else {
            adapter = object : FirebaseListAdapter<Message>(this, Message::class.java, R.layout.view_holder_message, mDatabase.child("messages")) {
                override fun populateView(v: View?, model: Message?, position: Int) {
                    v!!.chat_message.text = model!!.message
                    v.chat_user.text = model.user
                    v.chat_time.text = DateFormat.format("dd-MM-yyyy HH:mm", model.time)
                }
            }
        }

        chat_list_view.adapter = adapter
    }

    private fun setMode() {
        if (isDark) {

            this.window.statusBarColor = getColor(R.color.light_blue)
            activity_chat__toolbar_txt.setTextColor(getColor(R.color.dark))
            activity_chat__toolbar.background = getDrawable(R.color.light_blue)
            activity_chat__ic_back.setImageDrawable(getDrawable(R.drawable.ic_back_dark))

            bg_chat.setBackgroundColor(getColor(R.color.dark))
            input_chat_message.setTextColor(getColor(R.color.white))
            ic_send.background = getDrawable(R.drawable.bg_btn_send_dark)
            ic_send.setImageDrawable(getDrawable(R.drawable.ic_send_dark))
            input_chat_message.background = getDrawable(R.drawable.bg_input_chat_message_dark)
        }
    }
}
