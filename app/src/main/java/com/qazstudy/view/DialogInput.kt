package com.qazstudy.view

import com.qazstudy.R
import android.util.Log
import android.os.Bundle
import android.view.View
import android.app.Dialog
import android.content.Intent
import com.qazstudy.util.showToast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.qazstudy.ui.activity.ActivityProfile
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.dialog_input.view.*
import com.qazstudy.ui.activity.ActivityNavigation.Companion.mAuth
import com.qazstudy.ui.activity.ActivityNavigation.Companion.isDark
import kotlinx.android.synthetic.main.dialog_input_password_dark.view.*
import kotlinx.android.synthetic.main.dialog_input.view.dialog_password__ok
import kotlinx.android.synthetic.main.dialog_input.view.dialog_password__cancel

class DialogInput(private val hint: String, private val password: String): DialogFragment() {

    private val mDatabase = FirebaseDatabase.getInstance().getReference("users/${mAuth.currentUser!!.uid}/")

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        when (hint) {
            "name" -> return  getAlertDialog("Name")

            "city" -> return  getAlertDialog("City")

            "email" -> return  getAlertDialog("Email")

            "country" -> return  getAlertDialog("Country")

            "password" -> {
                return if (isDark) {

                    val view = layoutInflater.inflate(R.layout.dialog_input_password_dark, null)


                    view.dialog_password__ok.setOnClickListener {

                        val oldPassword = view.dialog_input_old_password.text.toString()
                        val newPassword = view.dialog_input_new_password.text.toString()
                        val newPasswordRepeat = view.dialog_input_new_password_repeat.text.toString()

                        if (oldPassword.isNotEmpty() && newPassword.isNotEmpty() && newPasswordRepeat.isNotEmpty()) {

                            if (newPassword == newPasswordRepeat && oldPassword == password) {

                                val credential = EmailAuthProvider.getCredential(mAuth.currentUser!!.email.toString(), oldPassword)

                                mAuth.currentUser!!.reauthenticate(credential).addOnCompleteListener {
                                        if (it.isSuccessful) {
                                            mAuth.currentUser!!.updatePassword(view.dialog_input_new_password.text.toString()).addOnCompleteListener {
                                                    if (it.isSuccessful) {
                                                        val updatesMap = mutableMapOf<String, Any>()
                                                        updatesMap[hint] = view.dialog_input_new_password.text.toString()
                                                        mDatabase.updateChildren(updatesMap).addOnCompleteListener {
                                                                if (it.isSuccessful) {
                                                                    startActivity(Intent(requireContext(), ActivityProfile::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION))
                                                                    requireActivity().finish()
                                                                } else {
                                                                    requireContext().showToast(it.exception.toString())
                                                                }
                                                            }
                                                    } else {
                                                        requireContext().showToast(it.exception.toString())
                                                    }
                                                }
                                        } else {
                                            requireContext().showToast(it.exception.toString())
                                        }
                                    }
                            } else {
                                requireContext().showToast("Passwords are not same")
                            }
                        } else {
                            requireContext().showToast("Enter passwords")
                        }
                    }

                    view.dialog_password__cancel.setOnClickListener { this.dismiss() }

                    AlertDialog.Builder(requireContext()).setView(view).create()
                } else {

                    val view = layoutInflater.inflate(R.layout.dialog_input_password_light, null)

                    view.dialog_password__ok.setOnClickListener {

                        val oldPassword = view.dialog_input_old_password.text.toString()
                        val newPassword = view.dialog_input_new_password.text.toString()
                        val newPasswordRepeat = view.dialog_input_new_password_repeat.text.toString()

                        if (oldPassword.isNotEmpty() && newPassword.isNotEmpty() && newPasswordRepeat.isNotEmpty()) {

                            if (newPassword == newPasswordRepeat && password == oldPassword) {

                                val credential = EmailAuthProvider.getCredential(mAuth.currentUser!!.email.toString(), oldPassword)

                                mAuth.currentUser!!.reauthenticate(credential).addOnCompleteListener {
                                    if (it.isSuccessful) {
                                        mAuth.currentUser!!.updatePassword(view.dialog_input_new_password.text.toString()).addOnCompleteListener {
                                            if (it.isSuccessful) {
                                                val updatesMap = mutableMapOf<String, Any>()
                                                updatesMap[hint] = view.dialog_input_new_password.text.toString()
                                                mDatabase.updateChildren(updatesMap).addOnCompleteListener {
                                                    if (it.isSuccessful) {
                                                        startActivity(Intent(requireContext(), ActivityProfile::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION))
                                                        requireActivity().finish()
                                                    } else {
                                                        requireContext().showToast(it.exception.toString())
                                                    }
                                                }
                                            } else {
                                                requireContext().showToast(it.exception.toString())
                                            }
                                        }
                                    } else {
                                        requireContext().showToast(it.exception.toString())
                                    }
                                }
                            } else {
                                requireContext().showToast("Passwords are not same")
                            }
                        } else {
                            requireContext().showToast("Enter passwords")
                        }
                    }

                    view.dialog_password__cancel.setOnClickListener { this.dismiss() }

                    AlertDialog.Builder(requireContext()).setView(view).create()
                }
            }
        }
        return  AlertDialog.Builder(requireContext()).setView(R.layout.dialog_input).create()
    }

    private fun getAlertDialog(str: String) : AlertDialog {
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

                val intent = Intent(requireContext(), ActivityProfile::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)

                if (hint == "email") {
                    val credential = EmailAuthProvider.getCredential(mAuth.currentUser!!.email.toString(), password)

                    mAuth.currentUser!!.reauthenticate(credential).addOnCompleteListener {
                        if (it.isSuccessful) {
                            mAuth.currentUser!!.updateEmail(view.dialog_input.text.toString()).addOnCompleteListener {
                                if (it.isSuccessful) {
                                    mDatabase.updateChildren(updatesMap).addOnCompleteListener {
                                        if (it.isSuccessful) {
                                            startActivity(intent)
                                            requireActivity().finish()
                                        } else {
                                            requireContext().showToast(it.exception.toString())
                                        }
                                    }
                                } else {
                                    requireContext().showToast(it.exception.toString())
                                }
                            }
                        } else {
                            requireContext().showToast(it.exception.toString())
                        }
                    }
                } else {
                    mDatabase.updateChildren(updatesMap).addOnCompleteListener {
                        if (it.isSuccessful) {
                            startActivity(intent)
                            requireActivity().finish()
                        } else {
                            requireContext().showToast(it.exception.toString())
                        }
                    }
                }
            } else {
                requireContext().showToast("Enter $hint")
            }
        }

        return AlertDialog.Builder(requireContext()).setView(view).create()
    }

    private fun getDialogView() : View {
        return if (isDark) {
            layoutInflater.inflate(R.layout.dialog_input_dark, null)
        } else {
            layoutInflater.inflate(R.layout.dialog_input, null)
        }
    }
}