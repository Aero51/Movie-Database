package com.aero51.moviedatabase.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.aero51.moviedatabase.repository.CreditsRepository
import com.aero51.moviedatabase.repository.DetailsRepository
import com.aero51.moviedatabase.repository.OmdbRepository
import com.aero51.moviedatabase.repository.model.omdb.OmdbModel
import com.aero51.moviedatabase.repository.model.tmdb.credits.Actor
import com.aero51.moviedatabase.repository.model.tmdb.credits.ActorImagesResponse.ActorImage
import com.aero51.moviedatabase.repository.model.tmdb.credits.MovieCredits.MovieCast
import com.aero51.moviedatabase.repository.model.tmdb.credits.MoviesWithPerson
import com.aero51.moviedatabase.repository.model.tmdb.credits.TvShowCredits
import com.aero51.moviedatabase.repository.model.tmdb.credits.TvShowsWithPerson
import com.aero51.moviedatabase.repository.model.tmdb.movie.*
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.TvShowDetailsResponse
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.TvShowVideoResponse
import com.aero51.moviedatabase.utils.AppExecutors
import com.aero51.moviedatabase.utils.Constants
import com.aero51.moviedatabase.utils.Resource
import com.google.gson.Gson

class DetailsViewModel(application: Application) : AndroidViewModel(application) {
    private val creditsRepository: CreditsRepository
    private val omdbRepository: OmdbRepository
    private val detailsRepository: DetailsRepository

    init {
        val executors = AppExecutors()
        creditsRepository = CreditsRepository(application, executors)
        omdbRepository = OmdbRepository(application, executors)
        detailsRepository= DetailsRepository(application,executors)
    }
    fun getMovieCast(movie_id: Int): LiveData<Resource<List<MovieCast>>> {
        return creditsRepository.loadMovieCastById(movie_id)
    }
    fun getTvShowCast(tv_show_id: Int): LiveData<Resource<List<TvShowCredits.TvShowCast>>> {
        return creditsRepository.loadTvShowCastById(tv_show_id)
    }

    fun getActorDetails(actor_id: Int): LiveData<Resource<Actor>> {
        Log.d(Constants.LOG, "DetailsViewModel getActorDetails id: $actor_id")
        return creditsRepository.loadActorById(actor_id)
    }

    fun getActorImages(actor_id: Int): LiveData<Resource<List<ActorImage>>> {
        Log.d(Constants.LOG, "DetailsViewModel getActorImages actor id: $actor_id")
        return creditsRepository.loadActorImagesByActorId(actor_id)
    }
    fun getMoviesWithPerson(person_id: Int): LiveData<Resource<MoviesWithPerson>> {
        Log.d(Constants.LOG, "DetailsViewModel getMoviesWithPerson person_id: $person_id")
        return creditsRepository.loadMoviesByActorId(person_id)
    }
    fun getTvShowsWithPerson(person_id: Int): LiveData<Resource<TvShowsWithPerson>> {
        Log.d(Constants.LOG, "DetailsViewModel getTvShowsWithPerson person_id: $person_id")
        return creditsRepository.loadTvShowsByActorId(person_id)
    }

    fun getOmbdDetails(title: String): LiveData<Resource<OmdbModel>> {
        return omdbRepository.loadOmdbBytitle(title)
    }

    fun getVideosForMovie(movie_id: Int): LiveData<Resource<List<MovieVideosResponse.MovieVideo>>> {
        return detailsRepository.loadVideosForMovie(movie_id)
    }

    fun getVideosForTvShow(tv_show_id: Int): LiveData<Resource<List<TvShowVideoResponse.TvShowVideo>>> {
        return detailsRepository.loadVideosForTvShow(tv_show_id)
    }

    fun getDetailsForMovie(movie_id: Int): LiveData<Resource<MovieDetailsResponse>> {
        return detailsRepository.loadDetailsForMovie(movie_id)
    }
    fun getDetailsForTvShow(tv_show_id: Int): LiveData<Resource<TvShowDetailsResponse>> {
        return detailsRepository.loadDetailsForTvShow(tv_show_id)
    }





    fun checkIfMovieIsFavourite(movieId: Int) : LiveData<MovieFavourite>{
        return detailsRepository.getMovieFavourites(movieId)
    }

    fun insertFavouriteMovie(movie: MovieDetailsResponse){
        val movieFavourite = transformMovieDetails(movie)
        detailsRepository.insertFavouriteMovie(movieFavourite)
    }
    fun deleteFavouriteMovie(movie: MovieDetailsResponse){
        val movieFavourite = transformMovieDetails(movie)
        detailsRepository.deleteFavouriteMovie(movieFavourite)
    }

    private fun transformMovieDetails(movie: MovieDetailsResponse) :MovieFavourite{
        val gson = Gson()
        return gson.fromJson(gson.toJson(movie), MovieFavourite::class.java)
    }

}