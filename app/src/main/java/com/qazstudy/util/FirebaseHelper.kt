package com.qazstudy.util

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.database.FirebaseDatabase

var AUTH = FirebaseAuth.getInstance()
var USER_UID = AUTH.currentUser?.uid.toString()
var STORAGE = FirebaseStorage.getInstance().reference
var DATABASE = FirebaseDatabase.getInstance().reference

const val NODE_USER = "users"
const val NODE_PHOTO = "photo"
const val NODE_MESSAGE = "messages"
