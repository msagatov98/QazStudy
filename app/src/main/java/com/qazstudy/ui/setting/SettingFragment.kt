package com.qazstudy.ui.setting

import android.content.Intent
import android.content.res.Resources
import com.qazstudy.R
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import android.view.LayoutInflater
import androidx.core.os.ConfigurationCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.qazstudy.Navigation
import kotlinx.android.synthetic.main.fragment_setting.*
import java.util.*

class SettingFragment : Fragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        locale_ru.setOnClickListener{ ru() }
        locale_en.setOnClickListener { en() }
        locale_tr.setOnClickListener { tr() }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    fun ru() {
        val dm = resources.displayMetrics
        val conf = resources.configuration
        conf.setLocale(Locale("ru"))
        resources.updateConfiguration(conf, dm)
        val intent : Intent = Intent(context, Navigation::class.java)
        startActivity(intent)
    }

    fun en() {
        val dm = resources.displayMetrics
        val conf = resources.configuration
        conf.setLocale(Locale("en"))
        resources.updateConfiguration(conf, dm)
        val intent : Intent = Intent(context, Navigation::class.java)
        startActivity(intent)
    }

    fun tr() {
        val dm = resources.displayMetrics
        val conf = resources.configuration
        conf.setLocale(Locale("tr"))
        resources.updateConfiguration(conf, dm)
        val intent : Intent = Intent(context, Navigation::class.java)
        startActivity(intent)
    }
}