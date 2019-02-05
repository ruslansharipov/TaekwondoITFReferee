package com.sharipov.taekwondoitfreferee.fragment.themes

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.sharipov.taekwondoitfreferee.activity_main.MainViewModel
import com.sharipov.taekwondoitfreferee.R
import com.sharipov.taekwondoitfreferee.repository.Theme
import kotlinx.android.synthetic.main.themes_fragment.*

class ThemesFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
    private lateinit var themesAdapter: ThemesAdapter

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.themes_search, menu)
        val item = menu.findItem(R.id.search)
        val searchView = item.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = themesAdapter.filterThemes(query)
            override fun onQueryTextChange(newText: String?): Boolean = themesAdapter.filterThemes(newText)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val view = inflater.inflate(R.layout.themes_fragment, container, false)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.themes.observe(this, Observer {
            themesAdapter = ThemesAdapter(it)
            initRecyclerView(it)
            progressBar.visibility = View.GONE
        })
        return view
    }

    private fun initRecyclerView(themes: List<Theme>) = with(recyclerView) {
        adapter = themesAdapter
        layoutManager = LinearLayoutManager(context)
        hasFixedSize()
    }

}
