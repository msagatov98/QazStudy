package com.qazstudy.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.qazstudy.databinding.ActivityBookBinding
import com.qazstudy.util.viewBinding
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class ActivityBook : AppCompatActivity() {

    private  val binding by viewBinding(ActivityBookBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBook()
    }

    fun onClick(v: View) {
        when(v) {
            binding.icBack -> finish()
        }
    }

    private fun setBook() {

        when(intent.getIntExtra("bookNum", -1)) {
            0 -> {
                binding.run {
                    bookTitle.text = "Ұсқынсыз үйрек"
                    bookText.text = getBookText("book1")
                }
            }
            1 -> {
                binding.run {
                    bookTitle.text = ""
                    bookText.text = getBookText("book2")
                }
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