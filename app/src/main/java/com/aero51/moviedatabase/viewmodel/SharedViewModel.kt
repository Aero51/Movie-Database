package com.aero51.moviedatabase.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aero51.moviedatabase.repository.model.epg.ChannelWithPrograms
import com.aero51.moviedatabase.repository.model.epg.EpgProgram
import com.aero51.moviedatabase.repository.model.tmdb.movie.Movie
import com.aero51.moviedatabase.repository.model.tmdb.movie.NowPlayingMoviesPage.NowPlayingMovie
import com.aero51.moviedatabase.repository.model.tmdb.movie.PopularMoviesPage.PopularMovie
import com.aero51.moviedatabase.repository.model.tmdb.movie.TopRatedMoviesPage.TopRatedMovie
import com.aero51.moviedatabase.repository.model.tmdb.movie.UpcomingMoviesPage.UpcomingMovie
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.AiringTvShowsPage
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.PopularTvShowsPage
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.TrendingTvShowsPage
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.TvShow
import com.aero51.moviedatabase.utils.SingleLiveEvent
import com.google.gson.Gson

class SharedViewModel : ViewModel() {
    private val liveEpgProgram = MutableLiveData<EpgProgram>()
    private val shouldSwitchToEpgDetailsFragment = SingleLiveEvent<Boolean>()
    private var epgIndex: Int? = null
    private val liveChannelWithPrograms = MutableLiveData<ChannelWithPrograms>()
    private val shouldSwitchToEpgAllProgramsFragment = SingleLiveEvent<Boolean>()
    private val shouldSwitchOtherChannelDetailFragment = SingleLiveEvent<Boolean>()
    private val otherChannelIndex: Int? = null
    private val liveMovie = MutableLiveData<Movie>()
    private val liveTvShow = MutableLiveData<TvShow>()
    private val shouldSwitchMovieDetailFragment = SingleLiveEvent<Boolean>()
    private val shouldSwitchTvShowDetailFragment = SingleLiveEvent<Boolean>()
    private var movieIndex: Int? = null
    private var tvShowIndex: Int? = null
    private val liveActorId = MutableLiveData<Int>()
    private val shouldSwitchActorFragment = SingleLiveEvent<Boolean>()
    private var castIndex: Int? = null
    private val liveGenreId = MutableLiveData<Int>()
    private val shouldSwitchMoviesByGenreListFragment = SingleLiveEvent<Boolean>()
    private val shouldSwitchMoviesByGenreListFragmentFromMovieDetailsFragment = SingleLiveEvent<Boolean>()
    private val shouldSwitchTvShowsByGenreListFragment = SingleLiveEvent<Boolean>()
    val hasEpgTvFragmentFinishedLoading = MutableLiveData<Boolean>()

    init {
        //used when process is killed and tv shows fragment is selected, otherwise tv shows fragment would be empty on relaunch
        hasEpgTvFragmentFinishedLoading.value = true
    }


    fun changeToEpgDetailsFragment(index: Int?, epgProgram: EpgProgram) {
        epgIndex = index
        liveEpgProgram.value = epgProgram
        shouldSwitchToEpgDetailsFragment.value = true
    }

    val liveDataProgram: LiveData<EpgProgram>
        get() = liveEpgProgram

    val singleLiveShouldSwitchToEpgTvDetailsFragment: LiveData<Boolean>
        get() = shouldSwitchToEpgDetailsFragment

    fun changeToEpgAllProgramsFragment(channelWithPrograms: ChannelWithPrograms) {
        liveChannelWithPrograms.value = channelWithPrograms
        shouldSwitchToEpgAllProgramsFragment.value = true
    }

    val liveDataChannelWithPrograms: LiveData<ChannelWithPrograms>
        get() = liveChannelWithPrograms

    val singleLiveShouldSwitchToEpgAllProgramsFragment: LiveData<Boolean>
        get() = shouldSwitchToEpgAllProgramsFragment

    //done like this to reduce code duplication(fragments, listeners, main activity identifiers
    fun changeToMoviedetailsFragment(movieObject: Any?, position: Int?) {
        var movie = Movie()
        if (movieObject is TopRatedMovie) {
            movie = transformTopRatedMovie(movieObject)
        }
        if (movieObject is NowPlayingMovie) {
            movie = transformNowPlayingMovie(movieObject)
        }
        if (movieObject is PopularMovie) {
            movie = transformPopularMovie(movieObject)
        }
        if (movieObject is UpcomingMovie) {
            movie = transformUpcomingMovie(movieObject)
        }
        movieIndex = position
        liveMovie.value = movie
        shouldSwitchMovieDetailFragment.value = true
    }

