package com.qazstudy.util

import android.widget.Toast
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.text.format.DateFormat
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseListAdapter
import com.qazstudy.R
import com.qazstudy.model.Message
import com.qazstudy.ui.activity.ActivityNavigation
import com.qazstudy.ui.activity.ActivityNavigation.Companion.isDark
import com.qazstudy.ui.activity.ActivityNavigation.Companion.mDatabase
import com.qazstudy.ui.activity.ActivityNavigation.Companion.mStorage
import kotlinx.android.synthetic.main.fragment_chat1.*
import kotlinx.android.synthetic.main.view_holder_message.view.*

fun Context.showToast(msg: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, msg, duration).show()
}

fun coordinateButtonAndInput(btn: Button, vararg input: EditText) {
    val watcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            btn.isEnabled = input.all { it.text.isNotEmpty()}
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
    }

    input.forEach { it.addTextChangedListener(watcher) }
    btn.isEnabled = input.all { it.text.isNotEmpty()}
}

fun Fragment.displayChat(lecture: String) {

    val adapter: FirebaseListAdapter<Message>

    if (isDark) {
        adapter = object : FirebaseListAdapter<Message>(activity, Message::class.java, R.layout.view_holder_message_dark, mDatabase.child("messages/$lecture/")) {
            override fun populateView(v: View?, model: Message?, position: Int) {
                if (model!!.userID.photo.isNotEmpty()) {
                    mStorage.child("users/${model.id}/photo").downloadUrl.addOnSuccessListener {
                        if (this@displayChat.isVisible) {
                            Glide.with(requireActivity()).load(it.toString()).into(v!!.ic_chat)
                        }
                    }
                }
                v!!.chat_message.text = model.message
                v.chat_user.text = model.userID.name
                v.chat_time.text = DateFormat.format("dd-MM-yyyy HH:mm", model.time)
            }
        }
    } else {
        adapter = object : FirebaseListAdapter<Message>(activity, Message::class.java, R.layout.view_holder_message, mDatabase.child("messages/$lecture/")) {
            override fun populateView(v: View?, model: Message?, position: Int) {
                if (model!!.userID.photo.isNotEmpty()) {
                    mStorage.child("users/${model.id}/photo").downloadUrl.addOnSuccessListener {
                        if (this@displayChat.isVisible) {
                            Glide.with(requireActivity()).load(it.toString()).into(v!!.ic_chat)
                        }
                    }
                }
                v!!.chat_message.text = model.message
                v.chat_user.text = model.userID.name
                v.chat_time.text = DateFormat.format("dd-MM-yyyy HH:mm", model.time)
            }
        }
    }
    chat_list_view.adapter = adapter
}