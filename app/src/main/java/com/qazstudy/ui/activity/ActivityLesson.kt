package com.qazstudy.ui.activity

import android.content.Intent
import com.qazstudy.R
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.qazstudy.ui.fragment.FragmentLecture1
import com.qazstudy.ui.fragment.FragmentLecture2
import kotlinx.android.synthetic.main.activity_lesson.*
import com.qazstudy.ui.activity.ActivityNavigation.Companion.isDark
import com.qazstudy.ui.fragment.FragmentLecture3

class ActivityLesson : AppCompatActivity() {

    private lateinit var openedFragment: Fragment
    private var fm : FragmentManager = supportFragmentManager
    private var ft : FragmentTransaction = fm.beginTransaction()
    private val arFragmentLecture = arrayListOf(FragmentLecture1(), FragmentLecture2(), FragmentLecture3())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lesson)
        setTheme()
        activity_lesson__ic_back.setOnClickListener { finish() }

        activity_lesson__ic_chat.setOnClickListener { startActivity(Intent(this, ActivityChat::class.java)) }

        for (i in arFragmentLecture.indices) {
            if (intent.getIntExtra("numLesson", -1) == i) {
                openedFragment = arFragmentLecture[i]
                fm = supportFragmentManager
                ft = fm.beginTransaction()
                ft.replace(R.id.fragment_container_lecture, arFragmentLecture[i])
                ft.commit()
            }
        }

        activity_lesson__ic_next.setOnClickListener {
            ft = fm.beginTransaction()

            when(openedFragment) {
                is FragmentLecture1 -> {
                    ft.replace(R.id.fragment_container_lecture, FragmentLecture2())
                    openedFragment = FragmentLecture2()
                }
                is FragmentLecture2 -> {
                    ft.replace(R.id.fragment_container_lecture, FragmentLecture3())
                    openedFragment = FragmentLecture3()
                }
            }

            ft.commit()
        }

        activity_lesson__ic_previous.setOnClickListener {
            ft = fm.beginTransaction()

            when(openedFragment) {
                is FragmentLecture2 -> {
                    ft.replace(R.id.fragment_container_lecture, FragmentLecture1())
                    openedFragment = FragmentLecture1()
                }
                is FragmentLecture3 -> {
                    ft.replace(R.id.fragment_container_lecture, FragmentLecture2())
                    openedFragment = FragmentLecture2()
                }
            }

            ft.commit()
        }
    }

    private fun setTheme() {
        if (isDark) {
            this.window.statusBarColor = getColor(R.color.light_blue)
            activity_lesson__toolbar.setBackgroundColor(getColor(R.color.light_blue))
            activity_lesson__constraint_layout.setBackgroundColor(getColor(R.color.dark))
            activity_lesson__ic_chat.setImageDrawable(getDrawable(R.drawable.ic_chat_dark))
            activity_lesson__ic_back.setImageDrawable(getDrawable(R.drawable.ic_back_dark))
            activity_lesson__ic_next.setImageDrawable(getDrawable(R.drawable.ic_next_dark))
            activity_lesson__toolbar_bottom.setBackgroundColor(getColor(R.color.light_blue))
            activity_lesson__ic_previous.setImageDrawable(getDrawable(R.drawable.ic_previous_dark))
            activity_lesson__ic_bookmark.setImageDrawable(getDrawable(R.drawable.ic_bookmark_dark))
        }
    }
}
