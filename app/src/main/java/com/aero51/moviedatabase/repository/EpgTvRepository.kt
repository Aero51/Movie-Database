package com.aero51.moviedatabase.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.aero51.moviedatabase.repository.db.CreditsDao
import com.aero51.moviedatabase.repository.db.Database
import com.aero51.moviedatabase.repository.db.EpgTvDao
import com.aero51.moviedatabase.repository.model.epg.EpgProgram
import com.aero51.moviedatabase.repository.model.tmdb.credits.ActorSearchResponse
import com.aero51.moviedatabase.repository.model.tmdb.credits.ActorSearchResponse.ActorSearch
import com.aero51.moviedatabase.repository.retrofit.RetrofitInstance
import com.aero51.moviedatabase.utils.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class EpgTvRepository(private val application: Application, private val executors: AppExecutors) {
    private val database: Database
    private val epgTvDao: EpgTvDao
    private val creditsDao: CreditsDao

    init {
        database = Database.getInstance(application)
        epgTvDao = database._epg_tv_dao
        creditsDao = database._credits_dao
    }


    fun loadProgramsForChannel(channelName: String): LiveData<Resource<List<EpgProgram>>> {
        //return EpgProgramsForChannelNetworkBoundResource(executors, application, channelName).asLiveData()
        return  object  : NetworkBoundResource<List<EpgProgram>, List<EpgProgram>>(executors){


            override fun shouldFetch(data: List<EpgProgram>?): Boolean {
                val noData = data!!.size == 0
                var shouldFetch = false
                var isGreater = false
                if (!noData) {
                    var currentTime: Date? = null
                    var programStartTime: Date? = null
                    try {
                        currentTime = Calendar.getInstance().time
                        programStartTime = SimpleDateFormat("yyyyMMddHHmmSS").parse(data[data.size - 1].start)
                    } catch (e: ParseException) {
                        e.printStackTrace()
                    }
                    //adding 5 hours == 18000000 ms
                    if (programStartTime!!.time > currentTime!!.time + 18000000) {
                        isGreater = true
                    }
                }
                if (noData == true || isGreater == false) {
                    shouldFetch = true
                }
                return shouldFetch
            }

            override fun loadFromDb(): LiveData<List<EpgProgram>> {
                return epgTvDao.getLiveDataProgramsForChannel(channelName)
            }

            override fun createCall(): LiveData<ApiResponse<List<EpgProgram>>> {
                val epgApi = RetrofitInstance.getEpgApiService()
                return epgApi.getLiveProgramsForChannel(channelName)
            }

            override fun saveCallResult(item: List<EpgProgram>) {
                epgTvDao.deleteProgramsForChannel(channelName)
                epgTvDao.insertProgramsList(item)
            }
        }.asLiveData()
    }

    fun loadActorSearch(actor_name: String): LiveData<Resource<ActorSearch>> {
        //return ActorSearchNetworkBoundResource(executors, application, actor_name).asLiveData()
        return  object  : NetworkBoundResource<ActorSearchResponse, ActorSearchResponse.ActorSearch>(executors){


            override fun shouldFetch(data: ActorSearch?): Boolean {
                return data == null
            }

            override fun loadFromDb(): LiveData<ActorSearch> {
                return creditsDao.getActorSearch(actor_name)
            }

            override fun createCall(): LiveData<ApiResponse<ActorSearchResponse>> {
                val theMovieDbApi = RetrofitInstance.getTmdbApiService()
                return theMovieDbApi.getLivePersonSearch(Constants.TMDB_API_KEY, actor_name)
            }
            override fun saveCallResult(item: ActorSearchResponse) {
                if (item.results.size > 0) {
                    database.runInTransaction { creditsDao.insertActorSearch(item.results[0]) }
                }
            }
        }.asLiveData()
    }
}