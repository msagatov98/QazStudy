package com.qazstudy.ui.activity

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.qazstudy.R

import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.app_bar_navigation.*

class ProfileActivity() : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val isDark = intent.getBooleanExtra("isDark", false)

        activity_profile__ic_back.setOnClickListener { finish() }
        setMode(isDark)
    }

    private fun setMode(isDark: Boolean) {
        if (isDark) {
            activity_profile__toolbar_txt.setTextColor(resources.getColor(R.color.dark))
            activity_profile__toolbar.background = resources.getDrawable(R.color.light_blue)
            activity_profile__ic_back.setImageDrawable(resources.getDrawable(R.drawable.ic_back_dark))

            activity_profile__input_name.setTextColor(resources.getColor(R.color.white))
            activity_profile__input_city.setTextColor(resources.getColor(R.color.white))
            activity_profile__input_email.setTextColor(resources.getColor(R.color.white))
            activity_profile__input_country.setTextColor(resources.getColor(R.color.white))
            activity_profile__input_password.setTextColor(resources.getColor(R.color.white))

            activity_profile__input_name.setHintTextColor(resources.getColor(R.color.txt_color))
            activity_profile__input_city.setHintTextColor(resources.getColor(R.color.txt_color))
            activity_profile__input_email.setHintTextColor(resources.getColor(R.color.txt_color))
            activity_profile__input_country.setHintTextColor(resources.getColor(R.color.txt_color))
            activity_profile__input_password.setHintTextColor(resources.getColor(R.color.txt_color))

            activity_profile__input_name.background.setTint(resources.getColor(R.color.txt_color))
            activity_profile__input_city.background.setTint(resources.getColor(R.color.txt_color))
            activity_profile__input_email.background.setTint(resources.getColor(R.color.txt_color))
            activity_profile__input_country.background.setTint(resources.getColor(R.color.txt_color))
            activity_profile__input_password.background.setTint(resources.getColor(R.color.txt_color))

            activity_profile__btn_exit.setTextColor(resources.getColor(R.color.light_blue))
            activity_profile__btn_exit.background = resources.getDrawable(R.drawable.bg_btn_exit_dark)
            activity_profile__btn_delete.background = resources.getDrawable(R.drawable.bg_btn_delete_account_dark)
            activity_profile__constraint_layout.background = resources.getDrawable(R.drawable.activity_profile__bg_edittext_dark)
        }
    }
}
