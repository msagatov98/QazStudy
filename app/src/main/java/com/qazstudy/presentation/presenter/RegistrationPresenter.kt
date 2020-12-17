package com.qazstudy.presentation.presenter

import android.app.Activity
import android.net.Uri
import com.qazstudy.model.Firebase
import com.qazstudy.presentation.view.RegistrationView
import com.theartofdev.edmodo.cropper.CropImage
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class RegistrationPresenter(val activity: Activity) : MvpPresenter<RegistrationView>() {

    private val firebase = Firebase()

    fun updateUser(imageUri: Uri ) {
        firebase.updateUser(imageUri)
    }

    fun cropImage(imageUri: Uri) {
        CropImage.activity(imageUri).setAspectRatio(1, 1).start(activity)
    }

}