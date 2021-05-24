package com.aero51.moviedatabase.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.aero51.moviedatabase.repository.db.CreditsDao
import com.aero51.moviedatabase.repository.db.Database
import com.aero51.moviedatabase.repository.model.tmdb.credits.*
import com.aero51.moviedatabase.repository.model.tmdb.credits.ActorImagesResponse.ActorImage
import com.aero51.moviedatabase.repository.model.tmdb.credits.MovieCredits.MovieCast
import com.aero51.moviedatabase.repository.retrofit.RetrofitInstance
import com.aero51.moviedatabase.utils.*

class CreditsRepository     // databaseCanQueryOnMainThread = MoviesDatabase.getInstanceAllowOnMainThread(application);
(private val application: Application, private val executors: AppExecutors) {
    private val database: Database
    private val creditsDao: CreditsDao

    init {
        database = Database.getInstance(application)
        creditsDao = database._credits_dao
    }

    fun loadMovieCastById(movie_id: Int): LiveData<Resource<List<MovieCast>>> {
        //return CastNetworkBoundResource(executors, application, movie_id).asLiveData()
        return object : NetworkBoundResource<MovieCredits, List<MovieCast>>(executors) {

            override fun shouldFetch(data: List<MovieCast>?): Boolean {
                return data!!.size == 0
            }

            override fun loadFromDb(): LiveData<List<MovieCast>> {
                return creditsDao.getMovieCast(movie_id)
            }

            override fun createCall(): LiveData<ApiResponse<MovieCredits>> {
                val theMovieDbApi = RetrofitInstance.getTmdbApiService()
                return theMovieDbApi.getLiveMovieCredits(movie_id, Constants.TMDB_API_KEY)
            }

            override fun saveCallResult(item: MovieCredits) {
                //this is executed on background thread
                database.runInTransaction { creditsDao.insertMovCredits(item) }
            }
        }.asLiveData()
    }
    fun loadMovieCrewById(movie_id: Int): LiveData<Resource<List<MovieCredits.MovieCrew>>> {

        //return CastNetworkBoundResource(executors, application, movie_id).asLiveData()
        return object : NetworkBoundResource<MovieCredits, List<MovieCredits.MovieCrew>>(executors) {

            override fun shouldFetch(data: List<MovieCredits.MovieCrew>?): Boolean {
                return data!!.size == 0
            }

            override fun loadFromDb(): LiveData<List<MovieCredits.MovieCrew>> {
                return creditsDao.getMovieCrew(movie_id)
            }

            override fun createCall(): LiveData<ApiResponse<MovieCredits>> {
                val theMovieDbApi = RetrofitInstance.getTmdbApiService()
                return theMovieDbApi.getLiveMovieCredits(movie_id, Constants.TMDB_API_KEY)
            }

            override fun saveCallResult(item: MovieCredits) {
                //this is executed on background thread
                //Not saving to db because loadMovieCastById() performs that
            }
        }.asLiveData()
    }

    fun loadTvShowCastById(tv_show_id: Int): LiveData<Resource<List<TvShowCredits.TvShowCast>>> {
        //return CastNetworkBoundResource(executors, application, movie_id).asLiveData()
        return object : NetworkBoundResource<TvShowCredits, List<TvShowCredits.TvShowCast>>(executors) {

            override fun shouldFetch(data: List<TvShowCredits.TvShowCast>?): Boolean {
                return data!!.isEmpty()
            }

            override fun loadFromDb(): LiveData<List<TvShowCredits.TvShowCast>> {
                return creditsDao.getTvShowCast(tv_show_id)
            }

            override fun createCall(): LiveData<ApiResponse<TvShowCredits>> {
                val theMovieDbApi = RetrofitInstance.getTmdbApiService()
                return theMovieDbApi.getLiveTvShowCredits(tv_show_id, Constants.TMDB_API_KEY)
            }

            override fun saveCallResult(item: TvShowCredits) {
                //this is executed on background thread
                database.runInTransaction { creditsDao.insertTvCredits(item) }
            }
        }.asLiveData()
    }

    fun loadActorById(actor_id: Int): LiveData<Resource<Actor>> {
        //return ActorNetworkBoundResource(executors, application, actor_id).asLiveData()
        return object : NetworkBoundResource<Actor, Actor>(executors) {

            override fun shouldFetch(data: Actor?): Boolean {
                return data == null
            }

            override fun loadFromDb(): LiveData<Actor> {
                return creditsDao.getActor(actor_id)
            }

            override fun createCall(): LiveData<ApiResponse<Actor>> {
                val theMovieDbApi = RetrofitInstance.getTmdbApiService()
                return theMovieDbApi.getLivePerson(actor_id, Constants.TMDB_API_KEY)
            }

            override fun saveCallResult(item: Actor) {
                //this is executed on background thread
                creditsDao.insertActor(item)
            }
        }.asLiveData()
    }

    fun loadActorImagesByActorId(actor_id: Int): LiveData<Resource<List<ActorImage>>> {
        //return ActorImagesNetworkBoundResource(executors, application, actor_id).asLiveData()
        return object : NetworkBoundResource<ActorImagesResponse, List<ActorImagesResponse.ActorImage>>(executors) {

            override fun shouldFetch(data: List<ActorImage>?): Boolean {
                return data!!.size == 0
            }

            override fun loadFromDb(): LiveData<List<ActorImage>> {
                return creditsDao.getActorImages(actor_id)
            }

            override fun createCall(): LiveData<ApiResponse<ActorImagesResponse>> {
                val theMovieDbApi = RetrofitInstance.getTmdbApiService()
                return theMovieDbApi.getLivePersonImages(actor_id, Constants.TMDB_API_KEY)
            }

            override fun saveCallResult(item: ActorImagesResponse) {
                //this is executed on background thread
                database.runInTransaction { creditsDao.insertActorImagesResponse(item) }
            }
        }.asLiveData()
    }

    fun loadMoviesByActorId(actor_id: Int): LiveData<Resource<MoviesWithPerson>> {
        return object : NetworkBoundResource<MoviesWithPerson, MoviesWithPerson>(executors) {

            override fun shouldFetch(data: MoviesWithPerson?): Boolean {
                return data == null
            }

            override fun loadFromDb(): LiveData<MoviesWithPerson> {
                return creditsDao.getMoviesWithPerson(actor_id)
            }

            override fun createCall(): LiveData<ApiResponse<MoviesWithPerson>> {
                val theMovieDbApi = RetrofitInstance.getTmdbApiService()
                return theMovieDbApi.getLiveMoviesWithPerson(actor_id, Constants.TMDB_API_KEY)
            }

            override fun saveCallResult(item: MoviesWithPerson) {
                //this is executed on background thread
                database.runInTransaction { creditsDao.insertMoviesWithPerson(item) }
            }
        }.asLiveData()
    }
    fun loadTvShowsByActorId(actor_id: Int): LiveData<Resource<TvShowsWithPerson>> {
        return object : NetworkBoundResource<TvShowsWithPerson, TvShowsWithPerson>(executors) {

            override fun shouldFetch(data: TvShowsWithPerson?): Boolean {
                return data == null
            }

            override fun loadFromDb(): LiveData<TvShowsWithPerson> {
                return creditsDao.getTvShowsWithPerson(actor_id)
            }

            override fun createCall(): LiveData<ApiResponse<TvShowsWithPerson>> {
                val theMovieDbApi = RetrofitInstance.getTmdbApiService()
                return theMovieDbApi.getLiveTvShowsWithPerson(actor_id, Constants.TMDB_API_KEY)
            }

            override fun saveCallResult(item: TvShowsWithPerson) {
                //this is executed on background thread
                database.runInTransaction { creditsDao.insertTvShowsWithPerson(item) }
            }
        }.asLiveData()
    }
}