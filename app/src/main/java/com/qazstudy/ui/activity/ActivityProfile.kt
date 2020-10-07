package com.qazstudy.ui.activity

import java.util.*
import java.io.File
import com.qazstudy.R
import android.util.Log
import android.view.View
import android.os.Bundle
import android.app.Activity
import android.content.Intent
import android.os.Environment
import android.widget.TextView
import com.bumptech.glide.Glide
import moxy.MvpAppCompatActivity
import java.text.SimpleDateFormat
import com.qazstudy.util.showToast
import android.provider.MediaStore
import com.qazstudy.presentation.view.MvpProfile
import moxy.presenter.InjectPresenter
import androidx.core.content.FileProvider
import androidx.fragment.app.DialogFragment
import com.qazstudy.presenter.ProfilePresenter
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_profile.*
import com.qazstudy.ui.activity.ActivityNavigation.Companion.mAuth
import com.qazstudy.ui.activity.ActivityNavigation.Companion.isDark
import com.qazstudy.ui.activity.ActivityNavigation.Companion.mStorage
import com.qazstudy.ui.activity.ActivityNavigation.Companion.mImageURI
import com.qazstudy.ui.activity.ActivityNavigation.Companion.mDatabase

class ActivityProfile : MvpAppCompatActivity(), MvpProfile {

    @InjectPresenter
    lateinit var mProfilePresenter: ProfilePresenter

    private lateinit var mDialog: DialogFragment

    private val TAG = "ActivityProfile"
    private val TAKE_PICTURE_REQUEST_CODE = 1
    private val timeStamp = SimpleDateFormat("yyyyMMDD_HHmmss", Locale.US).format(Date())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setMode()
        initProfile()
    }

    fun onClick(v: View) {
        when (v) {
            activity_profile__btn_exit -> showDialog("exit")
            activity_profile__btn_delete -> showDialog("delete")

            activity_profile__input_city -> showDialog("city")
            activity_profile__input_name -> showDialog("name")
            activity_profile__input_email -> showDialog("email")
            activity_profile__input_country -> showDialog("country")
            activity_profile__input_password -> showDialog("password")

            activity_profile__ic_back -> finish()
            activity_profile__image_profile -> takeCameraPicture()
        }
    }

    override fun showDialog(message: String) {
        when(message) {
            "city", "name", "email", "country", "password" ->
                mDialog = mProfilePresenter.getDialogInput(message)
            "exit", "delete" ->
                mDialog = mProfilePresenter.getDialogConfirm(message)
        }

        mDialog.show(supportFragmentManager, TAG)
    }

    override fun initProfile() {
        activity_profile__input_name.setText(mProfilePresenter.getName(), TextView.BufferType.EDITABLE)
        activity_profile__input_city.setText(mProfilePresenter.getCity(), TextView.BufferType.EDITABLE)
        activity_profile__input_email.setText(mProfilePresenter.getEmail(), TextView.BufferType.EDITABLE)
        activity_profile__input_country.setText(mProfilePresenter.getCountry(), TextView.BufferType.EDITABLE)
        activity_profile__input_password.setText(mProfilePresenter.getPassword(), TextView.BufferType.EDITABLE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.e(TAG, "onActivityResult")

        if (requestCode == TAKE_PICTURE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            CropImage.activity(mImageURI).setAspectRatio(1, 1).start(this)
        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            mImageURI = CropImage.getActivityResult(data).uri

            mStorage.child("users/${mAuth.currentUser!!.uid}/photo").putFile(mImageURI).addOnCompleteListener {
                if (it.isSuccessful) {
                    val url = mStorage.child("users/${mAuth.currentUser!!.uid}/photo/$mImageURI")
                    mDatabase.child("users/${mAuth.currentUser!!.uid}/photo").setValue(url.downloadUrl.toString()).addOnCompleteListener {it1 ->
                        if (it1.isSuccessful) {
                            showToast("Photo saved")
                            if (!this.isDestroyed)
                                mStorage.child("users/${mAuth.currentUser!!.uid}/photo").downloadUrl.addOnSuccessListener {imageUri ->
                                    if (!this.isDestroyed) {
                                        Glide.with(this).load(imageUri.toString())
                                            .into(activity_profile__image_profile)
                                    }
                                }
                        } else {
                            showToast(it1.exception!!.message.toString())
                        }
                    }
                } else {
                    showToast(it.exception!!.message.toString())
                }
            }
        }
    }

    private fun createImageFile(): File {
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(imageFileName, ".jpg", storageDir)
    }

    private fun takeCameraPicture() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        if (intent.resolveActivity(packageManager) != null) {
            val imageFile = createImageFile()
            mImageURI = FileProvider.getUriForFile(this,"com.qazstudy.fileprovider" , imageFile)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageURI)
            startActivityForResult(intent, TAKE_PICTURE_REQUEST_CODE)
        }
    }
    
    private fun setMode() {
        if (isDark) {
            this.window.statusBarColor = getColor(R.color.light_blue)
            activity_profile__toolbar_txt.setTextColor(getColor(R.color.dark))
            activity_profile__toolbar.background = getDrawable(R.color.light_blue)
            activity_profile__ic_back.setImageDrawable(getDrawable(R.drawable.ic_back_dark))

            activity_profile__input_name.setTextColor(getColor(R.color.white))
            activity_profile__input_city.setTextColor(getColor(R.color.white))
            activity_profile__input_email.setTextColor(getColor(R.color.white))
            activity_profile__input_country.setTextColor(getColor(R.color.white))
            activity_profile__input_password.setTextColor(getColor(R.color.white))

            activity_profile__input_name.setHintTextColor(getColor(R.color.txt_color))
            activity_profile__input_city.setHintTextColor(getColor(R.color.txt_color))
            activity_profile__input_email.setHintTextColor(getColor(R.color.txt_color))
            activity_profile__input_country.setHintTextColor(getColor(R.color.txt_color))
            activity_profile__input_password.setHintTextColor(getColor(R.color.txt_color))

            activity_profile__input_name.background.setTint(getColor(R.color.txt_color))
            activity_profile__input_city.background.setTint(getColor(R.color.txt_color))
            activity_profile__input_email.background.setTint(getColor(R.color.txt_color))
            activity_profile__input_country.background.setTint(getColor(R.color.txt_color))
            activity_profile__input_password.background.setTint(getColor(R.color.txt_color))

            activity_profile__btn_exit.setTextColor(getColor(R.color.light_blue))
            activity_profile__btn_exit.background = getDrawable(R.drawable.bg_btn_exit_dark)
            activity_profile__btn_delete.background = getDrawable(R.drawable.bg_btn_delete_account_dark)
            activity_profile__constraint_layout.background = getDrawable(R.drawable.activity_profile__bg_edittext_dark)
        }
    }
}



