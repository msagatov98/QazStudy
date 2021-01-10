package com.qazstudy.ui.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.qazstudy.databinding.ActivityRegistrationBinding
import com.qazstudy.presentation.presenter.RegistrationPresenter
import com.qazstudy.presentation.view.RegistrationView
import com.qazstudy.ui.activity.ActivityNavigation.Companion.mImageURI
import com.qazstudy.util.viewBinding
import com.theartofdev.edmodo.cropper.CropImage
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class RegistrationActivity : MvpAppCompatActivity(), RegistrationView {

    private val binding by viewBinding(ActivityRegistrationBinding::inflate)

    @InjectPresenter
    lateinit var mRegistrationPresenter: RegistrationPresenter

    @ProvidePresenter
    fun providePresenter(): RegistrationPresenter {
        return RegistrationPresenter(this)
    }

    private val TAG = javaClass.simpleName
    private val TAKE_PICTURE_REQUEST_CODE = 1
    private val TAKE_PERMISSION_REQUEST_CODE = 2
    private val timeStamp = SimpleDateFormat("yyyyMMDD_HHmmss", Locale.US).format(Date())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.icUser.setOnClickListener {
            takeCameraPicture()
        }

        binding.btnRegister.setOnClickListener {

            val name = binding.inputName.text.toString()
            val email = binding.inputEmail.text.toString()
            val password = binding.inputPassword.text.toString()

            mRegistrationPresenter.register(email, name, password)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.e(TAG, "onActivityResult")

        if (requestCode == TAKE_PICTURE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            mRegistrationPresenter.cropImage(mImageURI)
        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            mImageURI = CropImage.getActivityResult(data).uri

            Glide.with(this).load(mImageURI).into(binding.icUser)
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