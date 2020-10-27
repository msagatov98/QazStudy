package com.qazstudy.ui.activity

import java.util.*
import java.io.File
import com.qazstudy.R
import android.util.Log
import android.view.View
import android.os.Bundle
import com.qazstudy.util.*
import android.app.Activity
import android.content.Intent
import android.os.Environment
import android.widget.TextView
import com.bumptech.glide.Glide
import moxy.MvpAppCompatActivity
import android.provider.MediaStore
import java.text.SimpleDateFormat
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import androidx.core.content.FileProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.qazstudy.presenter.ProfilePresenter
import com.theartofdev.edmodo.cropper.CropImage
import com.qazstudy.presentation.view.MvpProfile
import kotlinx.android.synthetic.main.activity_profile.*
import com.qazstudy.ui.activity.ActivityNavigation.Companion.isDark
import com.qazstudy.ui.activity.ActivityNavigation.Companion.mImageURI

class ActivityProfile : MvpAppCompatActivity(), MvpProfile {

    @InjectPresenter
    lateinit var mProfilePresenter: ProfilePresenter

    @ProvidePresenter
    fun providePresenter(): ProfilePresenter {
        return ProfilePresenter(this)
    }

    private lateinit var mDialog: DialogFragment

    private val TAG = javaClass.simpleName
    private val TAKE_PICTURE_REQUEST_CODE = 1
    private val timeStamp = SimpleDateFormat("yyyyMMDD_HHmmss", Locale.US).format(Date())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setMode()
    }

    override fun onStart() {
        super.onStart()
        initProfile()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.e(TAG, "onActivityResult")

        if (requestCode == TAKE_PICTURE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            CropImage.activity(mImageURI).setAspectRatio(1, 1).start(this)
        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            mImageURI = CropImage.getActivityResult(data).uri

            STORAGE.child(NODE_USER).child(USER_UID).child(NODE_PHOTO)
                .putFile(mImageURI)
                .addOnCompleteListener { uploadTask ->
                    if (uploadTask.isSuccessful) {

                        val url = STORAGE.child(NODE_USER).child(USER_UID).child(NODE_PHOTO).child("$mImageURI")

                        DATABASE.child(NODE_USER).child(USER_UID).child(NODE_PHOTO).setValue(url.downloadUrl.toString()).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                showToast("Photo saved")
                                if (!this.isDestroyed)
                                    STORAGE.child(NODE_USER).child(USER_UID).child(NODE_PHOTO).downloadUrl.addOnSuccessListener { imageUri ->
                                        if (!this.isDestroyed) {
                                            Glide.with(this)
                                                .load(imageUri)
                                                .into(activity_profile__image_profile)
                                        }
                                    }
                            } else {
                                showToast(task.exception!!.message.toString())
                            }
                        }
                    } else {
                        showToast(uploadTask.exception!!.message.toString())
                    }
                }
        }
    }

    override fun setMode() {
        if (isDark) {
            this.window.statusBarColor = ContextCompat.getColor(this, R.color.light_blue)
            activity_profile__toolbar_txt.setTextColor(ContextCompat.getColor(this, R.color.dark))
            activity_profile__toolbar.background = ContextCompat.getDrawable(this, R.color.light_blue)
            activity_profile__ic_back.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_back_dark))
            activity_profile__image_profile.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_avatar_light))
            activity_profile__input_name.setTextColor(ContextCompat.getColor(this, R.color.white))
            activity_profile__input_city.setTextColor(ContextCompat.getColor(this, R.color.white))
            activity_profile__input_email.setTextColor(ContextCompat.getColor(this, R.color.white))
            activity_profile__input_country.setTextColor(ContextCompat.getColor(this, R.color.white))
            activity_profile__input_password.setTextColor(ContextCompat.getColor(this, R.color.white))
            activity_profile__input_name.setHintTextColor(ContextCompat.getColor(this, R.color.txt_color))
            activity_profile__input_city.setHintTextColor(ContextCompat.getColor(this, R.color.txt_color))
            activity_profile__input_email.setHintTextColor(ContextCompat.getColor(this, R.color.txt_color))
            activity_profile__input_country.setHintTextColor(ContextCompat.getColor(this, R.color.txt_color))
            activity_profile__input_password.setHintTextColor(ContextCompat.getColor(this, R.color.txt_color))

            activity_profile__input_name.background.setTint(ContextCompat.getColor(this,R.color.txt_color))
            activity_profile__input_city.background.setTint(ContextCompat.getColor(this, R.color.txt_color))
            activity_profile__input_email.background.setTint(ContextCompat.getColor(this, R.color.txt_color))
            activity_profile__input_country.background.setTint(ContextCompat.getColor(this, R.color.txt_color))
            activity_profile__input_password.background.setTint(ContextCompat.getColor(this, R.color.txt_color))

            activity_profile__btn_exit.setTextColor(ContextCompat.getColor(this, R.color.light_blue))
            activity_profile__btn_exit.background = ContextCompat.getDrawable(this, R.drawable.bg_btn_exit_dark)
            activity_profile__btn_delete.background = ContextCompat.getDrawable(this, R.drawable.bg_btn_delete_account_dark)
            activity_profile__constraint_layout.background = ContextCompat.getDrawable(this, R.drawable.activity_profile__bg_edittext_dark)
        }
    }

    override fun initProfile() {
        mProfilePresenter.setImage(activity_profile__image_profile)

        activity_profile__input_name.setText(
            mProfilePresenter.getName(),
            TextView.BufferType.EDITABLE
        )
        activity_profile__input_city.setText(
            mProfilePresenter.getCity(),
            TextView.BufferType.EDITABLE
        )
        activity_profile__input_email.setText(
            mProfilePresenter.getEmail(),
            TextView.BufferType.EDITABLE
        )
        activity_profile__input_country.setText(
            mProfilePresenter.getCountry(),
            TextView.BufferType.EDITABLE
        )
        activity_profile__input_password.setText(
            mProfilePresenter.getPassword(),
            TextView.BufferType.EDITABLE
        )
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

    private fun takeCameraPicture() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        if (intent.resolveActivity(packageManager) != null) {
            val imageFile = createImageFile()
            mImageURI = FileProvider.getUriForFile(this, "com.qazstudy.fileprovider", imageFile)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageURI)
            startActivityForResult(intent, TAKE_PICTURE_REQUEST_CODE)
        }
    }

    private fun createImageFile(): File {
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(imageFileName, ".jpg", storageDir)
    }

}
