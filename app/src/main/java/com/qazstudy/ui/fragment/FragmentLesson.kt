package com.qazstudy.ui.fragment

import com.qazstudy.R
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.qazstudy.model.Lesson
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.qazstudy.ui.adapter.AdapterLesson
import kotlinx.android.synthetic.main.fragment_lesson.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.DividerItemDecoration
import com.qazstudy.ui.activity.ActivityNavigation.Companion.isDark

class FragmentLesson() : Fragment() {

    private lateinit var lesson: Lesson
    private lateinit var lessonHeader: Array<String>
    private lateinit var lessonDescription: Array<String>

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        lessonHeader = resources.getStringArray(R.array.lessons_header)
        lessonDescription = resources.getStringArray(R.array.lessons_description)

        lesson = Lesson(lessonHeader, lessonDescription)

        val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)

        fragment_lesson__recycler_view.addItemDecoration(divider)
        fragment_lesson__recycler_view.layoutManager = LinearLayoutManager(this.context)
        fragment_lesson__recycler_view.adapter = AdapterLesson(requireContext(), lesson, isDark)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_lesson, container, false)
    }
}