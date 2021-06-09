package com.aero51.moviedatabase.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.aero51.moviedatabase.databinding.FragmentMovieSearchBinding
import com.aero51.moviedatabase.ui.adapter.NowPlayingMoviesPagedListAdapter
import com.aero51.moviedatabase.utils.Constants
import com.aero51.moviedatabase.utils.ObjectClickListener
import com.aero51.moviedatabase.viewmodel.SearchViewModel
import com.aero51.moviedatabase.viewmodel.SharedViewModel

class MovieSearchFragment : Fragment(), ObjectClickListener {
    private var binding: FragmentMovieSearchBinding? = null
    private var searchViewModel: SearchViewModel? = null
    private lateinit var sharedViewModel: SharedViewModel
    private var moviesSearchAdapter: NowPlayingMoviesPagedListAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchViewModel = ViewModelProvider(requireActivity()).get(SearchViewModel::class.java)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentMovieSearchBinding.inflate(inflater, container, false)
        binding!!.moviesSearchRecyclerView.setHasFixedSize(true)
        moviesSearchAdapter = NowPlayingMoviesPagedListAdapter(this)
        binding!!.moviesSearchRecyclerView.adapter = moviesSearchAdapter
        binding!!.moviesSearchRecyclerView.layoutManager = GridLayoutManager(context, 3)
        registerMovieSearchObserver()
        showBackButton(true)
        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    fun showBackButton(show: Boolean) {
        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity?)!!.supportActionBar!!.setDisplayHomeAsUpEnabled(show)
        }
    }

    private fun registerMovieSearchObserver() {
        searchViewModel!!.movieSearchResult.observe(viewLifecycleOwner, { nowPlayingMovies -> moviesSearchAdapter!!.submitList(nowPlayingMovies) })
    }

    override fun onObjectItemClick(movie: Any, position: Int) {
        sharedViewModel.changeToMoviedetailsFragment(movie, position)
    }
}