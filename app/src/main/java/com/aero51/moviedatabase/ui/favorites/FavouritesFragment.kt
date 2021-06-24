package com.aero51.moviedatabase.ui.favorites

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aero51.moviedatabase.databinding.FragmentFavouritesBinding
import com.aero51.moviedatabase.repository.model.tmdb.credits.ActorSearchResponse.ActorSearch
import com.aero51.moviedatabase.repository.model.tmdb.movie.MovieFavourite
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.TvShowFavourite
import com.aero51.moviedatabase.ui.adapter.ActorSearchAdapter
import com.aero51.moviedatabase.ui.adapter.MovieFavoritesAdapter
import com.aero51.moviedatabase.ui.adapter.TvShowFavouritesAdapter
import com.aero51.moviedatabase.viewmodel.FavoritesViewModel
import com.aero51.moviedatabase.viewmodel.SharedViewModel

class FavouritesFragment : Fragment(),MovieFavoritesAdapter.OnMovieItemClickListener,TvShowFavouritesAdapter.OnTvShowItemClickListener {

    private var binding: FragmentFavouritesBinding? = null
    private var favoritesViewModel: FavoritesViewModel? = null
    private lateinit var sharedViewModel: SharedViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favoritesViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(FavoritesViewModel::class.java)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavouritesBinding.inflate(inflater, container, false)

        binding!!.favouriteMoviesRecyclerViewHorizontal.setHasFixedSize(true)
        binding!!.favouriteTvShowsRecyclerViewHorizontal.setHasFixedSize(true)
        binding!!.favouriteMoviesRecyclerViewHorizontal.isNestedScrollingEnabled = false
        binding!!.favouriteTvShowsRecyclerViewHorizontal.isNestedScrollingEnabled = false
        val moviesLinearLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val tvShowsLinearLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding!!.favouriteMoviesRecyclerViewHorizontal.layoutManager = moviesLinearLayoutManager
        binding!!.favouriteTvShowsRecyclerViewHorizontal.layoutManager = tvShowsLinearLayoutManager


        getFavouriteMovies()
        getFavouriteTvShows()

        return binding!!.root
    }

    private fun getFavouriteMovies() {
        favoritesViewModel?.getFavoriteMovies()
            ?.observe(viewLifecycleOwner, Observer { favoriteMovieList ->

                if (!favoriteMovieList.isEmpty()) {

                    val movieFavoritesAdapter: MovieFavoritesAdapter =
                        MovieFavoritesAdapter(favoriteMovieList, this)
                    binding?.favouriteMoviesRecyclerViewHorizontal?.adapter = movieFavoritesAdapter

                }

            })
    }

    private fun getFavouriteTvShows() {
        favoritesViewModel?.getFavoriteTvShows()
            ?.observe(viewLifecycleOwner, Observer { favoriteTvShowList ->
                if (!favoriteTvShowList.isEmpty()) {
                    val tvShowFavoritesAdapter: TvShowFavouritesAdapter =
                        TvShowFavouritesAdapter(favoriteTvShowList, this)
                    binding?.favouriteTvShowsRecyclerViewHorizontal?.adapter =
                        tvShowFavoritesAdapter
                }

            })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onMovieItemClick(movieFavorite: MovieFavourite, position: Int) {
        sharedViewModel.changeToFavouriteMoviedetailsFragment(movieFavorite)
    }

    override fun onTvShowItemClick(tvShowFavourite: TvShowFavourite, position: Int) {
        Log.d("nikola","tvShowFavourite: "+tvShowFavourite.original_name)
    }


}