package com.qazstudy.uikit

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import com.qazstudy.util.doOnCreated
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

private const val DERIVATION_DEFAULT_TITLE = "ContextHolder's derivation"
private const val DERIVATION_EXPECTATION_TITLE =
    "either of Application, Activity or Fragment"

interface ContextHolder {

    suspend fun getUiContext() = suspendCoroutine<Context> { continuation ->
        when (val holder = this) {
            is Application -> continuation.resume(holder.applicationContext)
            is Activity -> continuation.resume(holder)
            is Fragment -> holder.viewLifecycleOwner.lifecycle.doOnCreated {
                continuation.resume(holder.requireContext())
            }
            else -> continuation.resumeWithException(
                WrongClassExtendedException(
                    this::class.qualifiedName ?: DERIVATION_DEFAULT_TITLE,
                    DERIVATION_EXPECTATION_TITLE
                )
            )
        }
    }
}