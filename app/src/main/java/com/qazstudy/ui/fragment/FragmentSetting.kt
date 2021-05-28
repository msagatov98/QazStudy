package com.qazstudy.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import com.qazstudy.R
import com.qazstudy.databinding.FragmentSettingBinding
import com.qazstudy.model.Country
import com.qazstudy.ui.adapter.AdapterCountry
import com.qazstudy.util.viewBinding

class FragmentSetting : Fragment(R.layout.fragment_setting) {

    private val binding by viewBinding(FragmentSettingBinding::bind)

    private lateinit var countryList: Array<Country>

    private lateinit var countryAdapter: AdapterCountry

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val russia = Country(getString(R.string.russian), R.drawable.russia)
        val turkey = Country(getString(R.string.turkish), R.drawable.turkey)
        val gb = Country(getString(R.string.english), R.drawable.united_kingdom)

        countryList = arrayOf(
            russia,
            turkey,
            gb
        )

        countryAdapter = AdapterCountry(requireContext(), countryList)

        binding.spinnerSettingLanguage.adapter = countryAdapter

        binding.spinnerSettingLanguage.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    when (position) {
                        1 -> {

                        }
                        2 -> {

                        }
                        3 -> {

                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }
}