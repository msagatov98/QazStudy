package com.qazstudy.ui.activity

import com.qazstudy.R
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_profile.*

class ActivityProfile : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val isDark = intent.getBooleanExtra("isDark", false)

        activity_profile__ic_back.setOnClickListener { finish() }
        setMode(isDark)
    }

    private fun setMode(isDark: Boolean) {
        if (isDark) {
            this.window.statusBarColor = getColor(R.color.light_blue)
            activity_profile__toolbar_txt.setTextColor(getColor(R.color.dark))
            activity_profile__toolbar.background = getDrawable(R.color.light_blue)
            activity_profile__ic_back.setImageDrawable(getDrawable(R.drawable.ic_back_dark))

            activity_profile__input_name.setTextColor(getColor(R.color.white))
            activity_profile__input_city.setTextColor(getColor(R.color.white))
            activity_profile__input_email.setTextColor(getColor(R.color.white))
            activity_profile__input_country.setTextColor(getColor(R.color.white))
            activity_profile__input_password.setTextColor(getColor(R.color.white))

            activity_profile__input_name.setHintTextColor(getColor(R.color.txt_color))
            activity_profile__input_city.setHintTextColor(getColor(R.color.txt_color))
            activity_profile__input_email.setHintTextColor(getColor(R.color.txt_color))
            activity_profile__input_country.setHintTextColor(getColor(R.color.txt_color))
            activity_profile__input_password.setHintTextColor(getColor(R.color.txt_color))

            activity_profile__input_name.background.setTint(getColor(R.color.txt_color))
            activity_profile__input_city.background.setTint(getColor(R.color.txt_color))
            activity_profile__input_email.background.setTint(getColor(R.color.txt_color))
            activity_profile__input_country.background.setTint(getColor(R.color.txt_color))
            activity_profile__input_password.background.setTint(getColor(R.color.txt_color))

            activity_profile__btn_exit.setTextColor(getColor(R.color.light_blue))
            activity_profile__btn_exit.background = getDrawable(R.drawable.bg_btn_exit_dark)
            activity_profile__btn_delete.background = getDrawable(R.drawable.bg_btn_delete_account_dark)
            activity_profile__constraint_layout.background = getDrawable(R.drawable.activity_profile__bg_edittext_dark)
        }
    }
}
