package com.aero51.moviedatabase.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.aero51.moviedatabase.repository.CreditsRepository
import com.aero51.moviedatabase.repository.OmdbRepository
import com.aero51.moviedatabase.repository.model.omdb.OmdbModel
import com.aero51.moviedatabase.repository.model.tmdb.credits.Actor
import com.aero51.moviedatabase.repository.model.tmdb.credits.ActorImagesResponse.ActorImage
import com.aero51.moviedatabase.repository.model.tmdb.credits.MovieCredits.Cast
import com.aero51.moviedatabase.utils.AppExecutors
import com.aero51.moviedatabase.utils.Constants
import com.aero51.moviedatabase.utils.Resource

class DetailsViewModel(application: Application) : AndroidViewModel(application) {
    private val creditsRepository: CreditsRepository
    private val omdbRepository: OmdbRepository

    init {
        val executors = AppExecutors()
        creditsRepository = CreditsRepository(application, executors)
        omdbRepository = OmdbRepository(application, executors)
    }
    fun getMovieCast(movie_id: Int?): LiveData<Resource<List<Cast>>> {
        return creditsRepository.loadCastById(movie_id!!)
    }

    fun getActorDetails(actor_id: Int): LiveData<Resource<Actor>> {
        Log.d(Constants.LOG, "MovieDetailsViewModel getActorDetails id: $actor_id")
        return creditsRepository.loadActorById(actor_id)
    }

    fun getActorImages(actor_id: Int): LiveData<Resource<List<ActorImage>>> {
        Log.d(Constants.LOG, "MovieDetailsViewModel getActorImages actor id: $actor_id")
        return creditsRepository.loadActorImagesByActorId(actor_id)
    }

    fun getOmbdDetails(title: String): LiveData<Resource<OmdbModel>> {
        //TODO   implement omdb fetching
        return omdbRepository.loadOmdbMovieBytitle(title)
    }


}