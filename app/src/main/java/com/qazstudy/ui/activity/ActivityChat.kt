package com.qazstudy.ui.activity

import com.qazstudy.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.qazstudy.ui.fragment.chat.*
import androidx.fragment.app.FragmentManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.activity_chat.*
import com.qazstudy.ui.activity.ActivityNavigation.Companion.isDark

class ActivityChat : AppCompatActivity() {

    private lateinit var openedFragment: Fragment
    private var fm : FragmentManager = supportFragmentManager
    private var ft : FragmentTransaction = fm.beginTransaction()

    private val arFragmentChat = arrayListOf(
        FragmentChat1(), FragmentChat2(), FragmentChat3(),
        FragmentChat4(), FragmentChat5(), FragmentChat6(),
        FragmentChat7(), FragmentChat8()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        setMode()
        activity_chat__ic_back.setOnClickListener { finish() }

        for (i in arFragmentChat.indices) {
            if (intent.getIntExtra("numChat", -1) == i) {
                openedFragment = arFragmentChat[i]
                fm = supportFragmentManager
                ft = fm.beginTransaction()
                ft.replace(R.id.activity_chat__fragment, arFragmentChat[i])
                ft.commit()
            }
        }
    }

    private fun setMode() {
        if (isDark) {
            bg_chat.setBackgroundColor(getColor(R.color.dark))
            this.window.statusBarColor = getColor(R.color.light_blue)
            activity_chat__toolbar_txt.setTextColor(getColor(R.color.dark))
            activity_chat__toolbar.setBackgroundColor(getColor(R.color.light_blue))
            activity_chat__ic_back.setImageDrawable(getDrawable(R.drawable.ic_back_dark))
        }
    }
}