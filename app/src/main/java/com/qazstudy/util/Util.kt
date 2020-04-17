package com.qazstudy.util

import android.widget.Toast
import android.content.Context

fun Context.showToast(msg: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, msg, duration).show()
}