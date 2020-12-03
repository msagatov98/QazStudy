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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.qazstudy.databinding.ActivityProfileBinding
import com.qazstudy.presentation.presenter.ProfilePresenter
import com.theartofdev.edmodo.cropper.CropImage
import com.qazstudy.presentation.view.ProfileView
import kotlinx.android.synthetic.main.activity_profile.*
import com.qazstudy.ui.activity.ActivityNavigation.Companion.mImageURI
import com.qazstudy.ui.activity.ActivityNavigation.Companion.mUser

class ActivityProfile : MvpAppCompatActivity(), ProfileView {

    private lateinit var binding: ActivityProfileBinding

    private val AUTH = FirebaseAuth.getInstance()
    private var STORAGE = FirebaseStorage.getInstance().reference
    private var DATABASE = FirebaseDatabase.getInstance().reference

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
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setMode()
        mProfilePresenter.init()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.e(TAG, "onActivityResult")

        if (requestCode == TAKE_PICTURE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            CropImage.activity(mImageURI).setAspectRatio(1, 1).start(this)
        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            mImageURI = CropImage.getActivityResult(data).uri

            STORAGE.child(NODE_USER).child(AUTH.currentUser!!.uid).child(NODE_PHOTO)
                .putFile(mImageURI)
                .addOnCompleteListener { uploadTask ->
                    if (uploadTask.isSuccessful) {

                        val url = STORAGE.child(NODE_USER).child(AUTH.currentUser!!.uid).child(NODE_PHOTO).child("$mImageURI")

                        DATABASE.child(NODE_USER).child(AUTH.currentUser!!.uid).child(NODE_PHOTO).setValue(url.downloadUrl.toString()).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                showToast("Photo saved")
                                if (!this.isDestroyed)
                                    STORAGE.child(NODE_USER).child(AUTH.currentUser!!.uid).child(NODE_PHOTO).downloadUrl.addOnSuccessListener { imageUri ->
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
        if (mUser.isDark) {
            this.window.statusBarColor = ContextCompat.getColor(this, R.color.light_blue)
            binding.activityProfileToolbarTxt.setTextColor(ContextCompat.getColor(this, R.color.dark))
            binding.activityProfileToolbar.background = ContextCompat.getDrawable(this, R.color.light_blue)
            binding.activityProfileIcBack.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_back_dark))
            binding.activityProfileImageProfile.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_avatar_light))
            binding.activityProfileInputName.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.activityProfileInputCity.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.activityProfileInputEmail.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.activityProfileInputCountry.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.activityProfileInputPassword.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.activityProfileInputName.setHintTextColor(ContextCompat.getColor(this, R.color.txt_color))
            binding.activityProfileInputCity.setHintTextColor(ContextCompat.getColor(this, R.color.txt_color))
            binding.activityProfileInputEmail.setHintTextColor(ContextCompat.getColor(this, R.color.txt_color))
            binding.activityProfileInputCountry.setHintTextColor(ContextCompat.getColor(this, R.color.txt_color))
            binding.activityProfileInputPassword.setHintTextColor(ContextCompat.getColor(this, R.color.txt_color))

            binding.activityProfileInputName.background.setTint(ContextCompat.getColor(this,R.color.txt_color))
            binding.activityProfileInputCity.background.setTint(ContextCompat.getColor(this, R.color.txt_color))
            binding.activityProfileInputEmail.background.setTint(ContextCompat.getColor(this, R.color.txt_color))
            binding.activityProfileInputCountry.background.setTint(ContextCompat.getColor(this, R.color.txt_color))
            binding.activityProfileInputPassword.background.setTint(ContextCompat.getColor(this, R.color.txt_color))

            binding.activityProfileBtnExit.setTextColor(ContextCompat.getColor(this, R.color.light_blue))
            binding.activityProfileBtnExit.background = ContextCompat.getDrawable(this, R.drawable.bg_btn_exit_dark)
            binding.activityProfileBtnDelete.background = ContextCompat.getDrawable(this, R.drawable.bg_btn_delete_account_dark)
            binding.activityProfileConstraintLayout.background = ContextCompat.getDrawable(this, R.drawable.activity_profile__bg_edittext_dark)
        }
    }

    override fun initProfile() {
        mProfilePresenter.setImage(activity_profile__image_profile)

        activity_profile__input_name.setText(
            mProfilePresenter.getName(),
            TextView.BufferType.EDITABLE)
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