package com.aero51.moviedatabase.repository

import android.app.Application
import androidx.lifecycle.LiveData

import com.aero51.moviedatabase.repository.db.*
import com.aero51.moviedatabase.repository.model.tmdb.movie.MovieFavourite
import com.aero51.moviedatabase.utils.AppExecutors

class FavouritesRepository(private val application: Application, private val executors: AppExecutors) {

    private val database: Database
    private lateinit var favouritesDao: FavouritesDao


    init {
        database = Database.getInstance(application)
        favouritesDao = database._favourites_dao

    }

    fun getFavoriteMovies(): LiveData<List<MovieFavourite>> {

        return favouritesDao.loadAllFavourites()

    }

}