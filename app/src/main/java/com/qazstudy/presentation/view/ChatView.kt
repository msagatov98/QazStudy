package com.qazstudy.presentation.view

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEnd


interface ChatView: BaseView {

    @AddToEnd
    fun displayChat(i : Int)

}