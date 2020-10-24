package com.qazstudy.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.qazstudy.R
import com.qazstudy.model.Lesson
import com.qazstudy.ui.adapter.AdapterLesson
import kotlinx.android.synthetic.main.fragment_lesson.*

class FragmentLesson() : Fragment() {

    val TAG = "FragmentLesson"
    private lateinit var lesson: Lesson

    private var lessonImage =
        arrayOf(
            R.drawable.intro, R.drawable.abc, R.drawable.reading,
            R.drawable.ic_android, R.drawable.ic_android, R.drawable.ic_android,
            R.drawable.ic_android, R.drawable.ic_android, R.drawable.ic_android, R.drawable.ic_android
        )

    private lateinit var lessonHeader: Array<String>

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        lessonHeader = resources.getStringArray(R.array.lessons_header)

        lesson = Lesson(lessonImage, lessonHeader)

        val adapter = AdapterLesson(requireContext(), lesson)
        val glm = GridLayoutManager(this.context, 2)

        glm.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {

                return when(adapter.getItemViewType(position)) {
                    adapter.TYPE_SINGLE_ITEM -> 2
                    adapter.TYPE_DOUBLE_ITEM -> 1
                    else -> -1
                }
            }
        }

        fragment_lesson__recycler_view.layoutManager = glm
        fragment_lesson__recycler_view.adapter = adapter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_lesson, container, false)
    }
}