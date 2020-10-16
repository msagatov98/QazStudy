package com.qazstudy.util

import com.qazstudy.R
import android.view.View
import android.widget.Toast
import android.widget.Button
import android.text.Editable
import android.content.Context
import android.widget.EditText
import android.text.TextWatcher
import com.bumptech.glide.Glide
import com.qazstudy.model.Message
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import com.firebase.ui.database.FirebaseListAdapter
import com.firebase.ui.database.FirebaseListOptions
import com.google.firebase.database.ChildEventListener
import kotlinx.android.synthetic.main.fragment_chat.*
import kotlinx.android.synthetic.main.view_holder_message.view.*
import com.qazstudy.ui.activity.ActivityNavigation.Companion.isDark
import com.qazstudy.ui.activity.ActivityNavigation.Companion.mDatabase
import com.qazstudy.ui.activity.ActivityNavigation.Companion.mStorage


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

fun Fragment.displayChat(lectureNum: String) {

    val adapter: FirebaseListAdapter<Message>
    val options: FirebaseListOptions<Message>

    val query = mDatabase.child("messages/$lectureNum")

    if (isDark) {
        options = FirebaseListOptions.Builder<Message>()
            .setQuery(query, Message::class.java)
            .setLayout(R.layout.view_holder_message_dark)
            .build()
    } else {
        options = FirebaseListOptions.Builder<Message>()
            .setQuery(query, Message::class.java)
            .setLayout(R.layout.view_holder_message)
            .build()
    }

    adapter = object : FirebaseListAdapter<Message>(options) {
        override fun populateView(v: View, model: Message, position: Int) {
            if (model.userID.photo.isNotEmpty()) {
                mStorage.child("users/${model.id}/photo").downloadUrl.addOnSuccessListener {
                    if (this@displayChat.isVisible) {
                        Glide.with(requireActivity()).load(it.toString()).into(v.ic_chat)
                    }
                }
            }
            v.chat_message.text = model.message
            v.chat_user.text = model.userID.name
            v.chat_time.text = DateFormat.format("dd-MM-yyyy HH:mm", model.time)
        }
    }
    adapter.startListening()


}