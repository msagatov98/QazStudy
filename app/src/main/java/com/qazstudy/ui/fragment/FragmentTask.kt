package com.qazstudy.ui.fragment

import com.qazstudy.R
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.qazstudy.model.Task
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.qazstudy.ui.adapter.AdapterTask
import kotlinx.android.synthetic.main.fragment_task.*
import androidx.recyclerview.widget.GridLayoutManager

class FragmentTask : Fragment() {

    private lateinit var taskHeader: Array<String>

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        taskHeader = resources.getStringArray(R.array.tasks_header)

        fragment_task__recycler_view.layoutManager = GridLayoutManager(context, 2)
        fragment_task__recycler_view.adapter = AdapterTask(requireContext(), Task(taskHeader))

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?):
            View? = inflater.inflate(R.layout.fragment_task, container, false)

}
