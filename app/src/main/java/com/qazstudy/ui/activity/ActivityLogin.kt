package com.qazstudy.ui.activity

import com.qazstudy.R
import android.os.Bundle
import android.view.View
import android.content.Intent
import com.qazstudy.util.showToast
import com.google.firebase.auth.FirebaseAuth
import androidx.appcompat.app.AppCompatActivity
import com.qazstudy.ui.activity.ActivityNavigation.Companion.mAuth
import com.qazstudy.util.coordinateButtonAndInput
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_profile.*
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent.setEventListener

class ActivityLogin : AppCompatActivity(), KeyboardVisibilityEventListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setEventListener(this, this)
        activity_login__btn_login.isEnabled = false

        coordinateButtonAndInput(activity_login__btn_login,
            activity_login__input_email, activity_login__input_password)

        txt_sign_up.setOnClickListener { startActivity(Intent(this, ActivityRegister::class.java)) }

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

    override fun onVisibilityChanged(isOpen: Boolean) {
        if (isOpen) {
            txt_sign_up.visibility = View.GONE
        } else {
            txt_sign_up.visibility = View.VISIBLE
        }
    }

    private fun validate(email: String, password: String) = email.isNotEmpty() && password.isNotEmpty()
}
