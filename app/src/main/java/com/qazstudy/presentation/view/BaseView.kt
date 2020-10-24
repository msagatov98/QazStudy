package com.qazstudy.presentation.view

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEnd

interface BaseView: MvpView {
    @AddToEnd
    fun setMode()
}