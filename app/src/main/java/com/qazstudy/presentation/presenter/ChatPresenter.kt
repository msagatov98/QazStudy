package com.qazstudy.presenter

import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.qazstudy.R
import com.qazstudy.presentation.view.ChatView
import com.qazstudy.ui.fragment.chat.*
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class ChatPresenter: MvpPresenter<ChatView>() {

    private lateinit var openedFragment: Fragment
    private var fm : FragmentManager = supportFragmentManager
    private var ft : FragmentTransaction = fm.beginTransaction()


    private val arFragmentChat =
        arrayListOf(
            FragmentChat1("messages/lecture1"), FragmentChat2(), FragmentChat3(),
            FragmentChat4(), FragmentChat5(), FragmentChat6(),
            FragmentChat7(), FragmentChat8()
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