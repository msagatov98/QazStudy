package com.qazstudy.ui.activity

import com.qazstudy.R
import android.os.Bundle
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import androidx.fragment.app.FragmentManager
import com.qazstudy.presentation.view.ChatView
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.activity_chat.*
import com.qazstudy.presentation.presenter.ChatPresenter
import com.qazstudy.ui.activity.ActivityNavigation.Companion.isDark

class ActivityChat : MvpAppCompatActivity(), ChatView {

    private var fm : FragmentManager = supportFragmentManager

    @InjectPresenter
    lateinit var mChatPresenter: ChatPresenter

    @ProvidePresenter
    fun provideChatPresenter(): ChatPresenter? {
        return ChatPresenter(fm)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        setMode()

        displayChat(intent.getIntExtra("numChat", -1))
        activity_chat__ic_back.setOnClickListener { finish() }

    }

    private fun setMode() {
        if (isDark) {
            bg_chat.setBackgroundColor(getColor(R.color.dark))
            this.window.statusBarColor = getColor(R.color.black)
            activity_chat__toolbar.setBackgroundColor(getColor(R.color.light_blue))
        }
    }

    override fun displayChat(i: Int) {
        mChatPresenter.displayChat(i)
    }
}