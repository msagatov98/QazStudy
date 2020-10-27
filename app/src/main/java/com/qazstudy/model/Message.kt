package com.qazstudy.model

import java.util.*
import com.qazstudy.util.AUTH
import com.qazstudy.ui.activity.ActivityNavigation.Companion.mUser

data class Message(val userID: User = mUser, val message: String = "", val time: Long = Calendar.getInstance().timeInMillis) {
    val id: String = AUTH.currentUser!!.uid
}