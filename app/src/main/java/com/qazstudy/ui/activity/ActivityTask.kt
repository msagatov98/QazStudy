package com.qazstudy.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.qazstudy.databinding.ActivityTaskBinding

class ActivityTask : AppCompatActivity() {

    private var position = -1

    private lateinit var binding: ActivityTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTaskBinding.inflate(layoutInflater)

        setContentView(binding.root)

        position = intent.getIntExtra("numTask", -1)

        binding.textView2.text = "Task $position"

        binding.activityTaskIcBack.setOnClickListener { finish() }
    }
}
