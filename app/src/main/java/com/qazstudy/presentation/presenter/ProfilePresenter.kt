package com.qazstudy.presenter

import androidx.fragment.app.DialogFragment
import moxy.MvpPresenter
import moxy.InjectViewState
import com.qazstudy.ui.activity.ActivityNavigation.Companion.mAuth
import com.qazstudy.ui.activity.ActivityNavigation.Companion.mDatabase
import com.qazstudy.ui.activity.ActivityNavigation.Companion.mStorage
import com.qazstudy.ui.activity.ActivityNavigation.Companion.mUser
import com.qazstudy.presentation.view.MvpProfile
import com.qazstudy.ui.adapter.ValueEventListenerAdapter
import com.qazstudy.presentation.view.DialogConfirm
import com.qazstudy.presentation.view.DialogInput


@InjectViewState
class ProfilePresenter : MvpPresenter<MvpProfile>() {

    init {
        viewState.initProfile()
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.initProfile()
    }

    fun getName() : String {
        return mUser.name
    }
    fun getCity() : String {
        return mUser.city
    }
    fun getEmail() : String {
        return mUser.email
    }
    fun getCountry() : String {
        return mUser.country
    }
    fun getPassword() : String {
        return mUser.password
    }

    fun getImage() {

        mDatabase.child("users/${mAuth.currentUser!!.uid}").addListenerForSingleValueEvent( ValueEventListenerAdapter {
            if (mUser.photo.isNotEmpty()) {
                mStorage.child("users/${mAuth.currentUser!!.uid}/photo").downloadUrl.addOnSuccessListener {

                }
            }
        })

    }

    fun getDialogInput(str: String): DialogFragment {
        return DialogInput(str, mUser.password)
    }

    fun getDialogConfirm(str: String): DialogFragment {
        return DialogConfirm(str)
    }

}