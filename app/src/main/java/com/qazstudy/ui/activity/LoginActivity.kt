package com.qazstudy.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.qazstudy.databinding.ActivityLoginBinding
import com.qazstudy.model.Firebase
import com.qazstudy.model.User
import com.qazstudy.util.hideKeyboard
import com.qazstudy.util.showToast
import com.qazstudy.util.viewBinding

class LoginActivity : AppCompatActivity() {

    companion object {
        lateinit var mUser: User
    }

    private val firebase = Firebase()

    private val binding by viewBinding(ActivityLoginBinding::inflate)

    fun onClick(view: View) {
        when (view) {
            binding.btnLogin -> login()
            binding.textRegister -> register()
        }
    }

    private fun login() {
        hideKeyboard()
        val email = binding.emailInput.text.toString()
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