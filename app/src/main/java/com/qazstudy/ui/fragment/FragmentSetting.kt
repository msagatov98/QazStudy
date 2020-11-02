package com.qazstudy.ui.fragment

import java.util.*
import com.qazstudy.R
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.content.Intent
import moxy.MvpAppCompatFragment
import android.view.LayoutInflater
import android.widget.AdapterView
import androidx.core.content.ContextCompat
import com.qazstudy.presentation.presenter.SettingPresenter
import com.qazstudy.presentation.view.SettingView
import com.qazstudy.ui.activity.ActivityNavigation
import kotlinx.android.synthetic.main.fragment_setting.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class FragmentSetting : MvpAppCompatFragment(), SettingView {

    @InjectPresenter
    lateinit var mSettingPresenter: SettingPresenter

    @ProvidePresenter
    fun providePresenter(): SettingPresenter {
        return SettingPresenter(requireContext())
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        spinner_setting_language.adapter = mSettingPresenter.getAdapter()

        spinner_setting_language.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    1 -> ru()
                    2 -> tr()
                    3 -> en()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) { }

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

    override fun setMode() {
        fragment_setting__constraint_layout.background =
            ContextCompat.getDrawable(requireContext(), R.color.dark)
    }
}