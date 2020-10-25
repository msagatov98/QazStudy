package com.qazstudy.presentation.view

import com.qazstudy.R
import android.os.Bundle
import android.view.View
import android.app.Dialog
import android.content.Intent
import com.qazstudy.util.showToast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.qazstudy.ui.activity.ActivityLogin
import com.qazstudy.ui.activity.ActivityNavigation
import com.qazstudy.ui.activity.ActivityNavigation.Companion.mAuth
import com.qazstudy.ui.activity.ActivityNavigation.Companion.isDark
import com.qazstudy.ui.activity.ActivityNavigation.Companion.mStorage
import com.qazstudy.ui.activity.ActivityNavigation.Companion.mDatabase
import kotlinx.android.synthetic.main.dialog_confirm_dark.view.*

class DialogConfirm(private val hint: String): DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        when (hint) {
            "exit" -> return  getAlertDialog("exit")
            "delete" -> return  getAlertDialog("delete")
        }

        return  AlertDialog.Builder(requireContext()).setView(R.layout.dialog_input).create()
    }

    private fun getAlertDialog(str: String) : AlertDialog {

        val view = getDialogView()

        val alertDialog: AlertDialog = AlertDialog.Builder(requireActivity()).setView(view).create()

        if (str == "exit") {
            view.dialog_title.text = resources.getString(R.string.confirm_exit)

            view.dialog_confirm__ok.setOnClickListener {
                mAuth.signOut()
                requireActivity().startActivity(Intent(requireActivity(), ActivityNavigation::class.java))
                requireActivity().finish()
            }
        } else if (str == "delete") {
            view.dialog_title.text = resources.getString(R.string.confirm_delete)

            view.dialog_confirm__ok.setOnClickListener {
                mStorage.child("users/${mAuth.currentUser!!.uid}/photo").delete()
                mDatabase.child("users/${mAuth.currentUser!!.uid}").removeValue()
                mAuth.currentUser!!.delete().addOnCompleteListener {
                    if (it.isSuccessful) {
                        startActivity(Intent(requireActivity(), ActivityNavigation::class.java))
                        requireActivity().finish()
                    } else {
                        requireActivity().showToast(it.exception.toString())
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
        return if (isDark)
            layoutInflater.inflate(R.layout.dialog_confirm_dark, null, false)
        else
            layoutInflater.inflate(R.layout.dialog_confirm_light, null, false)
    }
}