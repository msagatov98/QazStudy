package com.qazstudy.ui.activity

import android.content.Intent
import com.qazstudy.R
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.qazstudy.util.showToast
import kotlinx.android.synthetic.main.activity_login.*
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent.setEventListener
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener

class ActivityLogin : AppCompatActivity(), KeyboardVisibilityEventListener, TextWatcher {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setEventListener(this, this)
        activity_login__btn_login.isEnabled = false

        activity_login__input_email.addTextChangedListener(this)
        activity_login__input_password.addTextChangedListener(this)

        txt_sign_up.setOnClickListener { startActivity(Intent(this, ActivityRegister::class.java)) }

        mAuth = FirebaseAuth.getInstance()

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

    private fun validate(email: String, password: String) = email.isNotEmpty() && password.isNotEmpty()

    override fun onVisibilityChanged(isOpen: Boolean) {
        if (isOpen) {
            txt_sign_up.visibility = View.GONE
            activity_login__scroll_view.scrollTo(0, activity_login__scroll_view.bottom)
        } else {
            activity_login__scroll_view.scrollTo(0, activity_login__scroll_view.top)
            txt_sign_up.visibility = View.VISIBLE
        }
    }

    override fun afterTextChanged(s: Editable?) {
        activity_login__btn_login.isEnabled =
            validate(activity_login__input_email.text.toString(), activity_login__input_password.toString())
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }

}