package com.qazstudy.model

import java.util.*

data class Message(val message: String = "", val user: String = "", val time: Long = Calendar.getInstance().timeInMillis)