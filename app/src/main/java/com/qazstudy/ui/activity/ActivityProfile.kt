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
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.qazstudy.databinding.ActivityProfileBinding
import com.qazstudy.model.Firebase
import com.qazstudy.ui.activity.ActivityNavigation.Companion.mImageURI
import com.qazstudy.util.showToast
import com.qazstudy.util.viewBinding
import com.theartofdev.edmodo.cropper.CropImage
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.android.synthetic.main.activity_profile.activity_profile__btn_exit

class ActivityProfile : AppCompatActivity() {

    private val firebase = Firebase()

    private val binding by viewBinding(ActivityProfileBinding::inflate)

    private lateinit var mDialog: DialogFragment

    private val TAG = javaClass.simpleName
    private val TAKE_PICTURE_REQUEST_CODE = 1
    private val TAKE_PERMISSION_REQUEST_CODE = 2
    private val timeStamp = SimpleDateFormat("yyyyMMDD_HHmmss", Locale.US).format(Date())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initProfile()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.e(TAG, "onActivityResult")

        if (requestCode == TAKE_PICTURE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            mImageURI = CropImage.getActivityResult(data).uri

            Glide.with(this).load(mImageURI).circleCrop().into(binding.activityProfileImageProfile)

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == TAKE_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takeCameraPicture()
            } else {
                showToast("Give permission to take picture")
            }
        }
    }


    fun initProfile() {

        binding.run {

            activityProfileInputName.setText("")
            activityProfileInputCity.setText("")
            activityProfileInputEmail.setText("")
            activityProfileInputCountry.setText("")
            activityProfileInputPassword.setText("")
        }
    }

    fun showDialog(message: String) {
        when (message) {
//            "city", "name", "email", "country", "password" ->

//            "exit", "delete"

        }
        mDialog.show(supportFragmentManager, TAG)
    }

    fun onClick(v: View) {
        when (v) {
            binding.activityProfileBtnExit -> {
                firebase.signOut()
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
            binding.activityProfileIcBack -> finish()
            binding.activityProfileImageProfile -> takeCameraPicture()
        }
    }

    private fun takeCameraPicture() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            if (intent.resolveActivity(packageManager) != null) {
                val imageFile = createImageFile()
                mImageURI = FileProvider.getUriForFile(this, "com.qazstudy.fileprovider", imageFile)
                intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageURI)
                startActivityForResult(intent, TAKE_PICTURE_REQUEST_CODE)
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    TAKE_PERMISSION_REQUEST_CODE
                )
            }
        }
    }

    private fun createImageFile(): File {
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(imageFileName, ".jpg", storageDir)
    }
}