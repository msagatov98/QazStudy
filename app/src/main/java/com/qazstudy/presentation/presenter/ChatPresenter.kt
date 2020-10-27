package com.qazstudy.presentation.presenter

import com.qazstudy.R
import moxy.MvpPresenter
import moxy.InjectViewState
import com.qazstudy.ui.fragment.FragmentChat
import androidx.fragment.app.FragmentManager
import com.qazstudy.presentation.view.ChatView
import com.qazstudy.util.NODE_MESSAGE

@InjectViewState
class ChatPresenter(var fm: FragmentManager) : MvpPresenter<ChatView>() {

    private val arFragmentChat =
        arrayListOf(
            FragmentChat.newInstance("$NODE_MESSAGE/lectureIntro"),
            FragmentChat.newInstance("$NODE_MESSAGE/lecture1"),
            FragmentChat.newInstance("$NODE_MESSAGE/lecture2"),
            FragmentChat.newInstance("$NODE_MESSAGE/lecture3"),
            FragmentChat.newInstance("$NODE_MESSAGE/lecture4"),
            FragmentChat.newInstance("$NODE_MESSAGE/lecture5"),
            FragmentChat.newInstance("$NODE_MESSAGE/lecture6"),
            FragmentChat.newInstance("$NODE_MESSAGE/lecture7"),
            FragmentChat.newInstance("$NODE_MESSAGE/lecture8"),
            FragmentChat.newInstance("$NODE_MESSAGE/lecture9")
        )

    fun displayChat(i : Int) {
        val ft = fm.beginTransaction()
        ft.replace(R.id.activity_chat__fragment, arFragmentChat[i])
        ft.commit()
    }
}