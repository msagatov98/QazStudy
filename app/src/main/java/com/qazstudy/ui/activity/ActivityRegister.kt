package com.qazstudy.ui.activity

import com.qazstudy.R
import android.os.Bundle
import android.content.Intent
import android.net.Uri
import com.qazstudy.model.User
import com.qazstudy.util.showToast
import com.qazstudy.ui.fragment.FragmentEmail
import androidx.appcompat.app.AppCompatActivity
import com.qazstudy.ui.fragment.FragmentNamePass
import com.qazstudy.ui.activity.ActivityNavigation.Companion.mAuth
import com.qazstudy.ui.activity.ActivityNavigation.Companion.mDatabase
import com.qazstudy.ui.activity.ActivityNavigation.Companion.mStorage

class ActivityRegister : AppCompatActivity(), FragmentEmail.Listener, FragmentNamePass.Listener {

    private var mEmail: String? = null
    private var mImageURI : Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        supportFragmentManager.beginTransaction().add(R.id.activity_register__frame_layout, FragmentEmail()).commit()
    }

    override fun onNext(email: String, imageUri: Uri?) {
        if (email.isNotEmpty()) {
            mEmail = email
            if (imageUri != null)
                mImageURI = imageUri

            mAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener {
                if (it.isSuccessful) {
                    if (it.result?.signInMethods?.isEmpty() != false) {
                        supportFragmentManager.beginTransaction().replace(R.id.activity_register__frame_layout, FragmentNamePass()).addToBackStack(null).commit()
                    } else {
                        showToast("This email is registered")
                    }
                } else {
                    showToast(it.exception!!.message!!)
                }
            }

        } else {
            showToast("Enter email")
        }
    }

    override fun onRegister(name: String, password: String, repeatPassword: String) {
        if (name.isNotEmpty() && password.isNotEmpty() && repeatPassword.isNotEmpty()) {
            if (password == repeatPassword) {
                val email = mEmail
                if (email != null) {
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {authRes ->
                        if (authRes.isSuccessful) {
                            val user = User(name = name, email = email, password =  password)
                            mDatabase.child("users/${authRes.result!!.user!!.uid}").setValue(user)
                                .addOnCompleteListener { it ->
                                    if (it.isSuccessful) {
                                        if (mImageURI != null) {
                                            mStorage.child("users/${authRes.result!!.user!!.uid}/photo").putFile(mImageURI!!).addOnCompleteListener {
                                                if (it.isSuccessful) {
                                                    val url = mStorage.child("users/${authRes.result!!.user!!.uid}/photo/$mImageURI")
                                                    mDatabase.child("users/${authRes.result!!.user!!.uid}/photo").setValue(url.downloadUrl.toString()).addOnCompleteListener {
                                                        if (it.isSuccessful) {
                                                            startActivity(Intent(this, ActivityNavigation::class.java))
                                                            finish()
                                                        } else {
                                                            showToast(it.exception!!.message.toString())
                                                        }
                                                    }
                                                } else {
                                                    showToast(it.exception!!.message.toString())
                                                }
                                            }
                                        } else {
                                            startActivity(Intent(this, ActivityNavigation::class.java))
                                            finish()
                                        }
                                    } else {
                                        showToast("Failed to create database")
                                    }
                                }
                        } else {
                            showToast("Failed to create auth")
                        }
                    }
                } else {
                    showToast("Enter email")
                    supportFragmentManager.popBackStack()
                }
            } else {
                showToast("Password is incorrect")
            }
        } else {
            showToast("Enter name or password")
        }
    }
}