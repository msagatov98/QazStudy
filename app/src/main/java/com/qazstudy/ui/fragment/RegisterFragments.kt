package com.qazstudy.ui.fragment

import android.app.Activity
import com.qazstudy.R
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.qazstudy.ui.activity.ActivityNavigation
import com.qazstudy.util.coordinateButtonAndInput
import com.qazstudy.util.showToast
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.fragment_register_email.*
import kotlinx.android.synthetic.main.fragment_register_namepass.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class FragmentEmail: Fragment() {

    private lateinit var mListener: Listener
    private var imageURI: Uri? = null
    private val TAKE_PICTURE_REQUEST_CODE = 1
    private val timeStamp = SimpleDateFormat("yyyyMMDD_HHmmss", Locale.US).format(Date())

    interface Listener {
        fun onNext(email: String, imageUri: Uri?)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register_email, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        coordinateButtonAndInput(fragment_register__btn_next, fragment_register__input_email)

        fragment_register__ic_user.setOnClickListener { takeCameraPicture() }

        fragment_register__btn_next.setOnClickListener {
            val email = fragment_register__input_email.text.toString()
            mListener.onNext(email, imageURI)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = context as Listener
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == TAKE_PICTURE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            CropImage.activity(imageURI).setAspectRatio(1, 1).start(requireActivity())
        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            imageURI = CropImage.getActivityResult(data).uri
            Glide.with(this).load(imageURI.toString()).into(fragment_register__ic_user)
        }
    }

    private fun takeCameraPicture() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            val imageFile = createImageFile()
            imageURI = FileProvider.getUriForFile(requireContext(),"com.qazstudy.fileprovider" , imageFile)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageURI)
            startActivityForResult(intent, TAKE_PICTURE_REQUEST_CODE)
        }
    }

    private fun createImageFile(): File {
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(imageFileName, ".jpg", storageDir)
    }
}

class FragmentNamePass: Fragment() {

    private lateinit var mListener: Listener

    interface Listener {
        fun onRegister(name: String, password: String, repeatPassword: String)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register_namepass, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        coordinateButtonAndInput(activity_register__btn_register, fragment_register__input_name,
            fragment_register__input_password, fragment_register__input_password_repeat)

        activity_register__btn_register.setOnClickListener {
            val name = fragment_register__input_name.text.toString()
            val password = fragment_register__input_password.text.toString()
            val repeatPassword = fragment_register__input_password_repeat.text.toString()

            mListener.onRegister(name, password, repeatPassword)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = context as Listener
    }
}