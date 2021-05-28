package com.qazstudy.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.qazstudy.databinding.ActivityLoginBinding
import com.qazstudy.model.Firebase
import com.qazstudy.model.User
import com.qazstudy.util.hideKeyboard
import com.qazstudy.util.showToast
import com.qazstudy.util.viewBinding

class LoginActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityLoginBinding::inflate)
    private val firebase = Firebase()

    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) = Unit

        override fun onTextChanged(
            charSequence: CharSequence,
            i: Int,
            i2: Int,
            i3: Int
        ) {
            binding.run {
                val email = emailInput.text.toString()
                val password = inputPassword.text.toString()
                btnLogin.isEnabled = email.isNotEmpty() && password.isNotEmpty()
            }
        }

        override fun afterTextChanged(editable: Editable) {}
    }

    companion object {
        lateinit var mUser: User
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.emailInput.addTextChangedListener(textWatcher)
        binding.inputPassword.addTextChangedListener(textWatcher)
    }

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
            binding.progressBarLayout.visibility = View.VISIBLE
            firebase.mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    startActivity(Intent(this, ActivityNavigation::class.java))
                    finish()
                } else {
                    binding.progressBarLayout.visibility = View.GONE
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