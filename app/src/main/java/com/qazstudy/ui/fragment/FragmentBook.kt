package com.qazstudy.ui.fragment

import com.qazstudy.R
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.qazstudy.databinding.FragmentBookBinding
import com.qazstudy.ui.adapter.AdapterBook
import com.qazstudy.util.viewBinding

class FragmentBook : Fragment(R.layout.fragment_book) {

    private val binding by viewBinding(FragmentBookBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val ar = arrayOf(
            R.drawable.book0,R.drawable.book1,R.drawable.book2,R.drawable.book3,
            R.drawable.book,R.drawable.book1,R.drawable.book2,R.drawable.book3
        )

        binding.run {
            fragmentBookRecyclerView.layoutManager = GridLayoutManager(context, 2)
            fragmentBookRecyclerView.adapter = AdapterBook(requireContext(), ar)
            fragmentBookRecyclerView.setHasFixedSize(true)
        }
    }
}