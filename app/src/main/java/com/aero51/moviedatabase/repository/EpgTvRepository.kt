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
        Log.d(Constants.LOG2, "EpgProgramsForChannelNetworkBoundResource constructor!")
    }


    fun loadProgramsForChannel(channelName: String): LiveData<Resource<List<EpgProgram>>> {
        Log.d(Constants.LOG, "EpgTvRepository load other channel programs: $channelName")
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
                        // Log.d(Constants.LOG2, "currentTime! "+currentTime.getTime());
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
                //Log.d(Constants.LOG,data.get(data.size()-1).getStart());
                return shouldFetch
            }

            override fun loadFromDb(): LiveData<List<EpgProgram>> {
                Log.d(Constants.LOG2, "loadFromDb channel name: $channelName")
                return epgTvDao.getLiveDataProgramsForChannel(channelName)
            }

            override fun createCall(): LiveData<ApiResponse<List<EpgProgram>>> {
                Log.d(Constants.LOG, "EpgTv channel:$channelName ,get programs createCall ")
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
        Log.d(Constants.LOG, "EpgTvRepository loadActorSearch: $actor_name")
        //return ActorSearchNetworkBoundResource(executors, application, actor_name).asLiveData()
        return  object  : NetworkBoundResource<ActorSearchResponse, ActorSearchResponse.ActorSearch>(executors){


            override fun shouldFetch(data: ActorSearch?): Boolean {
                Log.d(Constants.LOG, "shouldFetch data==null: " + (data == null).toString())
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