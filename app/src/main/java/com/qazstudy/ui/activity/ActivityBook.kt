package com.qazstudy.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.qazstudy.R
import com.qazstudy.ui.activity.ActivityNavigation.Companion.mUser
import kotlinx.android.synthetic.main.activity_book.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class ActivityBook : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book)
        setTheme()
        setBook()
    }

    fun onClick(v: View) {
        when(v) {
            ic_back -> finish()
        }
    }

    private fun setTheme() {
        if (mUser.isDark) {
            book_text.setTextColor(getColor(R.color.white))
            book_text.setBackgroundColor(getColor(R.color.dark))
            toolbar.setBackgroundColor(getColor(R.color.light_blue))
            this.window.statusBarColor = getColor(R.color.light_blue)
        }
    }

    private fun setBook() {

        when(intent.getIntExtra("bookNum", -1)) {
            0 -> {
                book_title.text = "Ұсқынсыз үйрек"
                book_text.text = getBookText("book1")
            }
            1 -> {
                book_title.text = ""
                book_text.text = getBookText("book2")
            }
        }
    }

    private fun getBookText(bookName: String) : String {
        val termsString = StringBuilder()
        val reader: BufferedReader
        try {
            reader = BufferedReader(
                InputStreamReader(assets.open(bookName))
            )
            var str: String?
            while (reader.readLine().also { str = it } != null) {

                if (str!!.contains("\\n")) {
                    termsString.append("\n")
                } else {
                    termsString.append(str)
                }
            }
            reader.close()
            return termsString.toString()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return ""
    }

}