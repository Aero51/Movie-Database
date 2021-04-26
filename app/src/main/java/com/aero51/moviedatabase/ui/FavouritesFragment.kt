package com.aero51.moviedatabase.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aero51.moviedatabase.databinding.FragmentFavouritesBinding
import com.aero51.moviedatabase.ui.adapter.MovieFavoritesAdapter
import com.aero51.moviedatabase.utils.GenreObjectClickListener
import com.aero51.moviedatabase.viewmodel.FavoritesViewModel

class FavouritesFragment : Fragment(), GenreObjectClickListener {

    private var binding: FragmentFavouritesBinding? = null
    private var favoritesViewModel: FavoritesViewModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favoritesViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)).get(FavoritesViewModel::class.java)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFavouritesBinding.inflate(inflater, container, false)

        binding!!.favouriteMoviesRecyclerViewHorizontal.setHasFixedSize(true)
        binding!!.favouriteTvShowsRecyclerViewHorizontal.setHasFixedSize(true)
        binding!!.favouriteMoviesRecyclerViewHorizontal.isNestedScrollingEnabled = false
        binding!!.favouriteTvShowsRecyclerViewHorizontal.isNestedScrollingEnabled = false
        val moviesLinearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val tvShowsLinearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding!!.favouriteMoviesRecyclerViewHorizontal.layoutManager = moviesLinearLayoutManager
        binding!!.favouriteTvShowsRecyclerViewHorizontal.layoutManager = tvShowsLinearLayoutManager


        getFavouriteMovies()

        return binding!!.root
    }

    private fun getFavouriteMovies() {
        favoritesViewModel?.getFavoriteMovies()?.observe(viewLifecycleOwner, Observer { favoriteMovieList ->

            if (!favoriteMovieList.isEmpty()) {

              val movieFavoritesAdapter: MovieFavoritesAdapter = MovieFavoritesAdapter(favoriteMovieList,this)
                binding?.favouriteMoviesRecyclerViewHorizontal?.adapter = movieFavoritesAdapter

            }

        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onGenreItemClick(genreId: Int, position: Int) {
        TODO("Not yet implemented")
    }


}