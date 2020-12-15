package com.qazstudy.presentation.presenter

import com.qazstudy.model.Firebase
import com.qazstudy.presentation.view.NavigationView
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class NavigationPresenter : MvpPresenter<NavigationView>() {

    private val firebase = Firebase()

    fun initAuth() {

    }

}