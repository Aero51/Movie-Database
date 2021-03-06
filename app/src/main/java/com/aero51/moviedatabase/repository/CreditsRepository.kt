package com.aero51.moviedatabase.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.aero51.moviedatabase.repository.db.CreditsDao
import com.aero51.moviedatabase.repository.db.Database
import com.aero51.moviedatabase.repository.model.tmdb.credits.Actor
import com.aero51.moviedatabase.repository.model.tmdb.credits.ActorImagesResponse
import com.aero51.moviedatabase.repository.model.tmdb.credits.ActorImagesResponse.ActorImage
import com.aero51.moviedatabase.repository.model.tmdb.credits.MovieCredits
import com.aero51.moviedatabase.repository.model.tmdb.credits.MovieCredits.Cast
import com.aero51.moviedatabase.repository.retrofit.RetrofitInstance
import com.aero51.moviedatabase.utils.*

class CreditsRepository     // databaseCanQueryOnMainThread = MoviesDatabase.getInstanceAllowOnMainThread(application);
(private val application: Application, private val executors: AppExecutors) {
    private val database: Database
    private val creditsDao: CreditsDao

    init {
        database = Database.getInstance(application)
        creditsDao = database._credits_dao
        Log.d(Constants.LOG2, "CreditsRepository constructor!")
    }

    fun loadCastById(movie_id: Int): LiveData<Resource<List<Cast>>> {
        Log.d(Constants.LOG, "loadCastByMovieId id: $movie_id")
        //return CastNetworkBoundResource(executors, application, movie_id).asLiveData()
        return object : NetworkBoundResource<MovieCredits, List<MovieCredits.Cast>>(executors) {

            override fun shouldFetch(data: List<Cast>?): Boolean {
                return data!!.size == 0
            }

            override fun loadFromDb(): LiveData<List<Cast>> {
                return creditsDao.getTitleCast(movie_id)
            }

            override fun createCall(): LiveData<ApiResponse<MovieCredits>> {
                val theMovieDbApi = RetrofitInstance.getTmdbApiService()
                return theMovieDbApi.getLiveMovieCredits(movie_id, Constants.TMDB_API_KEY)
            }

            override fun saveCallResult(item: MovieCredits) {
                //this is executed on background thread
                database.runInTransaction { creditsDao.insertCredits(item) }
                Log.d(Constants.LOG, "saveCallResult movie id: " + item.id + " ,cast size: " + item.cast.size)
            }
        }.asLiveData()
    }

    fun loadActorById(actor_id: Int): LiveData<Resource<Actor>> {
        Log.d(Constants.LOG, "loadActorById id: $actor_id")
        //return ActorNetworkBoundResource(executors, application, actor_id).asLiveData()
        return object : NetworkBoundResource<Actor, Actor>(executors) {

            override fun shouldFetch(data: Actor?): Boolean {
                Log.d(Constants.LOG, "shouldFetch data==null: " + (data == null).toString())
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
                Log.d(Constants.LOG, "saveCallResult actor id: " + item.id)
            }
        }.asLiveData()
    }

    fun loadActorImagesByActorId(actor_id: Int): LiveData<Resource<List<ActorImage>>> {
        Log.d(Constants.LOG, "loadActorImagesByActorId  actor id: $actor_id")
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

                //this is executed on background thread
                database.runInTransaction { creditsDao.insertActorImagesResponse(item) }
                Log.d(Constants.LOG, "saveCallResult actor id: " + item.getId() + " ,images list  size: " + item.getImages().size)
            }
        }.asLiveData()
    }
}