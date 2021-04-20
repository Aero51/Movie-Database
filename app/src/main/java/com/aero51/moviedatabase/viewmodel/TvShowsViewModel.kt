package com.aero51.moviedatabase.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import com.aero51.moviedatabase.repository.TvShowsRepository
import com.aero51.moviedatabase.repository.model.NetworkState
import com.aero51.moviedatabase.repository.model.tmdb.movie.MoviesByGenrePage
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.*
import com.aero51.moviedatabase.utils.AppExecutors
import com.aero51.moviedatabase.utils.Constants
import com.aero51.moviedatabase.utils.Resource
import kotlinx.coroutines.launch

class TvShowsViewModel(application: Application) : AndroidViewModel(application) {
    private val tvShowsRepository: TvShowsRepository

    init {
        val executors = AppExecutors()
        tvShowsRepository = TvShowsRepository(application, executors)
    }

    val tvPopularResultsPagedList: LiveData<PagedList<PopularTvShowsPage.PopularTvShow>>?
        get() = tvShowsRepository.tvPopularResultsPagedList

    //used to get the page number by adding observer
    val popularLiveTvShowPage: LiveData<PopularTvShowsPage>
        get() = tvShowsRepository.lastPopularTvShowPage

    val popularTvShowsNetworkState: LiveData<NetworkState>
        get() = tvShowsRepository.popularNetworkState

    val tvAiringResultsPagedList: LiveData<PagedList<AiringTvShowsPage.AiringTvShow>>?
        get() = tvShowsRepository.tvAiringResultsPagedList

    //used to get the page number by adding observer
    val airingLiveTvShowPage: LiveData<AiringTvShowsPage>
        get() = tvShowsRepository.lastAiringTvShowPage

    val airingTvShowsNetworkState: LiveData<NetworkState>
        get() = tvShowsRepository.airingNetworkState

    val tvTrendingResultsPagedList: LiveData<PagedList<TrendingTvShowsPage.TrendingTvShow>>?
        get() = tvShowsRepository.tvTrendingResultsPagedList

    //used to get the page number by adding observer
    val trendingLiveTvShowPage: LiveData<TrendingTvShowsPage>
        get() = tvShowsRepository.lastTrendingTvShowPage

    val trendingTvShowsNetworkState: LiveData<NetworkState>
        get() = tvShowsRepository.trendingNetworkState

    val tvShowsGenres: LiveData<Resource<List<TvShowGenresResponse.TvShowGenre>>>
        get() = tvShowsRepository.loadTvShowsGenres()

    //used to get the page number by adding observer
    val tvShowsByGenrePage: LiveData<TvShowsByGenrePage>
        get() = tvShowsRepository.lastTvShowsByGenrePage

    fun getTvShowsByGenre(genreId: Int):  LiveData<PagedList<TvShowsByGenrePage.TvShowByGenre>>?{
        return tvShowsRepository.loadTvShowsByGenre(genreId)
    }

    fun  tvShowsByGenreDataValidationCheck(genreId: Int){
        viewModelScope.launch {
            // suspend and resume make this database request main-safe
            // so our ViewModel doesn't need to worry about threading
            tvShowsRepository.checkIfTvShowsByGenreNeedsRefresh(genreId)
        }
    }
    fun  popularShowsDataValidationCheck(){
        viewModelScope.launch {
            // suspend and resume make this database request main-safe
            // so our ViewModel doesn't need to worry about threading
            tvShowsRepository.checkIfPopularTvShowsNeedsRefresh()
        }
    }
    fun  trendingShowsDataValidationCheck(){
        viewModelScope.launch {
            // suspend and resume make this database request main-safe
            // so our ViewModel doesn't need to worry about threading
            tvShowsRepository.checkIfTrendingTvShowsNeedsRefresh()
        }
    }

    fun  airingShowsDataValidationCheck(){
        viewModelScope.launch {
            // suspend and resume make this database request main-safe
            // so our ViewModel doesn't need to worry about threading
            tvShowsRepository.checkIfAiringTvShowsNeedsRefresh()
        }
    }

}