package com.qazstudy.model

import com.qazstudy.ui.activity.ActivityNavigation.Companion.mAuth
import com.qazstudy.ui.activity.ActivityNavigation.Companion.mUser
import java.util.*

data class Message(val userID: User = mUser, val message: String = "", val time: Long = Calendar.getInstance().timeInMillis) {
    val id: String = mAuth.currentUser!!.uid
}