package com.qazstudy.presenter

import com.qazstudy.presentation.view.ChatView
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class ChatPresenter() : MvpPresenter<ChatView>() {

    init {
        viewState.displayChat()
    }
}