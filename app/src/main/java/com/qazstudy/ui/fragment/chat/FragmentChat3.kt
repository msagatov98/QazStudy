package com.qazstudy.ui.fragment.chat

import com.qazstudy.R
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.qazstudy.model.Message
import com.qazstudy.util.showToast
import android.view.LayoutInflater
import com.qazstudy.util.displayChat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_chat1.*
import com.qazstudy.ui.activity.ActivityNavigation.Companion.mUser
import com.qazstudy.ui.activity.ActivityNavigation.Companion.isDark
import com.qazstudy.ui.activity.ActivityNavigation.Companion.mDatabase

class FragmentChat3 : Fragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (isDark) {
            input_chat_message.setTextColor(requireContext().getColor(R.color.white))
            input_chat_message.background = requireContext().getDrawable(R.drawable.bg_input_chat_message_dark)
        }

        displayChat("lecture3")

        ic_send.setOnClickListener {
            if (input_chat_message.text.isNotEmpty()) {
                mDatabase.child("messages/lecture3/").push().setValue(
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