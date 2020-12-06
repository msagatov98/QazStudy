package com.qazstudy.ui.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.qazstudy.R
import com.qazstudy.databinding.ActivityProfileBinding
import com.qazstudy.presentation.presenter.ProfilePresenter
import com.qazstudy.presentation.view.ProfileView
import com.qazstudy.ui.activity.ActivityNavigation.Companion.mImageURI
import com.qazstudy.ui.activity.ActivityNavigation.Companion.mUser
import com.qazstudy.util.showToast
import com.theartofdev.edmodo.cropper.CropImage
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class ActivityProfile : MvpAppCompatActivity(), ProfileView {

    private lateinit var binding: ActivityProfileBinding

    @InjectPresenter
    lateinit var mProfilePresenter: ProfilePresenter

    @ProvidePresenter
    fun providePresenter(): ProfilePresenter {
        return ProfilePresenter(this)
    }

    private lateinit var mDialog: DialogFragment

    private val TAG = javaClass.simpleName
    private val TAKE_PICTURE_REQUEST_CODE = 1
    private val TAKE_PERMISSION_REQUEST_CODE = 2
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
            mProfilePresenter.cropImage(mImageURI)
        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            mImageURI = CropImage.getActivityResult(data).uri

            Glide.with(this).load(mImageURI).into(binding.activityProfileImageProfile)
            mProfilePresenter.updateUser(mImageURI)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == TAKE_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takeCameraPicture()
            } else {
                showToast("Give permission to take picture")
            }
        }

    }

    override fun setMode() {
        if (mUser.isDark) {
            this.window.statusBarColor = ContextCompat.getColor(this, R.color.light_blue)
            binding.activityProfileToolbarTxt.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.dark
                )
            )
            binding.activityProfileToolbar.background =
                ContextCompat.getDrawable(this, R.color.light_blue)
            binding.activityProfileIcBack.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_back_dark
                )
            )
            binding.activityProfileImageProfile.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_avatar_light
                )
            )
            binding.activityProfileInputName.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.white
                )
            )
            binding.activityProfileInputCity.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.white
                )
            )
            binding.activityProfileInputEmail.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.white
                )
            )
            binding.activityProfileInputCountry.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.white
                )
            )
            binding.activityProfileInputPassword.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.white
                )
            )
            binding.activityProfileInputName.setHintTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.txt_color
                )
            )
            binding.activityProfileInputCity.setHintTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.txt_color
                )
            )
            binding.activityProfileInputEmail.setHintTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.txt_color
                )
            )
            binding.activityProfileInputCountry.setHintTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.txt_color
                )
            )
            binding.activityProfileInputPassword.setHintTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.txt_color
                )
            )

            binding.activityProfileInputName.background.setTint(
                ContextCompat.getColor(
                    this,
                    R.color.txt_color
                )
            )
            binding.activityProfileInputCity.background.setTint(
                ContextCompat.getColor(
                    this,
                    R.color.txt_color
                )
            )
            binding.activityProfileInputEmail.background.setTint(
                ContextCompat.getColor(
                    this,
                    R.color.txt_color
                )
            )
            binding.activityProfileInputCountry.background.setTint(
                ContextCompat.getColor(
                    this,
                    R.color.txt_color
                )
            )
            binding.activityProfileInputPassword.background.setTint(
                ContextCompat.getColor(
                    this,
                    R.color.txt_color
                )
            )

            binding.activityProfileBtnExit.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.light_blue
                )
            )
            binding.activityProfileBtnExit.background =
                ContextCompat.getDrawable(this, R.drawable.bg_btn_exit_dark)
            binding.activityProfileBtnDelete.background =
                ContextCompat.getDrawable(this, R.drawable.bg_btn_delete_account_dark)
            binding.activityProfileConstraintLayout.background =
                ContextCompat.getDrawable(this, R.drawable.activity_profile__bg_edittext_dark)
        }
    }

    override fun initProfile() {
        mProfilePresenter.setImage(binding.activityProfileImageProfile)

        binding.activityProfileInputName.setText(
            mProfilePresenter.getName(),
            TextView.BufferType.EDITABLE
        )
        binding.activityProfileInputCity.setText(
            mProfilePresenter.getCity(),
            TextView.BufferType.EDITABLE
        )
        binding.activityProfileInputEmail.setText(
            mProfilePresenter.getEmail(),
            TextView.BufferType.EDITABLE
        )
        binding.activityProfileInputCountry.setText(
            mProfilePresenter.getCountry(),
            TextView.BufferType.EDITABLE
        )
        binding.activityProfileInputPassword.setText(
            mProfilePresenter.getPassword(),
            TextView.BufferType.EDITABLE
        )
    }

    override fun showDialog(message: String) {
        when (message) {
            "city", "name", "email", "country", "password" ->
                mDialog = mProfilePresenter.getDialogInput(message)
            "exit", "delete" ->
                mDialog = mProfilePresenter.getDialogConfirm(message)
        }
        mDialog.show(supportFragmentManager, TAG)
    }

    fun onClick(v: View) {
        when (v) {
            binding.activityProfileBtnExit -> showDialog("exit")
            binding.activityProfileBtnDelete -> showDialog("delete")

            binding.activityProfileInputCity -> showDialog("city")
            binding.activityProfileInputName -> showDialog("name")
            binding.activityProfileInputEmail -> showDialog("email")
            binding.activityProfileInputCountry -> showDialog("country")
            binding.activityProfileInputPassword -> showDialog("password")

            binding.activityProfileIcBack -> finish()
            binding.activityProfileImageProfile -> takeCameraPicture()
        }
    }

    private fun takeCameraPicture() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED) {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            if (intent.resolveActivity(packageManager) != null) {
                val imageFile = createImageFile()
                mImageURI = FileProvider.getUriForFile(this, "com.qazstudy.fileprovider", imageFile)
                intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageURI)
                startActivityForResult(intent, TAKE_PICTURE_REQUEST_CODE)
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), TAKE_PERMISSION_REQUEST_CODE)
            }
        }
    }

    private fun createImageFile(): File {
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(imageFileName, ".jpg", storageDir)
    }
}