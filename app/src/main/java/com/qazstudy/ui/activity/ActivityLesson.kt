package com.qazstudy.ui.activity

import com.qazstudy.R
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.qazstudy.ui.fragment.FragmentLecture1
import com.qazstudy.ui.fragment.FragmentLecture2
import kotlinx.android.synthetic.main.activity_lesson.*
import com.qazstudy.ui.activity.ActivityNavigation.Companion.isDark

class ActivityLesson : AppCompatActivity() {

    private var fm : FragmentManager = supportFragmentManager
    private var ft : FragmentTransaction = fm.beginTransaction()
    private val arFragmentLecture = arrayListOf(FragmentLecture1(), FragmentLecture2())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lesson)
        setTheme()
        activity_lesson__ic_back.setOnClickListener { finish() }

        for (i in arFragmentLecture.indices) {
            if (intent.getIntExtra("numLesson", -1) == i) {
                Toast.makeText(this, intent.getIntExtra("numLesson", -1).toString(), Toast.LENGTH_LONG).show()
                fm = supportFragmentManager
                ft = fm.beginTransaction()
                ft.add(R.id.fragment_container_lecture, arFragmentLecture[i])
                ft.commit()
                break
            }
        }
    }

    private fun setTheme() {
        if (isDark) {
            activity_lesson__toolbar.setBackgroundColor(getColor(R.color.light_blue))
            activity_lesson__constraint_layout.setBackgroundColor(getColor(R.color.dark))
            activity_lesson__ic_back.setImageDrawable(getDrawable(R.drawable.ic_back_dark))
            activity_lesson__ic_bookmark.setImageDrawable(getDrawable(R.drawable.ic_bookmark_dark))
        }
    }
}
