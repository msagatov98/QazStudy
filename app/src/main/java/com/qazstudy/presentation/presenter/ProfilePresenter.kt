package com.qazstudy.presentation.presenter

import android.app.Activity
import android.net.Uri
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.qazstudy.R
import com.qazstudy.model.Firebase
import com.qazstudy.presentation.view.ProfileView
import com.qazstudy.ui.activity.LoginActivity.Companion.mUser
import com.qazstudy.ui.dialog.DialogConfirm
import com.qazstudy.ui.dialog.DialogInput
import com.qazstudy.util.*
import com.theartofdev.edmodo.cropper.CropImage
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class ProfilePresenter(var activity: Activity) : MvpPresenter<ProfileView>() {

    private val firebase = Firebase()

    fun init() {
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

    fun updateUser(imageUri: Uri ) {
        firebase.updateUser(imageUri)
    }

    fun cropImage(imageUri: Uri) {
        CropImage.activity(imageUri).setAspectRatio(1, 1).start(activity)
    }

    fun setImage(view: ImageView) {
        firebase.mStorage.child(NODE_USER).child(firebase.mAuth.currentUser!!.uid).child(NODE_PHOTO).downloadUrl.addOnSuccessListener {
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