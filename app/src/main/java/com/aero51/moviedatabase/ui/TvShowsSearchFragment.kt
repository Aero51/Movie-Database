package com.aero51.moviedatabase.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.aero51.moviedatabase.databinding.FragmentTvShowsSearchBinding
import com.aero51.moviedatabase.repository.model.tmdb.movie.NowPlayingMoviesPage.NowPlayingMovie
import com.aero51.moviedatabase.ui.adapter.TvShowsPagedListAdapter
import com.aero51.moviedatabase.utils.NowPlayingItemClickListener
import com.aero51.moviedatabase.viewmodel.SearchViewModel

class TvShowsSearchFragment : Fragment(), NowPlayingItemClickListener {
    private var binding: FragmentTvShowsSearchBinding? = null
    private var searchViewModel: SearchViewModel? = null
    private var tvShowsSearchPagedListAdapter: TvShowsPagedListAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchViewModel = ViewModelProvider(requireActivity()).get(SearchViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentTvShowsSearchBinding.inflate(inflater, container, false)
        binding!!.tvShowsSearchRecyclerView.setHasFixedSize(true)
        tvShowsSearchPagedListAdapter = TvShowsPagedListAdapter()
        binding!!.tvShowsSearchRecyclerView.adapter = tvShowsSearchPagedListAdapter
        binding!!.tvShowsSearchRecyclerView.layoutManager = GridLayoutManager(context, 3)
        registerTvShowsSearchObserver()
        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun registerTvShowsSearchObserver() {
        searchViewModel!!.tvShowSearchResult.observe(viewLifecycleOwner, { tvShows -> tvShowsSearchPagedListAdapter!!.submitList(tvShows) })
    }

    override fun OnItemClick(result: NowPlayingMovie, position: Int) {}
}