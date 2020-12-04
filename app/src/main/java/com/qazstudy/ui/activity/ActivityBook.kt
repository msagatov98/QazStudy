package com.qazstudy.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.qazstudy.R
import com.qazstudy.databinding.ActivityBookBinding
import com.qazstudy.ui.activity.ActivityNavigation.Companion.mUser
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class ActivityBook : AppCompatActivity() {

    private lateinit var binding: ActivityBookBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setTheme()
        setBook()
    }

    fun onClick(v: View) {
        when(v) {
            binding.icBack -> finish()
        }
    }

    private fun setTheme() {
        if (mUser.isDark) {
            binding.bookText.setTextColor(getColor(R.color.white))
            binding.bookText.setBackgroundColor(getColor(R.color.dark))
            binding.toolbar.setBackgroundColor(getColor(R.color.light_blue))
            this.window.statusBarColor = getColor(R.color.light_blue)
        }
    }

    private fun setBook() {

        when(intent.getIntExtra("bookNum", -1)) {
            0 -> {
                binding.bookTitle.text = "Ұсқынсыз үйрек"
                binding.bookText.text = getBookText("book1")
            }
            1 -> {
                binding.bookTitle.text = ""
                binding.bookText.text = getBookText("book2")
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