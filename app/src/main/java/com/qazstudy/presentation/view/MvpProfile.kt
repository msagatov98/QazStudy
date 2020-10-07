package com.qazstudy.presentation.view

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEnd

interface MvpProfile : MvpView {

    @AddToEnd
    fun showDialog(message: String)

    @AddToEnd
    fun initProfile()

}