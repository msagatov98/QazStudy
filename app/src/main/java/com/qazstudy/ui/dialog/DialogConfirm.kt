package com.qazstudy.ui.dialog

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.qazstudy.R
import com.qazstudy.model.Firebase
import com.qazstudy.ui.activity.ActivityNavigation.Companion.mUser
import com.qazstudy.ui.activity.LoginActivity
import com.qazstudy.util.NODE_PHOTO
import com.qazstudy.util.NODE_USER
import com.qazstudy.util.showToast
import kotlinx.android.synthetic.main.dialog_confirm_dark.view.*

class DialogConfirm(private val hint: String): DialogFragment() {

    val firebase = Firebase()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        when (hint) {
            "exit" -> return getAlertDialog("exit")
            "delete" -> return getAlertDialog("delete")
        }

        return  AlertDialog.Builder(requireContext()).setView(R.layout.dialog_input).create()
    }

    private fun getAlertDialog(str: String) : AlertDialog {

        val view = getDialogView()

        val alertDialog: AlertDialog = AlertDialog.Builder(requireActivity()).setView(view).create()

        if (str == "exit") {

            view.dialog_title.text = resources.getString(R.string.confirm_exit)

            view.dialog_confirm__ok.setOnClickListener {
                firebase.signOut()
                val intent = Intent(activity, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                activity?.startActivity(intent)
                activity?.finish()
            }

        } else if (str == "delete") {

            view.dialog_title.text = resources.getString(R.string.confirm_delete)

            view.dialog_confirm__ok.setOnClickListener {
                if (firebase.deleteUser() ) {
                    val intent = Intent(activity, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    activity?.startActivity(intent)
                    activity?.finish()
                } else {
                    activity?.showToast("Can't delete account")
                }
            }

        }

        view.dialog_confirm__cancel.setOnClickListener {
            alertDialog.dismiss()
        }
        
        return alertDialog
    }

    private fun getDialogView() : View {
        return if (mUser.isDark)
            layoutInflater.inflate(R.layout.dialog_confirm_dark, null, false)
        else
            layoutInflater.inflate(R.layout.dialog_confirm_light, null, false)
    }
}