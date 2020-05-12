package com.qazstudy.ui.activity

import android.app.Activity
import com.qazstudy.R
import android.os.Bundle
import android.content.Intent
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import com.qazstudy.model.User
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.qazstudy.view.DialogInput
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_profile.*
import com.qazstudy.ui.adapter.ValueEventListenerAdapter
import com.qazstudy.ui.activity.ActivityNavigation.Companion.mAuth
import com.qazstudy.ui.activity.ActivityNavigation.Companion.isDark
import com.qazstudy.ui.activity.ActivityNavigation.Companion.mDatabase
import com.qazstudy.ui.activity.ActivityNavigation.Companion.mImageURI
import com.qazstudy.ui.activity.ActivityNavigation.Companion.mStorage
import com.qazstudy.ui.activity.ActivityNavigation.Companion.mUser
import com.qazstudy.util.showToast
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.dialog_confirm_dark.view.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class ActivityProfile : AppCompatActivity() {

    private val TAG = "ActivityProfile"

    private val TAKE_PICTURE_REQUEST_CODE = 1
    private val timeStamp = SimpleDateFormat("yyyyMMDD_HHmmss", Locale.US).format(Date())

    init {

        mDatabase.child("users/${mAuth.currentUser!!.uid}").addListenerForSingleValueEvent( ValueEventListenerAdapter{
            mUser = it.getValue(User::class.java)!!
            activity_profile__input_name.setText(mUser.name, TextView.BufferType.EDITABLE)
            activity_profile__input_city.setText(mUser.city, TextView.BufferType.EDITABLE)
            activity_profile__input_email.setText(mUser.email, TextView.BufferType.EDITABLE)
            activity_profile__input_country.setText(mUser.country, TextView.BufferType.EDITABLE)
            activity_profile__input_password.setText(mUser.password, TextView.BufferType.EDITABLE)

            if (mUser.photo.isNotEmpty()) {
                mStorage.child("users/${mAuth.currentUser!!.uid}/photo").downloadUrl.addOnSuccessListener {
                    Glide.with(this).load(it.toString()).into(activity_profile__image_profile)
                }
            }
        })

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setMode()

        mAuth.addAuthStateListener {
            if (it.currentUser == null) {
                startActivity(Intent(this, ActivityLogin::class.java))
                finish()
            }
        }

        activity_profile__input_city.setOnClickListener {
            DialogInput("city", mUser.password).show(supportFragmentManager, "TAG")
        }
        activity_profile__input_name.setOnClickListener {
            DialogInput("name", mUser.password).show(supportFragmentManager, "TAG")
        }
        activity_profile__input_email.setOnClickListener {
            DialogInput("email", mUser.password).show(supportFragmentManager, "TAG")
        }
        activity_profile__input_country.setOnClickListener {
            DialogInput("country", mUser.password).show(supportFragmentManager, "TAG")
        }
        activity_profile__input_password.setOnClickListener {
            DialogInput("password", mUser.password).show(supportFragmentManager, "TAG")
        }

        activity_profile__ic_back.setOnClickListener { finish() }
        activity_profile__image_profile.setOnClickListener { takeCameraPicture() }

        activity_profile__btn_exit.setOnClickListener { getAlertDialog("exit").show() }
        activity_profile__btn_delete.setOnClickListener { getAlertDialog("delete").show() }

    }

    private fun getAlertDialog(str: String) : AlertDialog {

        val view = getDialogView()

        val alertDialog: AlertDialog = AlertDialog.Builder(this).setView(view).create()

        if (str == "exit") {
            view.dialog_title.text = "Are you sure to exit from account"

            view.dialog_confirm__ok.setOnClickListener { mAuth.signOut() }

        } else if (str == "delete") {
            view.dialog_title.text = "Are you sure to delte account"

            view.dialog_confirm__ok.setOnClickListener {
                mDatabase.child("users/${mAuth.currentUser!!.uid}").removeValue()
                mAuth.currentUser!!.delete().addOnCompleteListener {
                    if (it.isSuccessful) {
                        startActivity(Intent(this, ActivityLogin::class.java))
                        finish()
                    } else {
                        showToast(it.exception.toString())
                    }
                }
            }

        }

        view.dialog_confirm__cancel.setOnClickListener {
            alertDialog.dismiss()
        }

        return alertDialog
    }

    private fun getDialogView() : View {
        return if (isDark) {
            layoutInflater.inflate(R.layout.dialog_confirm_dark, null)
        } else {
            layoutInflater.inflate(R.layout.dialog_confirm_light, null)
        }
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
                    mDatabase.child("users/${mAuth.currentUser!!.uid}/photo").setValue(url.downloadUrl.toString()).addOnCompleteListener {
                        if (it.isSuccessful) {
                            showToast("Photo saved")
                            if (!this.isDestroyed)
                            mStorage.child("users/${mAuth.currentUser!!.uid}/photo").downloadUrl.addOnSuccessListener {
                                if (!this.isDestroyed) {
                                    Glide.with(this).load(it.toString())
                                        .into(activity_profile__image_profile)
                                }
                            }
                        } else {
                            showToast(it.exception!!.message.toString())
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