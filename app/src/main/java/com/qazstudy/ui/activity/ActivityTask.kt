package com.qazstudy.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.qazstudy.R
import kotlinx.android.synthetic.main.activity_task.*

class ActivityTask : AppCompatActivity() {

    private var position = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)

        position = intent.getIntExtra("numTask", -1)

        textView2.text = "Task $position"

        activity_task__ic_back.setOnClickListener { finish() }
    }
}
