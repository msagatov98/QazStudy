package com.qazstudy.uikit

import androidx.annotation.CallSuper
import androidx.viewbinding.ViewBinding

interface PageContract<VB : ViewBinding, VM : ViewModelContract> : ContextHolder {

    val binding: VB

    val vm: VM

    @CallSuper
    fun initialize() = Unit
}