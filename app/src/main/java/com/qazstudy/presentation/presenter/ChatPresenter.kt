package com.qazstudy.presenter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.qazstudy.R
import com.qazstudy.presentation.view.ChatView
import com.qazstudy.ui.fragment.FragmentChat
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class ChatPresenter: MvpPresenter<ChatView>() {

    private lateinit var openedFragment: Fragment
    private var fm : FragmentManager = supportFragmentManager
    private var ft : FragmentTransaction = fm.beginTransaction()
    
    private val arFragmentChat =
        arrayListOf(
            FragmentChat.newInstance("messages/lecture1"),
            FragmentChat.newInstance("messages/lecture2"),
            FragmentChat.newInstance("messages/lecture3"),
            FragmentChat.newInstance("messages/lecture4"),
            FragmentChat.newInstance("messages/lecture5"),
            FragmentChat.newInstance("messages/lecture6"),
            FragmentChat.newInstance("messages/lecture7"),
            FragmentChat.newInstance("messages/lecture8")
        )

    init {
        for (i in arFragmentChat.indices) {
        if (intent.getIntExtra("numChat", -1) == i) {
            openedFragment = arFragmentChat[i]
            fm = supportFragmentManager
            ft = fm.beginTransaction()
            ft.replace(R.id.activity_chat__fragment, arFragmentChat[i])
            ft.commit()
            break
        }
    }
    }
}