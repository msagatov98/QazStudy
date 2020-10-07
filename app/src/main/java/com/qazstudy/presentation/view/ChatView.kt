package com.qazstudy.presentation.view

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEnd


interface ChatView: MvpView {

    @AddToEnd
    fun displayChat()
}