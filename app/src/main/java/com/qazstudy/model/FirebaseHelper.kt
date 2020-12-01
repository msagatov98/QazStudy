package com.qazstudy.model

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference

class FirebaseHelper {

    lateinit var mUser: User
    lateinit var mAuth: FirebaseAuth
    lateinit var mStorage: StorageReference
    lateinit var mDatabase: DatabaseReference

}