package com.qazstudy.presentation.presenter

import android.content.Context
import android.content.Intent
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat.startActivity
import com.qazstudy.R
import com.qazstudy.model.Country
import com.qazstudy.presentation.view.SettingView
import com.qazstudy.ui.activity.ActivityNavigation
import com.qazstudy.ui.adapter.AdapterCountry
import moxy.InjectViewState
import moxy.MvpPresenter
import java.util.*

@InjectViewState
class SettingPresenter(val context: Context) : MvpPresenter<SettingView>() {

    fun getCurrentCountry(): Country {
        return when(Locale.getDefault().displayLanguage) {
            context.getString(R.string.english) -> Country(context.getString(R.string.english), R.drawable.united_kingdom)
            "Русский" -> Country(context.getString(R.string.russian), R.drawable.russia)
            "Turkce" -> Country(context.getString(R.string.turkish), R.drawable.turkey)
            else -> Country(context.getString(R.string.russian), R.drawable.russia)
        }
    }

    fun ru() {
        val dm = context.resources.displayMetrics
        val conf = context.resources.configuration
        conf.setLocale(Locale("ru"))
        context.resources.updateConfiguration(conf, dm)
        val intent  = Intent(context, ActivityNavigation::class.java)
        context.startActivity(intent)
    }

    fun en() {
        val dm = context.resources.displayMetrics
        val conf = context.resources.configuration
        conf.setLocale(Locale.ENGLISH)
        context.resources.updateConfiguration(conf, dm)
        val intent  = Intent(context, ActivityNavigation::class.java)
        context.startActivity(intent)
    }

    fun tr() {
        val dm = context.resources.displayMetrics
        val conf = context.resources.configuration
        conf.setLocale(Locale("tr"))
        context.resources.updateConfiguration(conf, dm)
        val intent  = Intent(context, ActivityNavigation::class.java)
        context.startActivity(intent)
    }
}