package com.qazstudy.ui.gallery

import com.qazstudy.R
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import com.qazstudy.ui.TaskAdapter
import com.qazstudy.ui.LessonAdapter
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_task.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.DividerItemDecoration
import kotlinx.android.synthetic.main.activity_navigation.*
import kotlinx.android.synthetic.main.app_bar_navigation.*
import kotlinx.android.synthetic.main.fragment_lesson.*
import kotlinx.android.synthetic.main.view_holder_lesson.*

class GalleryFragment : Fragment() {

    private lateinit var taskHeader: Array<String>
    private lateinit var lessonDescription: Array<String>
    var isDark = false

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        taskHeader = resources.getStringArray(R.array.tasks_header)
        lessonDescription = resources.getStringArray(R.array.lessons_description)

        val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)

        fragment_task__recycler_view.addItemDecoration(divider)
        fragment_task__recycler_view.layoutManager = LinearLayoutManager(this.context)
        fragment_task__recycler_view.adapter = TaskAdapter(taskHeader, lessonDescription, isDark)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_task, container, false)
    }
}
