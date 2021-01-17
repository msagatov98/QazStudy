package com.qazstudy.presentation.view

import moxy.viewstate.strategy.alias.AddToEnd

interface ChatView : BaseView {

    @AddToEnd
    fun displayChat(i: Int)

    @AddToEnd
    fun initPath()

    @AddToEnd
    fun sendMessage()

    @AddToEnd()
    fun goToBottom()
}