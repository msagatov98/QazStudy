package com.qazstudy.ui.activity

import android.content.Intent
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.qazstudy.databinding.ActivityBookBinding
import com.qazstudy.databinding.ActivityLoginBinding
import com.qazstudy.presentation.presenter.LoginPresenter
import com.qazstudy.presentation.view.LoginView
import com.qazstudy.util.showToast
import com.qazstudy.util.viewBinding
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter

class LoginActivity : MvpAppCompatActivity(), LoginView {

    @InjectPresenter
    lateinit var presenter: LoginPresenter

    private lateinit var AUTH: FirebaseAuth

    private  val binding by viewBinding(ActivityLoginBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        AUTH = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener {

            val email = binding.inputEmail.text.toString()
            val password = binding.inputPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                AUTH.signInWithEmailAndPassword(email, password).addOnCompleteListener {
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
    }
}