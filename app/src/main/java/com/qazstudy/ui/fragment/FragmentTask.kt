package com.qazstudy.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.qazstudy.R
import com.qazstudy.databinding.FragmentTaskBinding
import com.qazstudy.model.Task
import com.qazstudy.ui.adapter.AdapterTask
import com.qazstudy.util.viewBinding

class FragmentTask : Fragment(R.layout.fragment_task) {

    private val binding by viewBinding(FragmentTaskBinding::bind)

    private lateinit var taskHeader: Array<String>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        taskHeader = resources.getStringArray(R.array.tasks_header)
        binding.run {
            fragmentTaskRecyclerView.layoutManager = GridLayoutManager(context, 2)
            fragmentTaskRecyclerView.adapter = AdapterTask(requireContext(), Task(taskHeader))
        }
    }
}
