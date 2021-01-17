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
import com.qazstudy.model.Firebase
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

    val firebase = Firebase()

    fun sendMessage(message: String) {
        viewState.initPath()

        firebase.mDatabase.child(mChatPath).push().setValue(
            Message(mUser, message)
        ).addOnSuccessListener {
            viewState.goToBottom()
        }
    }

    fun initPath(path: String) {
        mChatPath = path
    }
}