package com.qazstudy.ui.fragment.lecture

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.qazstudy.R
import com.qazstudy.databinding.FragmentLecture4Binding
import com.qazstudy.ui.activity.ActivityNavigation.Companion.isDark
import com.qazstudy.util.viewBinding

class FragmentLecture4 : BaseFragmentLecture(R.layout.fragment_lecture4) {

    private val binding by viewBinding(FragmentLecture4Binding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun setTheme() {
        if (isDark) {
            binding.lecture4Text.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.white
                )
            )
        }
    }
}
