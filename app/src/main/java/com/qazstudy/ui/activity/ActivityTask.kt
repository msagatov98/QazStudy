package com.qazstudy.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.qazstudy.databinding.ActivityBookBinding
import com.qazstudy.databinding.ActivityTaskBinding
import com.qazstudy.util.viewBinding

class ActivityTask : AppCompatActivity() {

    private var position = -1

    private  val binding by viewBinding(ActivityTaskBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        position = intent.getIntExtra("numTask", -1)

        binding.textView2.text = "Task $position"

        binding.activityTaskIcBack.setOnClickListener { finish() }
    }
}
