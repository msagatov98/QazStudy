package com.qazstudy.presentation.presenter

import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import com.qazstudy.model.Firebase
import com.qazstudy.presentation.view.LoginView
import com.qazstudy.ui.activity.ActivityNavigation
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class LoginPresenter : MvpPresenter<LoginView>() {

    private val firebase = Firebase()



}