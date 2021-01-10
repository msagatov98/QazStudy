package com.qazstudy.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.qazstudy.R
import com.qazstudy.databinding.FragmentLessonBinding
import com.qazstudy.model.Lesson
import com.qazstudy.ui.adapter.AdapterLesson
import com.qazstudy.util.viewBinding

class FragmentLesson : Fragment(R.layout.fragment_lesson) {

    private val binding by viewBinding(FragmentLessonBinding::bind)

    private lateinit var lesson: Lesson

    private var lessonImage =
        arrayOf(
            R.drawable.intro, R.drawable.abc, R.drawable.reading,
            R.drawable.ic_android, R.drawable.ic_android, R.drawable.ic_android,
            R.drawable.ic_android, R.drawable.ic_android, R.drawable.ic_android, R.drawable.ic_android
        )

    private lateinit var lessonHeader: Array<String>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        lessonHeader = resources.getStringArray(R.array.lessons_header)
        lesson = Lesson(lessonImage, lessonHeader)

        val adapterLesson = AdapterLesson(requireContext(), lesson)
        val gridLayoutManager = GridLayoutManager(this.context, 2)

        gridLayoutManager.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {

                return when(adapterLesson.getItemViewType(position)) {
                    adapterLesson.TYPE_SINGLE_ITEM -> 2
                    adapterLesson.TYPE_DOUBLE_ITEM -> 1
                    else -> -1
                }
            }
        }

        binding.fragmentLessonRecyclerView.adapter = adapterLesson
        binding.fragmentLessonRecyclerView.layoutManager = gridLayoutManager

        super.onViewCreated(view, savedInstanceState)
    }
}