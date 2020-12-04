package com.qazstudy.ui.fragment

import com.qazstudy.R
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.qazstudy.databinding.FragmentBookBinding
import com.qazstudy.ui.adapter.AdapterBook

class FragmentBook : Fragment() {

    private var _binding: FragmentBookBinding? = null

    private val binding get() = _binding

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val ar : Array<Int> = arrayOf(
            R.drawable.book0,R.drawable.book1,R.drawable.book2,R.drawable.book3,
            R.drawable.book,R.drawable.book1,R.drawable.book2,R.drawable.book3
        )

        binding?.fragmentBookRecyclerView!!.layoutManager = GridLayoutManager(context, 2)
        binding?.fragmentBookRecyclerView!!.adapter = AdapterBook(requireContext(), ar)
        binding?.fragmentBookRecyclerView!!.setHasFixedSize(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentBookBinding.inflate(inflater, container, false)

        return binding?.root!!
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}