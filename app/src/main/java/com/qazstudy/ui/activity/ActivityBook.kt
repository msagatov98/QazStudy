package com.qazstudy.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.qazstudy.R
import com.qazstudy.ui.activity.ActivityNavigation.Companion.isDark
import kotlinx.android.synthetic.main.activity_book.*

class ActivityBook : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book)

        if (isDark) {
            book_text.setTextColor(getColor(R.color.white))
            book_text.setBackgroundColor(getColor(R.color.dark))
            toolbar.setBackgroundColor(getColor(R.color.light_blue))
            this.window.statusBarColor = getColor(R.color.light_blue)
        }
    }

    fun onClick(v: View) {
        when(v) {
            ic_back -> finish()
        }
    }
}