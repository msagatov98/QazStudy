package com.qazstudy.model

import android.net.Uri
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.qazstudy.util.NODE_PHOTO
import com.qazstudy.util.NODE_USER
import kotlinx.android.synthetic.main.dialog_input.view.*

class Firebase {

    lateinit var mAuth: FirebaseAuth
    lateinit var mStorage: StorageReference
    lateinit var mDatabase: DatabaseReference

    init {
        mAuth = FirebaseAuth.getInstance()
        mStorage = FirebaseStorage.getInstance().reference
        mDatabase = FirebaseDatabase.getInstance().reference
    }

    fun updateUser(email: String, password: String) {
        val credential = EmailAuthProvider.getCredential(
            mAuth.currentUser!!.email.toString(),
            password
        )

        mAuth.currentUser!!.reauthenticate(credential)
            .addOnCompleteListener { task1 ->
                if (task1.isSuccessful) {
                    mAuth.currentUser!!.updateEmail(email)
                        .addOnCompleteListener { task2 ->
                            if (task2.isSuccessful) {

                                val updatesMap = mutableMapOf<String, Any>()
                                updatesMap["email"] = email

                                this.updateUser(updatesMap)
                            }
                        }
                }
            }
    }

    fun updateUser(updatesMap: MutableMap<String, Any>) {
        mDatabase.child(NODE_USER).child(mAuth.currentUser!!.uid)
            .updateChildren(updatesMap)
    }

    fun updateUser(imageUri: Uri) {

        mStorage.child(NODE_USER).child(mAuth.currentUser!!.uid).child(NODE_PHOTO)
            .putFile(imageUri)
            .addOnCompleteListener { uploadTask ->
                if (uploadTask.isSuccessful) {

                    val url =
                        mStorage.child(NODE_USER).child(mAuth.currentUser!!.uid).child(NODE_PHOTO)
                            .child("imageUri")

                    mDatabase.child(NODE_USER).child(mAuth.currentUser!!.uid).child(NODE_PHOTO)
                        .setValue(url.downloadUrl.toString())

                }
            }
    }

    fun deleteUser() {
        mStorage.child(NODE_USER).child(mAuth.currentUser!!.uid).child(NODE_PHOTO).delete()
        mDatabase.child(NODE_USER).child(mAuth.currentUser!!.uid).removeValue()
        mAuth.currentUser!!.delete()
    }

    fun signOut() {
        mAuth.signOut()
    }

}