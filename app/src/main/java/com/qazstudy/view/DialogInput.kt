package com.qazstudy.view

import com.qazstudy.R
import android.os.Bundle
import android.view.View
import android.app.Dialog
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.dialog_input.view.*
import com.qazstudy.ui.activity.ActivityNavigation.Companion.isDark

class DialogInput(private val hint: String): DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        when (hint) {
            "name" -> return  getAlertDialog("Name")

            "city" -> return  getAlertDialog("City")

            "email" -> return  getAlertDialog("Email")

            "country" -> return  getAlertDialog("Country")

            "password" -> {
                return if (isDark) {
                    AlertDialog.Builder(requireContext())
                        .setView(layoutInflater.inflate(R.layout.dialog_input_password_dark, null))
                        .create()
                } else {
                    AlertDialog.Builder(requireContext())
                        .setView(layoutInflater.inflate(R.layout.dialog_input_password_light, null))
                        .create()
                }
            }
        }
        return  AlertDialog.Builder(requireContext()).setView(R.layout.dialog_input).create()
    }

    private fun getAlertDialog(str: String) : AlertDialog {

        val view = getDialogView()

        view.dialog_title.text = "Enter new $str"
        view.dialog_input.hint = str

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