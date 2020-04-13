package com.qazstudy.ui.fragment

import com.qazstudy.R
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.qazstudy.ui.activity.ActivityNavigation.Companion.isDark
import com.qazstudy.ui.adapter.AdapterBook
import com.qazstudy.ui.adapter.AdapterLesson
import com.qazstudy.util.Lesson
import kotlinx.android.synthetic.main.fragment_book.*
import kotlinx.android.synthetic.main.fragment_lesson.*

class FragmentBook() : Fragment() {


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val ar : Array<Int> = arrayOf(R.drawable.book,R.drawable.book1,R.drawable.book2,R.drawable.book3,R.drawable.book,R.drawable.book1,R.drawable.book2,R.drawable.book3)

        fragment_book__recycler_view.layoutManager = GridLayoutManager(context, 2)
        fragment_book__recycler_view.adapter = AdapterBook(ar)
        fragment_book__recycler_view.setHasFixedSize(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_book, container, false)
    }
}