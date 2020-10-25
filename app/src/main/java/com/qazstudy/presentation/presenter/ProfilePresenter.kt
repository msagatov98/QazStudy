package com.qazstudy.presenter


import android.app.Activity
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.qazstudy.R
import moxy.MvpPresenter
import moxy.InjectViewState
import com.qazstudy.ui.activity.ActivityNavigation.Companion.mAuth
import com.qazstudy.ui.activity.ActivityNavigation.Companion.mStorage
import com.qazstudy.ui.activity.ActivityNavigation.Companion.mUser
import com.qazstudy.presentation.view.MvpProfile
import com.qazstudy.presentation.view.DialogConfirm
import com.qazstudy.presentation.view.DialogInput


@InjectViewState
class ProfilePresenter(var activity: Activity) : MvpPresenter<MvpProfile>() {


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

    fun setImage(view: ImageView) {
         mStorage.child("users/${mAuth.currentUser!!.uid}/photo").downloadUrl.addOnSuccessListener {
             Glide.with(activity)
                 .load(it)
                 .error(ContextCompat.getDrawable(activity, R.drawable.ic_avatar_dark))
                 .into(view)
         }
    }

    fun getDialogInput(str: String): DialogFragment {
        return DialogInput(str, mUser.password)
    }

    fun getDialogConfirm(str: String): DialogFragment {
        return DialogConfirm(str)
    }

}