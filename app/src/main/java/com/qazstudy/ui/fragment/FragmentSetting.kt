package com.qazstudy.ui.fragment

import android.content.Intent
import com.qazstudy.R
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.qazstudy.ui.activity.ActivityNavigation
import com.qazstudy.ui.activity.ActivityNavigation.Companion.isDark
import kotlinx.android.synthetic.main.fragment_setting.*
import java.util.*

class FragmentSetting : Fragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        locale_ru.setOnClickListener{ ru() }
        locale_en.setOnClickListener { en() }
        locale_tr.setOnClickListener { tr() }

        if (isDark) {
            fragment_setting__constraint_layout.background = resources.getDrawable(R.color.dark)
        } else {
            fragment_setting__constraint_layout.background = resources.getDrawable(R.color.white)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    fun ru() {
        val dm = resources.displayMetrics
        val conf = resources.configuration
        conf.setLocale(Locale("ru"))
        resources.updateConfiguration(conf, dm)
        val intent : Intent = Intent(context, ActivityNavigation::class.java)
        startActivity(intent)
    }

    fun en() {
        val dm = resources.displayMetrics
        val conf = resources.configuration
        conf.setLocale(Locale("en"))
        resources.updateConfiguration(conf, dm)
        val intent : Intent = Intent(context, ActivityNavigation::class.java)
        startActivity(intent)
    }

    fun tr() {
        val dm = resources.displayMetrics
        val conf = resources.configuration
        conf.setLocale(Locale("tr"))
        resources.updateConfiguration(conf, dm)
        val intent : Intent = Intent(context, ActivityNavigation::class.java)
        startActivity(intent)
    }
}