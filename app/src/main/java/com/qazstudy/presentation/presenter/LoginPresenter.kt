package com.qazstudy.presentation.presenter

import android.app.Activity
import com.qazstudy.model.Firebase
import com.qazstudy.presentation.view.LoginView
import com.qazstudy.presentation.view.ProfileView
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class LoginPresenter : MvpPresenter<LoginView>() {

    private val firebase = Firebase()

    fun isUserExist() : Boolean {
        return firebase.isUserExits()
    }

}