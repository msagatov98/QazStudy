package com.qazstudy.ui.fragment

import android.content.Context
import com.qazstudy.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.qazstudy.util.coordinateButtonAndInput
import com.qazstudy.util.showToast
import kotlinx.android.synthetic.main.fragment_register_email.*
import kotlinx.android.synthetic.main.fragment_register_namepass.*

class FragmentEmail: Fragment() {

    private lateinit var mListener: Listener

    interface Listener {
        fun onNext(email: String)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register_email, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        coordinateButtonAndInput(fragment_register__btn_next, fragment_register__input_email)

        fragment_register__btn_next.setOnClickListener {
            val email = fragment_register__input_email.text.toString()
            mListener.onNext(email)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = context as Listener
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