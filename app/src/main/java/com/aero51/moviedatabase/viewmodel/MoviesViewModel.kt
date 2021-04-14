package com.aero51.moviedatabase.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.aero51.moviedatabase.repository.MoviesRepository
import com.aero51.moviedatabase.repository.model.NetworkState
import com.aero51.moviedatabase.repository.model.tmdb.movie.*
import com.aero51.moviedatabase.utils.AppExecutors
import com.aero51.moviedatabase.utils.Constants
import com.aero51.moviedatabase.utils.Resource

class MoviesViewModel(application: Application) : AndroidViewModel(application) {
    private val moviesRepository: MoviesRepository
    val topRatedResultsPagedList: LiveData<PagedList<TopRatedMoviesPage.TopRatedMovie>>?
        get() = moviesRepository.topRatedResultsPagedList

    //used to get the page number by adding observer
    val topRatedLiveMoviePage: LiveData<TopRatedMoviesPage>
        get() = moviesRepository.lastTopRatedMoviePage

    val topRatedMoviesNetworkState: LiveData<NetworkState>
        get() = moviesRepository.topRatedNetworkState

    val popularResultsPagedList: LiveData<PagedList<PopularMoviesPage.PopularMovie>>?
        get() = moviesRepository.popularResultsPagedList

    //used to get the page number by adding observer
    val popularLiveMoviePage: LiveData<PopularMoviesPage>
        get() = moviesRepository.lastPopularMoviePage

    val popularMoviesNetworkState: LiveData<NetworkState>
        get() = moviesRepository.popularNetworkState

    val nowPlayingResultsPagedList: LiveData<PagedList<NowPlayingMoviesPage.NowPlayingMovie>>?
        get() = moviesRepository.nowPlayingResultsPagedList

    //used to get the page number by adding observer
    val nowPlayingLiveMoviePage: LiveData<NowPlayingMoviesPage>
        get() = moviesRepository.lastNowPlayingMoviePage

    val nowPlayingMoviesNetworkState: LiveData<NetworkState>
        get() = moviesRepository.nowPlayingNetworkState

    val upcomingResultsPagedList: LiveData<PagedList<UpcomingMoviesPage.UpcomingMovie>>?
        get() = moviesRepository.upcomingResultsPagedList

    //used to get the page number by adding observer
    val upcomingLiveMoviePage: LiveData<UpcomingMoviesPage>
        get() = moviesRepository.lastUpcomingMoviePage

    val upcomingMoviesNetworkState: LiveData<NetworkState>
        get() = moviesRepository.upcomingNetworkState

    val moviesGenres: LiveData<Resource<List<MovieGenresResponse.MovieGenre>>>
        get() = moviesRepository.loadMoviesGenres()

    val genreList: LiveData<Resource<List<MovieGenresResponse.MovieGenre>>>
        get() = moviesRepository.loadMoviesGenres()




    override fun onCleared() {
        Log.d(Constants.LOG, "view model on cleared ")
        // repository.getMoviePageLd().removeObserver(repository.getObserver());
        super.onCleared()
    }

    init {
        val executors = AppExecutors()
        moviesRepository = MoviesRepository(application, executors)
    }
}