package com.qazstudy.presentation.presenter

import com.qazstudy.R
import moxy.MvpPresenter
import moxy.InjectViewState
import com.qazstudy.ui.fragment.FragmentChat
import androidx.fragment.app.FragmentManager
import com.qazstudy.presentation.view.ChatView

@InjectViewState
class ChatPresenter(var fm: FragmentManager) : MvpPresenter<ChatView>() {

    private val arFragmentChat =
        arrayListOf(
            FragmentChat.newInstance("messages/lectureIntro"),
            FragmentChat.newInstance("messages/lecture1"),
            FragmentChat.newInstance("messages/lecture2"),
            FragmentChat.newInstance("messages/lecture3"),
            FragmentChat.newInstance("messages/lecture4"),
            FragmentChat.newInstance("messages/lecture5"),
            FragmentChat.newInstance("messages/lecture6"),
            FragmentChat.newInstance("messages/lecture7"),
            FragmentChat.newInstance("messages/lecture8"),
            FragmentChat.newInstance("messages/lecture9")
        )

    fun displayChat(i : Int) {
        val ft = fm.beginTransaction()
        ft.replace(R.id.activity_chat__fragment, arFragmentChat[i])
        ft.commit()
    }
}