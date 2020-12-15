package com.qazstudy.model

import com.google.firebase.auth.FirebaseAuth
import com.qazstudy.ui.activity.LoginActivity.Companion.mUser
import java.util.*


private val AUTH = FirebaseAuth.getInstance()

data class Message(val userID: User = mUser, val message: String = "", val time: Long = Calendar.getInstance().timeInMillis) {
    val id: String = AUTH.currentUser!!.uid
}