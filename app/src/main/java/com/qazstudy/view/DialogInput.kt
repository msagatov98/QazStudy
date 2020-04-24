package com.qazstudy.view

import com.qazstudy.R
import android.os.Bundle
import android.app.Dialog
import android.text.InputType
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.dialog_input.view.*
import com.qazstudy.ui.activity.ActivityNavigation.Companion.isDark

class DialogInput(private val hint: String): DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val view = getDialogView()

        when (hint) {
            "name" -> {
                view.dialog_input.hint = "Name"
                view.dialog_input.inputType = InputType.TYPE_CLASS_TEXT
                return  AlertDialog.Builder(requireContext())
                    .setView(view)
                    .create()
            }

            "email" -> {
                view.dialog_input.hint = "Email"
                view.dialog_input.inputType = InputType.TYPE_CLASS_TEXT
                return  AlertDialog.Builder(requireContext())
                    .setView(view)
                    .create()
            }

            "country" -> {
                view.dialog_input.hint = "Country"
                view.dialog_input.inputType = InputType.TYPE_CLASS_TEXT
                return  AlertDialog.Builder(requireContext())
                    .setView(view)
                    .create()
            }

            "city" -> {
                view.dialog_input.hint = "City"
                view.dialog_input.inputType = InputType.TYPE_CLASS_TEXT
                return  AlertDialog.Builder(requireContext())
                    .setView(view)
                    .create()
            }

            "password" -> {
                if (isDark) {
                    return AlertDialog.Builder(requireContext())
                        .setView(layoutInflater.inflate(R.layout.dialog_input_password_dark, null))
                        .create()
                } else {
                    return AlertDialog.Builder(requireContext())
                        .setView(layoutInflater.inflate(R.layout.dialog_input_password_light, null))
                        .create()
                }
            }
        }
        return  AlertDialog.Builder(requireContext()).setView(R.layout.dialog_input).create()
    }

    private fun getDialogView() : View {

        return if (isDark) {
            layoutInflater.inflate(R.layout.dialog_input_dark, null)
        } else {
            layoutInflater.inflate(R.layout.dialog_input, null)
        }
    }
}