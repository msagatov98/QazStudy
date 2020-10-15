package com.qazstudy.ui.activity

import com.qazstudy.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.qazstudy.ui.fragment.chat.*
import androidx.fragment.app.FragmentManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.qazstudy.presenter.ChatPresenter
import com.qazstudy.presenter.ProfilePresenter
import kotlinx.android.synthetic.main.activity_chat.*
import com.qazstudy.ui.activity.ActivityNavigation.Companion.isDark
import moxy.presenter.InjectPresenter

class ActivityChat : AppCompatActivity() {

    @InjectPresenter
    lateinit var mChatPresenter: ChatPresenter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        setMode()
        activity_chat__ic_back.setOnClickListener { finish() }
    }

    private fun setMode() {
        if (isDark) {
            bg_chat.setBackgroundColor(getColor(R.color.dark))
            this.window.statusBarColor = getColor(R.color.black)
            activity_chat__toolbar.setBackgroundColor(getColor(R.color.light_blue))
        }
    }
}