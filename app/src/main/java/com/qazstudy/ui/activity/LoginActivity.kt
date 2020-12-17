package com.qazstudy.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.qazstudy.databinding.ActivityBookBinding
import com.qazstudy.databinding.ActivityLoginBinding
import com.qazstudy.model.Firebase
import com.qazstudy.model.User
import com.qazstudy.presentation.presenter.LoginPresenter
import com.qazstudy.presentation.view.LoginView
import com.qazstudy.util.NODE_USER
import com.qazstudy.util.showToast
import com.qazstudy.util.viewBinding
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter

class LoginActivity : MvpAppCompatActivity(), LoginView {

    @InjectPresenter
    lateinit var presenter: LoginPresenter

    companion object {
        lateinit var mUser: User
    }

    private val firebase = Firebase()

    private  val binding by viewBinding(ActivityLoginBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    fun onClick(view: View) {
        when(view) {
            binding.btnLogin -> login()
            binding.textRegister -> register()
        }
    }

    private fun login() {
        val email = binding.inputEmail.text.toString()
        val password = binding.inputPassword.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            firebase.mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {

                    startActivity(Intent(this, ActivityNavigation::class.java))
                    finish()
                } else {
                    showToast("Неверный адрес электронной почты или пароль")
                }
            }
        } else {
            showToast("Введите адрес электронной почты и пароль")
        }
    }

    private fun register() {
        val intent = Intent(this, RegistrationActivity::class.java)
        startActivity(intent)
    }
}