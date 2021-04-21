package com.aero51.moviedatabase.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.aero51.moviedatabase.repository.db.Database
import com.aero51.moviedatabase.repository.db.OmdbDao
import com.aero51.moviedatabase.repository.model.omdb.OmdbModel
import com.aero51.moviedatabase.repository.retrofit.RetrofitInstance
import com.aero51.moviedatabase.utils.*

class OmdbRepository(
        private val application: Application, private val executors: AppExecutors) {
    private val database: Database
    private val omdbDao: OmdbDao

    init {
        database = Database.getInstance(application)
        omdbDao = database._omdb_dao
        Log.d(Constants.LOG2, "OmdbRepository constructor!")
    }


    fun loadOmdbMovieBytitle(movie_title: String): LiveData<Resource<OmdbModel>> {
        Log.d(Constants.LOG, "loadOmdbMovieBytitle title: $movie_title")
        //return ActorNetworkBoundResource(executors, application, actor_id).asLiveData()
        return object : NetworkBoundResource<OmdbModel, OmdbModel>(executors) {

            override fun shouldFetch(data: OmdbModel?): Boolean {
                Log.d(Constants.LOG, "shouldFetch data==null: " + (data == null).toString())
                return data == null
            }

            override fun loadFromDb(): LiveData<OmdbModel> {
                return omdbDao.getLiveOmdbMovieByTitle(movie_title)
            }

            override fun createCall(): LiveData<ApiResponse<OmdbModel>> {
                val omdbApi = RetrofitInstance.getOmdbApiService()
                return omdbApi.getLiveMovieDetails(Constants.OMDB_API_KEY,movie_title)
            }

            override fun saveCallResult(item: OmdbModel) {
                //this is executed on background thread
                omdbDao.insertOmdbMovieDetails(item)
                Log.d(Constants.LOG, "saveCallResult movie title: " +item.title)
            }
        }.asLiveData()
    }


}