package com.qazstudy.presentation.presenter

import android.content.Context
import android.widget.ArrayAdapter
import com.qazstudy.R
import com.qazstudy.model.Country
import com.qazstudy.presentation.view.SettingView
import com.qazstudy.ui.adapter.AdapterCountry
import moxy.InjectViewState
import moxy.MvpPresenter
import java.util.*

@InjectViewState
class SettingPresenter(val context: Context) : MvpPresenter<SettingView>() {


    private val countryList = arrayOf (
        getCurrentCountry(),
        Country(context.getString(R.string.russian), R.drawable.russia),
        Country(context.getString(R.string.turkish), R.drawable.turkey),
        Country(context.getString(R.string.english), R.drawable.united_kingdom)
    )


    private var countryAdapter: AdapterCountry = AdapterCountry(context, countryList)

    fun getCountryAdapter(): AdapterCountry {
        return countryAdapter
    }

    private fun getCurrentCountry(): Country {
        return when(Locale.getDefault().displayLanguage) {
            "English" -> Country(context.getString(R.string.english), R.drawable.united_kingdom)
            "Russian" -> Country(context.getString(R.string.russian), R.drawable.russia)
            "Turkish" -> Country(context.getString(R.string.turkish), R.drawable.turkey)
            else -> Country(context.getString(R.string.russian), R.drawable.russia)
        }
    }
}