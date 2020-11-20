package com.qazstudy.ui.dialog

import com.qazstudy.R
import android.os.Bundle
import android.view.View
import android.app.Dialog
import com.qazstudy.util.*
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.qazstudy.ui.activity.ActivityNavigation
import kotlinx.android.synthetic.main.dialog_confirm_dark.view.*
import com.qazstudy.ui.activity.ActivityNavigation.Companion.mUser

class DialogConfirm(private val hint: String): DialogFragment() {

    private val AUTH = FirebaseAuth.getInstance()
    private var STORAGE = FirebaseStorage.getInstance().reference
    private var DATABASE = FirebaseDatabase.getInstance().reference

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
                AUTH.signOut()
                requireActivity().startActivity(Intent(requireActivity(), ActivityNavigation::class.java))
                requireActivity().finish()
            }
        } else if (str == "delete") {
            view.dialog_title.text = resources.getString(R.string.confirm_delete)

            view.dialog_confirm__ok.setOnClickListener {
                STORAGE.child(NODE_USER).child(AUTH.currentUser!!.uid).child(NODE_PHOTO).delete()
                DATABASE.child(NODE_USER).child(AUTH.currentUser!!.uid).removeValue()
                AUTH.currentUser!!.delete().addOnCompleteListener {
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
        return if (mUser.isDark)
            layoutInflater.inflate(R.layout.dialog_confirm_dark, null, false)
        else
            layoutInflater.inflate(R.layout.dialog_confirm_light, null, false)
    }
}