package com.qazstudy.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.qazstudy.R
import com.qazstudy.ui.fragment.FragmentEmail

class ActivityRegister : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        supportFragmentManager.beginTransaction().add(R.id.activity_register__frame_layout, FragmentEmail()).commit()
    }
}


