package com.qazstudy.presentation.view

import moxy.viewstate.strategy.alias.AddToEnd
interface MvpProfile : BaseView {

    @AddToEnd
    fun showDialog(message: String)

    @AddToEnd
    fun initProfile()

}