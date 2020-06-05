package com.qazstudy.ui.activity

import com.qazstudy.R
import android.os.Bundle
import android.view.View
import android.util.Log
import android.content.Intent
import com.qazstudy.model.User
import com.qazstudy.util.showToast
import androidx.appcompat.app.AppCompatActivity
import com.qazstudy.util.coordinateButtonAndInput
import com.google.firebase.auth.GoogleAuthProvider
import com.google.android.gms.common.api.ApiException
import kotlinx.android.synthetic.main.activity_login.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.qazstudy.ui.activity.ActivityNavigation.Companion.mAuth
import com.qazstudy.ui.activity.ActivityNavigation.Companion.mDatabase
import com.qazstudy.ui.activity.ActivityNavigation.Companion.mStorage
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent.setEventListener

class ActivityLogin : AppCompatActivity(), KeyboardVisibilityEventListener {

    lateinit var mGoogleSignInClient: GoogleSignInClient

    private val TAG = javaClass.name
    private val RC_SIGN_IN = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setEventListener(this, this)
        activity_login__btn_login.isEnabled = false

        coordinateButtonAndInput(activity_login__btn_login,
            activity_login__input_email, activity_login__input_password)

        txt_sign_up.setOnClickListener { startActivity(Intent(this, ActivityRegister::class.java)) }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        ic_google.setOnClickListener {
            val intent = mGoogleSignInClient.signInIntent
            startActivityForResult(intent, RC_SIGN_IN)
        }

        activity_login__btn_login.setOnClickListener {
            val email = activity_login__input_email.text.toString()
            val password = activity_login__input_password.text.toString()
            if (validate(email, password)) {
                mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            startActivity(Intent(this, ActivityNavigation::class.java))
                            finish()
                        } else {
                            showToast("Incorrect email or password")
                        }
                }
            } else {
                showToast("Please enter email or password")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    override fun onVisibilityChanged(isOpen: Boolean) {
        if (isOpen) {
            txt_sign_up.visibility = View.GONE
        } else {
            txt_sign_up.visibility = View.VISIBLE
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")
                    val auth = mAuth.currentUser
                    val googleAccount = GoogleSignIn.getLastSignedInAccount(this)

                    var name = ""
                    var photo = ""
                    var email = ""

                    if (googleAccount!!.givenName!!.isNotEmpty())
                        name = googleAccount.givenName!!

                    if (googleAccount.email!!.isNotEmpty())
                        email = googleAccount.email!!

                    if (googleAccount.photoUrl != null)
                        photo = googleAccount.photoUrl.toString()

                    val user = User(name= name, email = email, photo = photo)

                    mDatabase.child("users/${auth!!.uid}").setValue(user)
                        .addOnCompleteListener { it ->
                            if (it.isSuccessful) {
                                if (user.photo.isNotEmpty()) {
                                    mStorage.child("users/${auth.uid}/photo").putFile(googleAccount.photoUrl!!).addOnCompleteListener {
                                        if (it.isSuccessful) {
                                            val url = mStorage.child("users/${auth.uid}/photo/${photo}")
                                            mDatabase.child("users/${auth.uid}/photo").setValue(url.downloadUrl.toString()).addOnCompleteListener {
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

                    startActivity(Intent(this, ActivityNavigation::class.java))
                    finish()
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                }
            }
    }

    private fun validate(email: String, password: String) = email.isNotEmpty() && password.isNotEmpty()
}