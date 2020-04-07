package com.qazstudy.ui.fragment

import com.qazstudy.R
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.qazstudy.ui.activity.Navigation.Companion.isDark
import com.qazstudy.ui.adapter.LessonAdapter
import kotlinx.android.synthetic.main.fragment_lesson.*

class FragmentLesson() : Fragment() {

    private lateinit var lessonHeader: Array<String>
    private lateinit var lessonDescription: Array<String>

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        lessonHeader = resources.getStringArray(R.array.lessons_header)
        lessonDescription = resources.getStringArray(R.array.lessons_description)

        val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)

        fragment_lesson__recycler_view.addItemDecoration(divider)
        fragment_lesson__recycler_view.layoutManager = LinearLayoutManager(this.context)
        fragment_lesson__recycler_view.adapter =
            LessonAdapter(
                lessonHeader,
                lessonDescription,
                isDark
            )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_lesson, container, false)
    }
}