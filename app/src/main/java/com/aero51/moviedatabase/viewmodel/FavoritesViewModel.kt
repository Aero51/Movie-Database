package com.aero51.moviedatabase.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.aero51.moviedatabase.repository.FavouritesRepository
import com.aero51.moviedatabase.repository.model.tmdb.movie.MovieFavourite
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.TvShowFavourite
import com.aero51.moviedatabase.utils.AppExecutors

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {
    private val favoritesRepository: FavouritesRepository

    init {
        val executors = AppExecutors()
        favoritesRepository = FavouritesRepository(application, executors)
    }


    fun getFavoriteMovies(): LiveData<List<MovieFavourite>> {
      return   favoritesRepository.getFavoriteMovies()
    }
    fun getFavoriteTvShows(): LiveData<List<TvShowFavourite>> {
        return   favoritesRepository.getFavoriteTvShows()
    }
}