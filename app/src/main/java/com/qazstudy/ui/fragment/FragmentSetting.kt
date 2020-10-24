package com.qazstudy.ui.fragment

import java.util.*
import com.qazstudy.R
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.content.Intent
import android.view.LayoutInflater
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.qazstudy.ui.activity.ActivityNavigation
import kotlinx.android.synthetic.main.fragment_setting.*
import com.qazstudy.ui.activity.ActivityNavigation.Companion.isDark

class FragmentSetting : Fragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        locale_ru.setOnClickListener{ ru() }
        locale_en.setOnClickListener { en() }
        locale_tr.setOnClickListener { tr() }

        if (isDark) {
            fragment_setting__constraint_layout.background = requireContext().getDrawable(R.color.dark)
        } else {
            fragment_setting__constraint_layout.background = requireContext().getDrawable(R.color.white)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    private fun ru() {
        val dm = resources.displayMetrics
        val conf = resources.configuration
        conf.setLocale(Locale("ru"))
        resources.updateConfiguration(conf, dm)
        val intent  = Intent(context, ActivityNavigation::class.java)
        startActivity(intent)
    }

    private fun en() {
        val dm = resources.displayMetrics
        val conf = resources.configuration
        conf.setLocale(Locale.ENGLISH)
        resources.updateConfiguration(conf, dm)
        val intent  = Intent(context, ActivityNavigation::class.java)
        startActivity(intent)
    }

    private fun tr() {
        val dm = resources.displayMetrics
        val conf = resources.configuration
        conf.setLocale(Locale("tr"))
        resources.updateConfiguration(conf, dm)
        val intent  = Intent(context, ActivityNavigation::class.java)
        startActivity(intent)
    }
}