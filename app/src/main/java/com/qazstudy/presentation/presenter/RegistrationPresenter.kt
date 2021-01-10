package com.qazstudy.presentation.presenter

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import com.qazstudy.model.Firebase
import com.qazstudy.model.User
import com.qazstudy.presentation.view.RegistrationView
import com.qazstudy.ui.activity.ActivityNavigation
import com.qazstudy.ui.activity.LoginActivity
import com.qazstudy.ui.activity.LoginActivity.Companion.mUser
import com.qazstudy.util.NODE_PHOTO
import com.qazstudy.util.NODE_USER
import com.qazstudy.util.showToast
import com.theartofdev.edmodo.cropper.CropImage
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class RegistrationPresenter(val activity: Activity) : MvpPresenter<RegistrationView>() {

    private val firebase = Firebase()

    fun updateUser(imageUri: Uri ) {
        firebase.updateUser(imageUri)
    }

    fun register(email: String, name: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty() && name.isNotEmpty()) {

            firebase.mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    mUser = User(
                        name = name,
                        email = email,
                        password = password,
                        photo = ActivityNavigation.mImageURI.toString()
                    )

                    firebase.mDatabase.child(NODE_USER)
                        .child(firebase.mAuth.currentUser!!.uid)
                        .setValue(mUser)
                        .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            firebase.mStorage.child(NODE_USER).child(firebase.mAuth.currentUser!!.uid)
                                .child(NODE_PHOTO)
                                .putFile(ActivityNavigation.mImageURI)
                                .addOnCompleteListener { snapshot ->
                                    if (snapshot.isSuccessful) {
                                        activity.startActivity(Intent(activity, ActivityNavigation::class.java))
                                        activity.finish()
                                    }
                            }
                        }
                    }
                }
            }
        } else {
            activity.showToast("Введите адрес электронной почты и пароль")
        }
    }

    fun cropImage(imageUri: Uri) {
        CropImage.activity(imageUri).setAspectRatio(1, 1).start(activity)
    }

}