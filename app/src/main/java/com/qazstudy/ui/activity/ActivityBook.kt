package com.qazstudy.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.qazstudy.R
import com.qazstudy.ui.activity.ActivityNavigation.Companion.isDark
import kotlinx.android.synthetic.main.activity_book.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

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

        book_text.text = getBookText()
    }

    private fun getBookText() : String {
        val termsString = StringBuilder()
        val reader: BufferedReader
        try {
            reader = BufferedReader(
                InputStreamReader(assets.open("book1"))
            )
            var str: String?
            while (reader.readLine().also { str = it } != null) {
                termsString.append(str)
            }
            reader.close()
            return termsString.toString()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return ""
    }

    fun onClick(v: View) {
        when(v) {
            ic_back -> finish()
        }
    }
}