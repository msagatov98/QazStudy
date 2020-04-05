package com.qazstudy.ui.home

import android.content.Intent
import com.qazstudy.R
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.qazstudy.ProfileActivity
import com.qazstudy.ui.LessonAdapter
import com.qazstudy.ui.TaskAdapter
import kotlinx.android.synthetic.main.activity_navigation.*
import kotlinx.android.synthetic.main.app_bar_navigation.*
import kotlinx.android.synthetic.main.fragment_lesson.*
import kotlinx.android.synthetic.main.fragment_task.*
import kotlinx.android.synthetic.main.nav_header_navigation.*
import kotlinx.android.synthetic.main.view_holder_lesson.*

class FragmentLesson() : Fragment() {

    private lateinit var lessonHeader: Array<String>
    private lateinit var lessonDescription: Array<String>
    var isDark = false

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        lessonHeader = resources.getStringArray(R.array.lessons_header)
        lessonDescription = resources.getStringArray(R.array.lessons_description)

        val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)

        fragment_lesson__recycler_view.addItemDecoration(divider)
        fragment_lesson__recycler_view.layoutManager = LinearLayoutManager(this.context)
        fragment_lesson__recycler_view.adapter = LessonAdapter(lessonHeader, lessonDescription, isDark)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_lesson, container, false)
    }
}