package com.qazstudy.ui.fragment

import com.qazstudy.R
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import com.qazstudy.ui.adapter.AdapterTask
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_task.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.DividerItemDecoration
import com.qazstudy.ui.activity.ActivityNavigation.Companion.isDark

class FragmentTask : Fragment() {

    private lateinit var taskHeader: Array<String>
    private lateinit var lessonDescription: Array<String>

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        taskHeader = resources.getStringArray(R.array.tasks_header)
        lessonDescription = resources.getStringArray(R.array.lessons_description)

        val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)

        fragment_task__recycler_view.addItemDecoration(divider)
        fragment_task__recycler_view.layoutManager = LinearLayoutManager(this.context)
        fragment_task__recycler_view.adapter =
            AdapterTask(
                taskHeader,
                lessonDescription,
                isDark
            )

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_task, container, false)
    }
}