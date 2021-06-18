package com.aero51.moviedatabase.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.aero51.moviedatabase.databinding.FragmentTvShowsSearchBinding
import com.aero51.moviedatabase.ui.adapter.TvShowsPagedListAdapter
import com.aero51.moviedatabase.utils.MediaClickListener
import com.aero51.moviedatabase.viewmodel.SearchViewModel
import com.aero51.moviedatabase.viewmodel.SharedViewModel

class TvShowsSearchFragment : Fragment(),
    MediaClickListener {
    private var binding: FragmentTvShowsSearchBinding? = null
    private var searchViewModel: SearchViewModel? = null
    private var tvShowsSearchPagedListAdapter: TvShowsPagedListAdapter? = null
    private lateinit var sharedViewModel: SharedViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchViewModel = ViewModelProvider(requireActivity()).get(SearchViewModel::class.java)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentTvShowsSearchBinding.inflate(inflater, container, false)
        binding!!.tvShowsSearchRecyclerView.setHasFixedSize(true)
        tvShowsSearchPagedListAdapter = TvShowsPagedListAdapter(this)
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


    override fun onMediaItemClick(tvShow: Any?, position: Int) {
        Log.d("nikola","onObjectItemClick "+position)
        sharedViewModel.changeToTvShowDetailsFragment(tvShow, position)
    }


}