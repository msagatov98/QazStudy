package com.qazstudy.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.qazstudy.databinding.ActivityLoginBinding
import com.qazstudy.util.showToast

class LoginActivity : AppCompatActivity() {

    private lateinit var AUTH: FirebaseAuth

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
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

    override fun onBackPressed() {
        this.finish()
    }
}