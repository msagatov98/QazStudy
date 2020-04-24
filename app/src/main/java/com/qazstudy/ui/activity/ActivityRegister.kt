package com.qazstudy.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.qazstudy.R
import com.qazstudy.model.User
import com.qazstudy.ui.fragment.FragmentEmail
import com.qazstudy.ui.fragment.FragmentNamePass
import com.qazstudy.util.showToast
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener

class ActivityRegister : AppCompatActivity(), FragmentEmail.Listener, FragmentNamePass.Listener {

    private var mEmail: String? = null

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance().reference

        supportFragmentManager.beginTransaction().add(R.id.activity_register__frame_layout, FragmentEmail()).commit()
    }

    override fun onNext(email: String) {
        if (email.isNotEmpty()) {
            mEmail = email

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
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                        if (it.isSuccessful) {
                            val user = User(name = name, email = email, password =  password)
                            mDatabase.child("users/${it.result!!.user!!.uid}").setValue(user).addOnCompleteListener {
                                if (it.isSuccessful) {
                                    startActivity(Intent(this, ActivityNavigation::class.java))
                                    finish()
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