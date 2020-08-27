package com.qazstudy.ui.fragment.chat

import com.qazstudy.R
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.qazstudy.model.Message
import com.qazstudy.util.showToast
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import com.qazstudy.util.displayChat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_chat1.*
import com.qazstudy.ui.activity.ActivityNavigation.Companion.mUser
import com.qazstudy.ui.activity.ActivityNavigation.Companion.isDark
import com.qazstudy.ui.activity.ActivityNavigation.Companion.mDatabase

class FragmentChat1 : Fragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        displayChat("lecture1")

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
}