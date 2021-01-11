package com.qazstudy.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.core.content.ContextCompat
import com.qazstudy.R
import com.qazstudy.databinding.FragmentSettingBinding
import com.qazstudy.model.Country
import com.qazstudy.presentation.presenter.SettingPresenter
import com.qazstudy.presentation.view.SettingView
import com.qazstudy.ui.adapter.AdapterCountry
import com.qazstudy.util.viewBinding
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class FragmentSetting : MvpAppCompatFragment(R.layout.fragment_setting), SettingView {

    private val binding by viewBinding(FragmentSettingBinding::bind)

    @InjectPresenter
    lateinit var mSettingPresenter: SettingPresenter

    @ProvidePresenter
    fun providePresenter(): SettingPresenter {
        return SettingPresenter(requireContext())
    }

    private lateinit var countryList : Array<Country>

    private lateinit var countryAdapter: AdapterCountry

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        countryList = arrayOf(
            mSettingPresenter.getCurrentCountry(),
            Country(getString(R.string.russian), R.drawable.russia),
            Country(getString(R.string.turkish), R.drawable.turkey),
            Country(getString(R.string.english), R.drawable.united_kingdom)
        )

        countryAdapter = AdapterCountry(requireContext(), countryList)

        binding.spinnerSettingLanguage.adapter = countryAdapter

        binding.spinnerSettingLanguage.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    when (position) {
                        1 -> mSettingPresenter.ru()
                        2 -> mSettingPresenter.tr()
                        3 -> mSettingPresenter.en()
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }

    override fun setMode() {
        binding.fragmentSettingConstraintLayout.background =
            ContextCompat.getDrawable(requireContext(), R.color.dark)
        binding.tvSettingAbc.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        binding.tvSettingLanguage.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.white
            )
        )
    }
}