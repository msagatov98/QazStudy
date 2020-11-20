package com.qazstudy.model

import com.google.firebase.auth.FirebaseAuth
import java.util.*
import com.qazstudy.ui.activity.ActivityNavigation.Companion.mUser


private val AUTH = FirebaseAuth.getInstance()

data class Message(val userID: User = mUser, val message: String = "", val time: Long = Calendar.getInstance().timeInMillis) {
    val id: String = AUTH.currentUser!!.uid
}