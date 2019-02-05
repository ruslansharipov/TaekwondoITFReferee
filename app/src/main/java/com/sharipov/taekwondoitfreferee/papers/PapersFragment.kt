package com.sharipov.taekwondoitfreferee.papers

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import com.sharipov.taekwondoitfreferee.MainViewModel
import com.sharipov.taekwondoitfreferee.R
import kotlinx.android.synthetic.main.papers_fragment.*

class PapersFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
    private lateinit var papersAdapter: PapersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.papers_fragment, container, false)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.papersLiveData.observe(this, Observer { showPapers(it) })
        return view
    }

    private fun showPapers(list: List<Int>) = with(list) {
        papersAdapter = PapersAdapter(this)
        recyclerView.apply {
            layoutManager = GridLayoutManager(context, getSpanCount())
            adapter = papersAdapter
        }
        progressBar.visibility = View.GONE
    }

    private fun getSpanCount(): Int =
        if (context?.resources?.configuration?.orientation == OrientationHelper.HORIZONTAL) HORIZONTAL_SPAN_COUNT
        else VERTICAL_SPAN_COUNT

    companion object {
        const val VERTICAL_SPAN_COUNT = 4
        const val HORIZONTAL_SPAN_COUNT = 6
    }
}