    //done like this to reduce code duplication(fragments, listeners, main activity identifiers
    fun changeToTvShowDetailsFragment(tvShowObject: Any?, position: Int?) {
        var tvShow = TvShow()
        if (tvShowObject is PopularTvShowsPage.PopularTvShow) {
            val popularTvShow = tvShowObject as PopularTvShowsPage.PopularTvShow
            tvShow = transformPopularTvShow(popularTvShow)
        }
        if (tvShowObject is TrendingTvShowsPage.TrendingTvShow) {
            val trendingTvShow = tvShowObject as TrendingTvShowsPage.TrendingTvShow
            tvShow = transformTrendingTvShow(trendingTvShow)
        }
        if (tvShowObject is AiringTvShowsPage.AiringTvShow) {
            val airingTvShow = tvShowObject as AiringTvShowsPage.AiringTvShow
            tvShow = transformAiringTvShow(airingTvShow)
        }

        tvShowIndex = position
        liveTvShow.setValue(tvShow)
        shouldSwitchTvShowDetailFragment.setValue(true)
    }

    val liveDataMovie: LiveData<Movie>
        get() = liveMovie

    val liveDataTvShow: LiveData<TvShow>
        get() = liveTvShow

    val singleLiveShouldSwitchMovieDetailsFragment: LiveData<Boolean>
        get() = shouldSwitchMovieDetailFragment

    val singleLiveShouldSwitchTvShowDetailsFragment: LiveData<Boolean>
        get() = shouldSwitchTvShowDetailFragment

    fun changeToActorFragment(position: Int?, actorId: Int) {
        castIndex = position
        liveActorId.value = actorId
        shouldSwitchActorFragment.value = true
    }

    fun changeToMoviesByGenreListFragment(genreId: Int, position: Int) {
        liveGenreId.value = genreId
        shouldSwitchMoviesByGenreListFragment.value = true
    }
    fun changeToMoviesByGenreListFragmentFromMovieDetailsFragment(genreId: Int, position: Int) {
        liveGenreId.value = genreId
        shouldSwitchMoviesByGenreListFragmentFromMovieDetailsFragment.value = true
    }

    fun changeToTvShowsByGenreListFragment(genreId: Int, position: Int?) {
        liveGenreId.value = genreId
        shouldSwitchTvShowsByGenreListFragment.value = true
    }

    val liveDataActorId: LiveData<Int>
        get() = liveActorId

    val singleLiveShouldSwitchActorFragment: LiveData<Boolean>
        get() = shouldSwitchActorFragment

    val liveDataGenreId: LiveData<Int>
        get() = liveGenreId

    val singleLiveShouldSwitchMoviesByGenreListFragment: LiveData<Boolean>
        get() = shouldSwitchMoviesByGenreListFragment

    val singleLiveShouldSwitchMoviesByGenreListFragmentFromMovieDetailsFragment: LiveData<Boolean>
        get() = shouldSwitchMoviesByGenreListFragmentFromMovieDetailsFragment

    val singleLiveShouldSwitchTvShowsByGenreListFragment: LiveData<Boolean>
        get() = shouldSwitchTvShowsByGenreListFragment

    fun setHasEpgTvFragmentFinishedLoading(hasEpgTvFragmentFinishedLoading: Boolean) {
        this.hasEpgTvFragmentFinishedLoading.value = hasEpgTvFragmentFinishedLoading
    }

    fun getHasEpgTvFragmentFinishedLoading(): LiveData<Boolean> {
        return hasEpgTvFragmentFinishedLoading
    }

    //deserialization and serialization
    private fun transformTopRatedMovie(original: TopRatedMovie?): Movie {
        val gson = Gson()
        return gson.fromJson(gson.toJson(original), Movie::class.java)
    }

    private fun transformNowPlayingMovie(original: NowPlayingMovie?): Movie {
        val gson = Gson()
        return gson.fromJson(gson.toJson(original), Movie::class.java)
    }

    private fun transformPopularMovie(original: PopularMovie?): Movie {
        val gson = Gson()
        return gson.fromJson(gson.toJson(original), Movie::class.java)
    }

    private fun transformUpcomingMovie(original: UpcomingMovie?): Movie {
        val gson = Gson()
        return gson.fromJson(gson.toJson(original), Movie::class.java)
    }


    private fun transformPopularTvShow(original: PopularTvShowsPage.PopularTvShow?): TvShow {
        val gson = Gson()
        return gson.fromJson(gson.toJson(original), TvShow::class.java)
    }
    private fun transformTrendingTvShow(original: TrendingTvShowsPage.TrendingTvShow?): TvShow {
        val gson = Gson()
        return gson.fromJson(gson.toJson(original), TvShow::class.java)
    }
    private fun transformAiringTvShow(original: AiringTvShowsPage.AiringTvShow?): TvShow {
        val gson = Gson()
        return gson.fromJson(gson.toJson(original), TvShow::class.java)
    }

}