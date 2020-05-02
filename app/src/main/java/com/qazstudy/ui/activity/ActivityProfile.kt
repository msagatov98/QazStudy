package com.qazstudy.ui.activity

import com.qazstudy.R
import android.os.Bundle
import android.content.Intent
import com.qazstudy.model.User
import android.widget.TextView
import com.qazstudy.view.DialogInput
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_profile.*
import com.qazstudy.ui.adapter.ValueEventListenerAdapter
import com.qazstudy.ui.activity.ActivityNavigation.Companion.mAuth
import com.qazstudy.ui.activity.ActivityNavigation.Companion.isDark
import com.qazstudy.ui.activity.ActivityNavigation.Companion.mDatabase

class ActivityProfile : AppCompatActivity() {

    private lateinit var mUser: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setMode()

        mDatabase.child("users/${mAuth.currentUser!!.uid}").addListenerForSingleValueEvent( ValueEventListenerAdapter{
            mUser = it.getValue(User::class.java)!!
            activity_profile__input_name.setText(mUser.name, TextView.BufferType.EDITABLE)
            activity_profile__input_city.setText(mUser.city, TextView.BufferType.EDITABLE)
            activity_profile__input_email.setText(mUser.email, TextView.BufferType.EDITABLE)
            activity_profile__input_country.setText(mUser.country, TextView.BufferType.EDITABLE)
            activity_profile__input_password.setText(mUser.password, TextView.BufferType.EDITABLE)
        })

        activity_profile__input_city.setOnClickListener {
            DialogInput("city", mUser.password).show(supportFragmentManager, "TAG")
        }
        activity_profile__input_name.setOnClickListener {
            DialogInput("name", mUser.password).show(supportFragmentManager, "TAG")
        }
        activity_profile__input_email.setOnClickListener {
            DialogInput("email", mUser.password).show(supportFragmentManager, "TAG")
        }
        activity_profile__input_country.setOnClickListener {
            DialogInput("country", mUser.password).show(supportFragmentManager, "TAG")
        }
        activity_profile__input_password.setOnClickListener {
            DialogInput("password", mUser.password).show(supportFragmentManager, "TAG")
        }

        activity_profile__ic_back.setOnClickListener { finish() }
        activity_profile__btn_exit.setOnClickListener { mAuth.signOut() }

        mAuth.addAuthStateListener {
            if (it.currentUser == null) {
                startActivity(Intent(this, ActivityLogin::class.java))
                finish()
            }
        }

        activity_profile__btn_delete.setOnClickListener {
            mDatabase.child("users/${mAuth.currentUser!!.uid}").removeValue()
            mAuth.currentUser!!.delete()
        }
    }
    
    private fun setMode() {
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

