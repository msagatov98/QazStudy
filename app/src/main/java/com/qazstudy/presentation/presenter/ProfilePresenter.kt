package com.qazstudy.presentation.presenter

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.widget.ImageView
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.qazstudy.R
import com.qazstudy.model.Firebase
import moxy.MvpPresenter
import moxy.InjectViewState
import com.qazstudy.ui.activity.ActivityNavigation.Companion.mUser
import com.qazstudy.presentation.view.ProfileView
import com.qazstudy.ui.activity.ActivityNavigation
import com.qazstudy.ui.dialog.DialogConfirm
import com.qazstudy.ui.dialog.DialogInput
import com.qazstudy.util.*
import com.theartofdev.edmodo.cropper.CropImage
import java.io.File

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