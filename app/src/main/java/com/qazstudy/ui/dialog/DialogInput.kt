package com.qazstudy.ui.dialog

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.qazstudy.R
import com.qazstudy.model.Firebase
import com.qazstudy.ui.activity.ActivityNavigation.Companion.isDark
import com.qazstudy.ui.activity.LoginActivity.Companion.mUser
import com.qazstudy.util.*
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.dialog_input.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class DialogInput(private val hint: String, private val password: String) : DialogFragment() {

    val firebase = Firebase()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        when (hint) {
            "name" -> return getAlertDialog("Name")

            "city" -> return getAlertDialog("City")

            "email" -> return getAlertDialog("Email")

            "country" -> return getAlertDialog("Country")

//            "password" -> {
//                return if (mUser.isDark) {
//
//                    val view = layoutInflater.inflate(R.layout.dialog_input_password_dark, null)
//
//                    view.dialog_password__ok.setOnClickListener {
//
//                        val oldPassword = view.dialog_input_old_password.text.toString()
//                        val newPassword = view.dialog_input_new_password.text.toString()
//                        val newPasswordRepeat =
//                            view.dialog_input_new_password_repeat.text.toString()
//
//                        if (oldPassword.isNotEmpty() && newPassword.isNotEmpty() && newPasswordRepeat.isNotEmpty()) {
//
//                            if (newPassword == newPasswordRepeat && oldPassword == password) {
//
//                                val credential = EmailAuthProvider.getCredential(
//                                    AUTH.currentUser!!.email.toString(),
//                                    oldPassword
//                                )
//
//                                AUTH.currentUser!!.reauthenticate(credential)
//                                    .addOnCompleteListener {
//                                        if (it.isSuccessful) {
//                                            AUTH.currentUser!!.updatePassword(view.dialog_input_new_password.text.toString())
//                                                .addOnCompleteListener {
//                                                    if (it.isSuccessful) {
//                                                        val updatesMap = mutableMapOf<String, Any>()
//                                                        updatesMap[hint] =
//                                                            view.dialog_input_new_password.text.toString()
//                                                        DATABASE.updateChildren(updatesMap)
//                                                            .addOnCompleteListener {
//                                                                if (it.isSuccessful) {
//                                                                    startActivity(
//                                                                        Intent(
//                                                                            requireContext(),
//                                                                            ActivityProfile::class.java
//                                                                        ).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
//                                                                    )
//                                                                    requireActivity().finish()
//                                                                } else {
//                                                                    requireContext().showToast(it.exception.toString())
//                                                                }
//                                                            }
//                                                    } else {
//                                                        requireContext().showToast(it.exception.toString())
//                                                    }
//                                                }
//                                        } else {
//                                            requireContext().showToast(it.exception.toString())
//                                        }
//                                    }
//                            } else {
//                                requireContext().showToast("Passwords are not same")
//                            }
//                        } else {
//                            requireContext().showToast("Enter passwords")
//                        }
//                    }
//
//                    view.dialog_password__cancel.setOnClickListener { this.dismiss() }
//
//                    AlertDialog.Builder(requireContext()).setView(view).create()
//                } else {
//
//                    val view = layoutInflater.inflate(R.layout.dialog_input_password_light, null)
//
//                    view.dialog_password__ok.setOnClickListener {
//
//                        val oldPassword = view.dialog_input_old_password.text.toString()
//                        val newPassword = view.dialog_input_new_password.text.toString()
//                        val newPasswordRepeat =
//                            view.dialog_input_new_password_repeat.text.toString()
//
//                        if (oldPassword.isNotEmpty() && newPassword.isNotEmpty() && newPasswordRepeat.isNotEmpty()) {
//
//                            if (newPassword == newPasswordRepeat && password == oldPassword) {
//
//                                val credential = EmailAuthProvider.getCredential(
//                                    AUTH.currentUser!!.email.toString(),
//                                    oldPassword
//                                )
//
//                                AUTH.currentUser!!.reauthenticate(credential)
//                                    .addOnCompleteListener {
//                                        if (it.isSuccessful) {
//                                            AUTH.currentUser!!.updatePassword(view.dialog_input_new_password.text.toString())
//                                                .addOnCompleteListener {
//                                                    if (it.isSuccessful) {
//                                                        val updatesMap = mutableMapOf<String, Any>()
//                                                        updatesMap[hint] =
//                                                            view.dialog_input_new_password.text.toString()
//                                                        DATABASE.updateChildren(updatesMap)
//                                                            .addOnCompleteListener {
//                                                                if (it.isSuccessful) {
//                                                                    startActivity(
//                                                                        Intent(
//                                                                            requireContext(),
//                                                                            ActivityProfile::class.java
//                                                                        ).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
//                                                                    )
//                                                                    requireActivity().finish()
//                                                                } else {
//                                                                    requireContext().showToast(it.exception.toString())
//                                                                }
//                                                            }
//                                                    } else {
//                                                        requireContext().showToast(it.exception.toString())
//                                                    }
//                                                }
//                                        } else {
//                                            requireContext().showToast(it.exception.toString())
//                                        }
//                                    }
//                            } else {
//                                requireContext().showToast("Passwords are not same")
//                            }
//                        } else {
//                            requireContext().showToast("Enter passwords")
//                        }
//                    }
//
//                    view.dialog_password__cancel.setOnClickListener { this.dismiss() }
//
//                    AlertDialog.Builder(requireContext()).setView(view).create()
//                }
//            }

            else -> return AlertDialog.Builder(requireContext()).setView(R.layout.dialog_input)
                .create()
        }
    }

    private fun getAlertDialog(str: String): AlertDialog {


        Log.e("Dialog", "alertDialog")

        val view = getDialogView()

        view.dialog_title.text = "Enter new $str"
        view.dialog_input.hint = str

        view.dialog_password__cancel.setOnClickListener {
            this.dismiss()
        }

        view.dialog_password__ok.setOnClickListener {
            if (view.dialog_input.text.toString().isNotEmpty()) {

                val updatesMap = mutableMapOf<String, Any>()
                updatesMap[hint] = view.dialog_input.text.toString()

                val newValue = view.dialog_input.text.toString()

                when (hint) {
                    "email" -> activity?.activity_profile__input_email?.setText(newValue)
                    "name" -> activity?.activity_profile__input_name?.setText(newValue)
                    "city" -> activity?.activity_profile__input_city?.setText(newValue)
                    "country" -> activity?.activity_profile__input_country?.setText(newValue)
                }

                if (hint == "email") {

                    CoroutineScope(IO).launch {

                        val email = view.dialog_input.text.toString()
                        val password = mUser.password

                        firebase.updateUser(email, password)
                    }
                } else {
                    CoroutineScope(IO).launch {
                        firebase.updateUser(updatesMap)
                    }
                }

                this.dismiss()
            } else {
                requireContext().showToast("Enter $hint")
            }
        }

        return AlertDialog.Builder(requireContext()).setView(view).create()
    }

    private fun getDialogView(): View {
        return if (isDark) {
            layoutInflater.inflate(R.layout.dialog_input_dark, null)
        } else {
            layoutInflater.inflate(R.layout.dialog_input, null)
        }
    }
}