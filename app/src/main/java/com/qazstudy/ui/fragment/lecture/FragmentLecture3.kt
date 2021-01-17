package com.qazstudy.ui.fragment.lecture

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.qazstudy.R
import com.qazstudy.databinding.FragmentLecture3Binding
import com.qazstudy.ui.activity.ActivityNavigation.Companion.isDark
import com.qazstudy.util.viewBinding

class FragmentLecture3 : BaseFragmentLecture(R.layout.fragment_lecture3) {

    private val binding by viewBinding(FragmentLecture3Binding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun setTheme() {
        if (isDark) {
            binding.lecture3Text.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.white
                )
            )
        }
    }
}