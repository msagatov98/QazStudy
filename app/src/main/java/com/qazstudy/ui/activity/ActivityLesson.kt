package com.qazstudy.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.qazstudy.R
import com.qazstudy.ui.activity.ActivityNavigation.Companion.isDark
import kotlinx.android.synthetic.main.activity_lesson.*

class ActivityLesson : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lesson)
        setTheme()
        activity_lesson__ic_back.setOnClickListener { finish() }

        val ar = resources.getStringArray(R.array.lecture_lesson)

        for (i in ar.indices) {
            if (intent.getIntExtra("numLesson", -1) == i) {

            }
        }
    }

    private fun setTheme() {
        if (isDark) {
            activity_lesson__toolbar.setBackgroundColor(getColor(R.color.light_blue))
            activity_lesson__constraint_layout.setBackgroundColor(getColor(R.color.dark))
            activity_lesson__ic_back.setImageDrawable(getDrawable(R.drawable.ic_back_dark))
        }
    }
}
