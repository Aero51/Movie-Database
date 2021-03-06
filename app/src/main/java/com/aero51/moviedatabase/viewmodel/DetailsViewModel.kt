package com.aero51.moviedatabase.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.aero51.moviedatabase.repository.CreditsRepository
import com.aero51.moviedatabase.repository.DetailsRepository
import com.aero51.moviedatabase.repository.OmdbRepository
import com.aero51.moviedatabase.repository.model.omdb.OmdbModel
import com.aero51.moviedatabase.repository.model.tmdb.credits.*
import com.aero51.moviedatabase.repository.model.tmdb.credits.ActorImagesResponse.ActorImage
import com.aero51.moviedatabase.repository.model.tmdb.credits.MovieCredits.MovieCast
import com.aero51.moviedatabase.repository.model.tmdb.movie.*
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.TvShowDetailsResponse
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.TvShowFavourite
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.TvShowVideoResponse
import com.aero51.moviedatabase.utils.AppExecutors
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
    fun getMovieCrew(movie_id: Int): LiveData<Resource<List<MovieCredits.MovieCrew>>> {
        return creditsRepository.loadMovieCrewById(movie_id)
    }
    fun getTvShowCast(tv_show_id: Int): LiveData<Resource<List<TvShowCredits.TvShowCast>>> {
        return creditsRepository.loadTvShowCastById(tv_show_id)
    }

    fun getActorDetails(actor_id: Int): LiveData<Resource<Actor>> {
        return creditsRepository.loadActorById(actor_id)
    }

    fun getActorImages(actor_id: Int): LiveData<Resource<List<ActorImage>>> {
        return creditsRepository.loadActorImagesByActorId(actor_id)
    }
    fun getMoviesWithPerson(person_id: Int): LiveData<Resource<MoviesWithPerson>> {
        return creditsRepository.loadMoviesByActorId(person_id)
    }
    fun getTvShowsWithPerson(person_id: Int): LiveData<Resource<TvShowWithPerson>> {
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


    fun checkIfTvShowIsFavourite(tvShowId: Int) : LiveData<TvShowFavourite>{
        return detailsRepository.getTvShowFavourites(tvShowId)
    }

    fun insertFavouriteTvShow(tvShow: TvShowDetailsResponse){
        val tvShowFavourite = transformTvShowDetails(tvShow)
        detailsRepository.insertFavouriteTvShow(tvShowFavourite)
    }
    fun deleteFavouriteTvShow(tvShow: TvShowDetailsResponse){
        val tvShowFavourite = transformTvShowDetails(tvShow)
        detailsRepository.deleteFavouriteTvShow(tvShowFavourite)
    }

    private fun transformMovieDetails(movie: MovieDetailsResponse) :MovieFavourite{
        val gson = Gson()
        return gson.fromJson(gson.toJson(movie), MovieFavourite::class.java)
    }
    private fun transformTvShowDetails(tvShow: TvShowDetailsResponse) : TvShowFavourite {
        val gson = Gson()
        return gson.fromJson(gson.toJson(tvShow), TvShowFavourite::class.java)
    }

}