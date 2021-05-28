package com.qazstudy.ui.fragment.lecture

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.qazstudy.R
import com.qazstudy.databinding.FragmentLecture1Binding
import com.qazstudy.util.viewBinding

class FragmentLecture1 : Fragment(R.layout.fragment_lecture1) {

    private val binding by viewBinding(FragmentLecture1Binding::bind)

    private lateinit var arrayAbcLatin: Array<TextView>
    private lateinit var arrayAbcCyrillic: Array<TextView>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            arrayAbcLatin = arrayOf(
                abcLatin1, abcLatin2, abcLatin3, abcLatin4, abcLatin5,
                abcLatin6, abcLatin7, abcLatin8, abcLatin9, abcLatin10,
                abcLatin11, abcLatin12, abcLatin13, abcLatin14, abcLatin15,
                abcLatin16, abcLatin17, abcLatin18, abcLatin19, abcLatin20,
                abcLatin21, abcLatin22, abcLatin23, abcLatin24, abcLatin25,
                abcLatin26, abcLatin27, abcLatin28, abcLatin29, abcLatin30,
                abcLatin31, abcLatin32
            )

            arrayAbcCyrillic = arrayOf(
                abcCyrillic1, abcCyrillic2, abcCyrillic3, abcCyrillic4, abcCyrillic5,
                abcCyrillic6, abcCyrillic7, abcCyrillic8, abcCyrillic9, abcCyrillic10,
                abcCyrillic11, abcCyrillic12, abcCyrillic13, abcCyrillic14, abcCyrillic15,
                abcCyrillic16, abcCyrillic17, abcCyrillic18, abcCyrillic19, abcCyrillic20,
                abcCyrillic21, abcCyrillic22, abcCyrillic23, abcCyrillic24, abcCyrillic25,
                abcCyrillic26, abcCyrillic27, abcCyrillic28, abcCyrillic29, abcCyrillic30,
                abcCyrillic31, abcCyrillic32, abcCyrillic33, abcCyrillic34, abcCyrillic35,
                abcCyrillic36, abcCyrillic37, abcCyrillic38, abcCyrillic39, abcCyrillic40,
                abcCyrillic41, abcCyrillic42
            )
        }
    }
}