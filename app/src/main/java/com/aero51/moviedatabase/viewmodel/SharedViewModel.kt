package com.aero51.moviedatabase.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aero51.moviedatabase.repository.model.epg.ChannelWithPrograms
import com.aero51.moviedatabase.repository.model.epg.EpgProgram
import com.aero51.moviedatabase.repository.model.tmdb.credits.MoviesWithPerson
import com.aero51.moviedatabase.repository.model.tmdb.credits.TvShowWithPerson
import com.aero51.moviedatabase.repository.model.tmdb.movie.Movie
import com.aero51.moviedatabase.repository.model.tmdb.movie.MovieFavourite
import com.aero51.moviedatabase.repository.model.tmdb.movie.MoviesByGenrePage
import com.aero51.moviedatabase.repository.model.tmdb.movie.NowPlayingMoviesPage.NowPlayingMovie
import com.aero51.moviedatabase.repository.model.tmdb.movie.PopularMoviesPage.PopularMovie
import com.aero51.moviedatabase.repository.model.tmdb.movie.TopRatedMoviesPage.TopRatedMovie
import com.aero51.moviedatabase.repository.model.tmdb.movie.UpcomingMoviesPage.UpcomingMovie
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.*
import com.aero51.moviedatabase.utils.SingleLiveEvent
import com.google.gson.Gson

class SharedViewModel : ViewModel() {
    private val liveEpgProgram = MutableLiveData<EpgProgram>()
    private val shouldSwitchToEpgDetailsFragment = SingleLiveEvent<Boolean>()
    private val shouldSwitchToEpgDetailsFragmentFromEpgAllProgramsFragment = SingleLiveEvent<Boolean>()
    private val liveChannelWithPrograms = MutableLiveData<ChannelWithPrograms>()
    private val shouldSwitchToEpgAllProgramsFragment = SingleLiveEvent<Boolean>()
    private val liveMovie = MutableLiveData<Movie>()
    private val liveTvShow = MutableLiveData<TvShow>()
    private val liveMovieFromGenreOrActor = MutableLiveData<Movie>()
    private val liveTvShowFromGenreOrActor = MutableLiveData<TvShow>()
    private val liveEpgMovie = MutableLiveData<Movie>()
    private val livePersonSearchMovie = MutableLiveData<Movie>()
    private val livePersonSearchTvShow = MutableLiveData<TvShow>()
    private val liveFavoriteMovie = MutableLiveData<Movie>()
    private val liveFavoriteTvShow = MutableLiveData<TvShow>()
    private val liveEpgTvShow = MutableLiveData<TvShow>()
    private val liveFavoriteMovieFromGenreOrActor = MutableLiveData<Movie>()
    private val liveFavoriteTvShowFromGenreOrActor = MutableLiveData<TvShow>()
    private val shouldSwitchMovieDetailFragment = SingleLiveEvent<Boolean>()
    private val shouldSwitchTvShowDetailFragment = SingleLiveEvent<Boolean>()
    private val shouldSwitchMovieDetailFragmentFromMovieActorFragment = SingleLiveEvent<Boolean>()
    private val shouldSwitchMovieDetailFragmentFromMoviesByGenreListFragment = SingleLiveEvent<Boolean>()
    private val shouldSwitchTvShowDetailFragmentFromTvShowsByGenreListFragment = SingleLiveEvent<Boolean>()
    private val shouldSwitchTvShowDetailFragmentFromTvShowActorFragment = SingleLiveEvent<Boolean>()
    private val shouldSwitchFavoriteMovieDetailFragmentFromMovieActorFragment = SingleLiveEvent<Boolean>()
    private val shouldSwitchFavoriteTvShowDetailFragmentFromTvShowActorFragment = SingleLiveEvent<Boolean>()
    private val shouldSwitchEpgMovieDetailFragment = SingleLiveEvent<Boolean>()
    private val shouldSwitchEpgTvShowDetailFragment = SingleLiveEvent<Boolean>()
    private val shouldSwitchFavoriteMovieDetailsFragment = SingleLiveEvent<Boolean>()
    private val shouldSwitchFavoriteTvShowDetailsFragment = SingleLiveEvent<Boolean>()
    private val shouldSwitchSearchMovieDetailFragment = SingleLiveEvent<Boolean>()
    private val shouldSwitchSearchTvShowDetailFragment = SingleLiveEvent<Boolean>()
    private val liveEpgActorId = MutableLiveData<Int>()
    private val liveMovieActorId = MutableLiveData<Int>()
    private val liveTvShowActorId = MutableLiveData<Int>()
    private val liveFavoriteMovieActorId = MutableLiveData<Int>()
    private val liveFavoriteTvShowActorId = MutableLiveData<Int>()
    private val liveMovieAndTvShowActorSearchId = MutableLiveData<Int>()
    private val shouldSwitchEpgActorFragment = SingleLiveEvent<Boolean>()
    private val shouldSwitchMovieActorFragment = SingleLiveEvent<Boolean>()
    private val shouldSwitchTvActorFragment = SingleLiveEvent<Boolean>()
    private val shouldSwitchFavoriteMovieActorFragment = SingleLiveEvent<Boolean>()
    private val shouldSwitchFavoriteTvActorFragment = SingleLiveEvent<Boolean>()
    private val shouldSwitchMovieAndTvShowActorFragment = SingleLiveEvent<Boolean>()
    private val liveMovieGenreId = MutableLiveData<Int>()
    private val liveTvShowGenreId = MutableLiveData<Int>()
    private val liveEpgGenreId = MutableLiveData<Int>()
    private val liveFavoriteMovieGenreId = MutableLiveData<Int>()
    private val liveFavoriteTvShowGenreId = MutableLiveData<Int>()
    private val shouldSwitchMoviesByGenreListFragment = SingleLiveEvent<Boolean>()
    private val shouldSwitchMoviesByGenreListFragmentFromMovieDetailsFragment = SingleLiveEvent<Boolean>()
    private val shouldSwitchTvShowsByGenreListFragmentFromTvShowDetailsFragment = SingleLiveEvent<Boolean>()
    private val shouldSwitchFavoriteMovieDetailsFragmentFromMovieByGenreFragment = SingleLiveEvent<Boolean>()
    private val shouldSwitchFavoriteTvShowDetailsFragmentFromTvShowByGenreFragment = SingleLiveEvent<Boolean>()
    private val shouldSwitchTvShowsByGenreListFragment = SingleLiveEvent<Boolean>()
    private val shouldSwitchEpgMoviesByGenreListFragment = SingleLiveEvent<Boolean>()
    private val shouldSwitchEpgTvShowsByGenreListFragment = SingleLiveEvent<Boolean>()
    private val shouldSwitchFavoriteMoviesByGenreListFragment = SingleLiveEvent<Boolean>()
    private val shouldSwitchFavoriteTvShowsByGenreListFragment = SingleLiveEvent<Boolean>()
    val hasEpgTvFragmentFinishedLoading = MutableLiveData<Boolean>()

    init {
        //used when process is killed and tv shows fragment is selected, otherwise tv shows fragment would be empty on relaunch
        hasEpgTvFragmentFinishedLoading.value = true
    }


    fun changeToEpgDetailsFragment( epgProgram: EpgProgram) {
        liveEpgProgram.value = epgProgram
        shouldSwitchToEpgDetailsFragment.value = true
    }
    val liveDataProgram: LiveData<EpgProgram>
        get() = liveEpgProgram

    val singleLiveShouldSwitchToEpgTvDetailsFragment: LiveData<Boolean>
        get() = shouldSwitchToEpgDetailsFragment


    fun changeToEpgDetailsFragmentFromEpgAllProgramsFragment( epgProgram: EpgProgram) {
        liveEpgProgram.value = epgProgram
        shouldSwitchToEpgDetailsFragmentFromEpgAllProgramsFragment.value = true
    }
    val singleLiveShouldSwitchToEpgTvDetailsFragmentFromEpgAllProgramsFragment: LiveData<Boolean>
        get() = shouldSwitchToEpgDetailsFragmentFromEpgAllProgramsFragment


    fun changeToEpgAllProgramsFragment(channelWithPrograms: ChannelWithPrograms) {
        liveChannelWithPrograms.value = channelWithPrograms
        shouldSwitchToEpgAllProgramsFragment.value = true
    }

    val liveDataChannelWithPrograms: LiveData<ChannelWithPrograms>
        get() = liveChannelWithPrograms

    val singleLiveShouldSwitchToEpgAllProgramsFragment: LiveData<Boolean>
        get() = shouldSwitchToEpgAllProgramsFragment

    //done like this to reduce code duplication(fragments, listeners, main activity identifiers
    fun changeToMoviedetailsFragment(movieObject: Any?) {
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
        if (movieObject is MoviesByGenrePage.MovieByGenre) {
            movie = transformMovieByGenre(movieObject)
        }
        if (movieObject is MoviesWithPerson.Cast) {
            movie = transformMovieWithPersonCast(movieObject)
        }
        liveMovie.value = movie
        shouldSwitchMovieDetailFragment.value = true
    }
    fun changeToMoviedetailsFragmentFromMovieActorFragment(movieObject: Any?) {
        var movie = Movie()

        if (movieObject is MoviesWithPerson.Cast) {
            movie = transformMovieWithPersonCast(movieObject)
        }
        liveMovieFromGenreOrActor.value = movie
        shouldSwitchMovieDetailFragmentFromMovieActorFragment.value = true
    }
    fun changeToMovieDetailsFragmentFromMoviesByGenreListFragment(movieObject: Any?) {
        var movie = Movie()
        if (movieObject is MoviesByGenrePage.MovieByGenre) {
            movie = transformMovieByGenre(movieObject)
        }
        liveMovieFromGenreOrActor.value=movie
        shouldSwitchMovieDetailFragmentFromMoviesByGenreListFragment.value = true
    }



    fun changeToFavoriteMovieDetailsFragmentFromMovieActorFragment(movieObject: Any?) {
        var movie = Movie()

        if (movieObject is MoviesWithPerson.Cast) {
            movie = transformMovieWithPersonCast(movieObject)
        }
        liveFavoriteMovieFromGenreOrActor.value = movie
        shouldSwitchFavoriteMovieDetailFragmentFromMovieActorFragment.value = true
    }
    fun changeToEpgMoviedetailsFragment(movieObject: Any?) {
        var movie = Movie()

        if (movieObject is MoviesWithPerson.Cast) {
            movie = transformMovieWithPersonCast(movieObject)
        }
        liveEpgMovie.value = movie
        shouldSwitchEpgMovieDetailFragment.value = true
    }

    fun changeToTvShowDetailsFragmentFromTvShowByGenreListFragment(tvShowObject: Any?) {
        var tv_show = TvShow()
        if (tvShowObject is TvShowsByGenrePage.TvShowByGenre) {
            tv_show = transformTvShowByGenre(tvShowObject)
        }
        liveTvShowFromGenreOrActor.value=tv_show
        shouldSwitchTvShowDetailFragmentFromTvShowsByGenreListFragment.value = true
    }


    fun changeToTvShowDetailsFragmentFromTvShowActorFragment(tvShowObject: Any?) {
        var tv_show = TvShow()

        if (tvShowObject is TvShowWithPerson.Cast) {
            tv_show = transformTvShowWithPersonCast(tvShowObject)
        }
        liveTvShowFromGenreOrActor.value = tv_show
        shouldSwitchTvShowDetailFragmentFromTvShowActorFragment.value = true

    }

    //done like this to reduce code duplication(fragments, listeners, main activity identifiers
    fun changeToTvShowDetailsFragment(tvShowObject: Any?) {
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
        if (tvShowObject is TvShowsByGenrePage.TvShowByGenre) {
            tvShow = transformTvShowByGenre(tvShowObject)
        }
        if (tvShowObject is TvShowSearchResult.TvShow) {
            tvShow = transformTvShowSearchResult(tvShowObject)
        }

        liveTvShow.setValue(tvShow)
        shouldSwitchTvShowDetailFragment.setValue(true)
    }

    fun changeToEpgTvShowDetailsFragment(tvShowObject: Any?) {
        var tvShow = TvShow()
        if (tvShowObject is TvShowWithPerson.Cast) {
            val tvShowWithePersonCast = tvShowObject as TvShowWithPerson.Cast
            tvShow = transformTvShowWithPersonCast(tvShowWithePersonCast)
        }

        liveEpgTvShow.setValue(tvShow)
        shouldSwitchEpgTvShowDetailFragment.setValue(true)
    }

    fun changeToFavouriteMovieDetailsFragmentFromMoviesByGenreListFragment(movieObject: Any?) {
        var movie = Movie()
        if (movieObject is MoviesByGenrePage.MovieByGenre) {
            movie = transformMovieByGenre(movieObject)
        }
        liveFavoriteMovieFromGenreOrActor.value=movie
        shouldSwitchFavoriteMovieDetailsFragmentFromMovieByGenreFragment.value = true
    }
    fun changeToFavouriteTvShowDetailsFragmentFromTvShowsByGenreListFragment(tvObject: Any?) {
        var tvShow = TvShow()
        if (tvObject is   TvShowsByGenrePage.TvShowByGenre) {
            tvShow = transformTvShowByGenre(tvObject)
        }
        liveFavoriteTvShowFromGenreOrActor.value=tvShow
        shouldSwitchFavoriteTvShowDetailsFragmentFromTvShowByGenreFragment.value = true
    }
    fun changeToFavoriteTvShowDetailsFragmentFromTvShowActorFragment(tvObject: Any?) {
        var tvShow = TvShow()

        if (tvObject is TvShowWithPerson.Cast) {
            tvShow = transformTvShowWithPersonCast(tvObject)
        }
        liveFavoriteTvShowFromGenreOrActor.value = tvShow
        shouldSwitchFavoriteTvShowDetailFragmentFromTvShowActorFragment.value = true
    }


    fun changeToFavouriteMovieDetailsFragment(movieFavourite: MovieFavourite) {

        liveFavoriteMovie.value = transformMovieFavorite(movieFavourite)
        shouldSwitchFavoriteMovieDetailsFragment.value = true
    }
    fun changeToFavouriteTvShowDetailsFragment(tvShowFavourite: TvShowFavourite) {
        liveFavoriteTvShow.value=transformTvShowFavorite(tvShowFavourite)
        shouldSwitchFavoriteTvShowDetailsFragment.value = true
    }


    val liveDataMovie: LiveData<Movie>
        get() = liveMovie


    val liveDataTvShow: LiveData<TvShow>
        get() = liveTvShow

    val liveDataEpgMovie: LiveData<Movie>
        get() = liveEpgMovie

    val liveDataFavoriteMovie: LiveData<Movie>
        get() = liveFavoriteMovie

    val liveDataFavoriteTvShow: LiveData<TvShow>
        get() = liveFavoriteTvShow

    val liveDataEpgTvShow: LiveData<TvShow>
        get() = liveEpgTvShow

    val liveDataFavoriteMovieFromGenreOrActor: LiveData<Movie>
        get() = liveFavoriteMovieFromGenreOrActor


    val liveDataFavoriteTvShowFromGenreOrActor: LiveData<TvShow>
        get() = liveFavoriteTvShowFromGenreOrActor


    val liveDataMovieFromGenreOrActor: LiveData<Movie>
        get() = liveMovieFromGenreOrActor

    val liveDataTvShowFromGenreOrActor: LiveData<TvShow>
        get() = liveTvShowFromGenreOrActor


    val singleLiveShouldSwitchMovieDetailsFragment: LiveData<Boolean>
        get() = shouldSwitchMovieDetailFragment

    val singleLiveShouldSwitchMovieDetailsFragmentFromMovieActorFragment: LiveData<Boolean>
        get() = shouldSwitchMovieDetailFragmentFromMovieActorFragment

    val singleLiveShouldSwitchMovieDetailFragmentFromMoviesByGenreListFragment: LiveData<Boolean>
        get() = shouldSwitchMovieDetailFragmentFromMoviesByGenreListFragment


    val singleLiveShouldSwitchTvShowDetailFragmentFromTvShowsByGenreListFragment: LiveData<Boolean>
        get() = shouldSwitchTvShowDetailFragmentFromTvShowsByGenreListFragment

    val singleLiveShouldSwitchTvShowDetailsFragmentFromTvShowActorFragment: LiveData<Boolean>
        get() = shouldSwitchTvShowDetailFragmentFromTvShowActorFragment


    val singleLiveShouldSwitchFavoriteMovieDetailsFragmentFromMovieByGenreFragment: LiveData<Boolean>
        get() = shouldSwitchFavoriteMovieDetailsFragmentFromMovieByGenreFragment

    val singleLiveShouldSwitchFavoriteMovieDetailFragmentFromMovieActorFragment: LiveData<Boolean>
        get() = shouldSwitchFavoriteMovieDetailFragmentFromMovieActorFragment

    val singleLiveShouldSwitchFavoriteTvShowDetailsFragmentFromTvShowByGenreFragment: LiveData<Boolean>
        get() = shouldSwitchFavoriteTvShowDetailsFragmentFromTvShowByGenreFragment

    val singleLiveShouldSwitchFavoriteTvShowDetailsFragmentFromTvShowActorFragment: LiveData<Boolean>
        get() = shouldSwitchFavoriteTvShowDetailFragmentFromTvShowActorFragment

    val singleLiveShouldSwitchEpgMovieDetailsFragment: LiveData<Boolean>
        get() = shouldSwitchEpgMovieDetailFragment

    val singleLiveShouldSwitchEpgTvShowDetailsFragment: LiveData<Boolean>
        get() = shouldSwitchEpgTvShowDetailFragment

    val singleLiveShouldSwitchTvShowDetailsFragment: LiveData<Boolean>
        get() = shouldSwitchTvShowDetailFragment

    val singleLiveShouldSwitchFavoriteMovieDetailsFragment: LiveData<Boolean>
        get() = shouldSwitchFavoriteMovieDetailsFragment

    val singleLiveShouldSwitchFavoriteTvShowDetailsFragment: LiveData<Boolean>
        get() = shouldSwitchFavoriteTvShowDetailsFragment



    fun changeToEpgActorFragment( actorId: Int) {
        liveEpgActorId.value = actorId
        shouldSwitchEpgActorFragment.value = true
    }
    fun changeToMovieActorFragment( actorId: Int) {
        liveMovieActorId.value = actorId
        shouldSwitchMovieActorFragment.value = true
    }
    fun changeToTvActorFragment( actorId: Int) {
        liveTvShowActorId.value = actorId
        shouldSwitchTvActorFragment.value = true
    }

    fun changeToMoviesByGenreListFragment(genreId: Int) {
        liveMovieGenreId.value = genreId
        shouldSwitchMoviesByGenreListFragment.value = true
    }
    fun changeToMoviesByGenreListFragmentFromMovieDetailsFragment(genreId: Int) {
        liveMovieGenreId.value = genreId
        shouldSwitchMoviesByGenreListFragmentFromMovieDetailsFragment.value = true
    }
    fun changeToFavoriteMoviesByGenreListFragment(genreId: Int) {
        liveFavoriteMovieGenreId.value = genreId
        shouldSwitchFavoriteMoviesByGenreListFragment.value = true
    }

    fun changeToFavoriteTvShowsByGenreListFragment(genreId: Int) {
        liveFavoriteTvShowGenreId.value = genreId
        shouldSwitchFavoriteTvShowsByGenreListFragment.value = true
    }

    fun changeToTvShowsByGenreListFragmentFromTvShowDetailsFragment(genreId: Int) {
        liveTvShowGenreId.value = genreId
        shouldSwitchTvShowsByGenreListFragmentFromTvShowDetailsFragment.value = true
    }

    fun changeToEpgMoviesByGenreListFragment(genreId: Int) {
        liveEpgGenreId.value = genreId
        shouldSwitchEpgMoviesByGenreListFragment.value = true
    }

    fun changeToTvShowsByGenreListFragment(genreId: Int) {
        liveTvShowGenreId.value = genreId
        shouldSwitchTvShowsByGenreListFragment.value = true
    }
    fun changeToEpgTvShowsByGenreListFragment(genreId: Int) {
        liveEpgGenreId.value = genreId
        shouldSwitchEpgTvShowsByGenreListFragment.value = true
    }

    fun changeToMoviesAndTvShowsActorFragment( actorId: Int) {
        liveMovieAndTvShowActorSearchId.value = actorId
        shouldSwitchMovieAndTvShowActorFragment.value = true
    }
    fun changeToFavoriteMovieActorFragment( actorId: Int) {
        liveFavoriteMovieActorId.value = actorId
        shouldSwitchFavoriteMovieActorFragment.value = true
    }
    fun changeToFavoriteTvActorFragment( actorId: Int) {
        liveFavoriteTvShowActorId.value = actorId
        shouldSwitchFavoriteTvActorFragment.value = true
    }

    fun changeToMediaDetailsFragment( mediaObject: Any?) {


        if (mediaObject is MoviesWithPerson.Cast) {
            var movie: Movie = transformMovieWithPersonCast(mediaObject)
            livePersonSearchMovie.value = movie
            shouldSwitchSearchMovieDetailFragment.value = true
        }
        if (mediaObject is TvShowWithPerson.Cast) {
            var tvShow: TvShow = transformTvShowWithPersonCast(mediaObject)
            livePersonSearchTvShow.value = tvShow
            shouldSwitchSearchTvShowDetailFragment.value = true
        }

    }



    val liveDataEpgActorId: LiveData<Int>
        get() = liveEpgActorId

    val liveDataMovieActorId: LiveData<Int>
        get() = liveMovieActorId

    val liveDataTvShowActorId: LiveData<Int>
        get() = liveTvShowActorId

    val liveDataFavoriteMovieActorId: LiveData<Int>
        get() = liveFavoriteMovieActorId

    val liveDataFavoriteTvShowActorId: LiveData<Int>
        get() = liveFavoriteTvShowActorId


    val liveDataMovieAndTvShowActorSearchId: LiveData<Int>
        get() = liveMovieAndTvShowActorSearchId

    val singleLiveShouldSwitchEpgActorFragment: LiveData<Boolean>
        get() = shouldSwitchEpgActorFragment

    val singleLiveShouldSwitchMovieActorFragment: LiveData<Boolean>
        get() = shouldSwitchMovieActorFragment

    val singleLiveShouldSwitchTvActorFragment: LiveData<Boolean>
        get() = shouldSwitchTvActorFragment

    val liveDataMovieGenreId: LiveData<Int>
        get() = liveMovieGenreId

    val liveDataTvShowGenreId: LiveData<Int>
        get() = liveTvShowGenreId

    val liveDataEpgGenreId: LiveData<Int>
        get() = liveEpgGenreId

    val liveDataFavoriteMovieGenreId: LiveData<Int>
        get() = liveFavoriteMovieGenreId

    val liveDataFavoriteTvShowGenreId: LiveData<Int>
        get() = liveFavoriteTvShowGenreId

    val liveDataPersonSearchMovie: LiveData<Movie>
        get() = livePersonSearchMovie

    val liveDataPersonSearchTvShow: LiveData<TvShow>
        get() = livePersonSearchTvShow

    val singleLiveShouldSwitchSearchMovieDetailFragment: LiveData<Boolean>
        get() = shouldSwitchSearchMovieDetailFragment

    val singleLiveShouldSwitchSearchTvShowDetailFragment: LiveData<Boolean>
        get() = shouldSwitchSearchTvShowDetailFragment

    val singleLiveShouldSwitchFavoriteMovieActorFragment: LiveData<Boolean>
        get() = shouldSwitchFavoriteMovieActorFragment

    val singleLiveShouldSwitchFavoriteTvActorFragment: LiveData<Boolean>
        get() = shouldSwitchFavoriteTvActorFragment


    val singleLiveShouldSwitchMoviesByGenreListFragment: LiveData<Boolean>
        get() = shouldSwitchMoviesByGenreListFragment

    val singleLiveShouldSwitchMoviesByGenreListFragmentFromMovieDetailsFragment: LiveData<Boolean>
        get() = shouldSwitchMoviesByGenreListFragmentFromMovieDetailsFragment

    val singleLiveShouldSwitchTvShowsByGenreListFragmentFromTvShowDetailsFragment : LiveData<Boolean>
        get() = shouldSwitchTvShowsByGenreListFragmentFromTvShowDetailsFragment

    val singleLiveShouldSwitchTvShowsByGenreListFragment: LiveData<Boolean>
        get() = shouldSwitchTvShowsByGenreListFragment

    val singleLiveShouldSwitchEpgMoviesByGenreListFragment: LiveData<Boolean>
        get() = shouldSwitchEpgMoviesByGenreListFragment

    val singleLiveShouldSwitchEpgTvShowsByGenreListFragment: LiveData<Boolean>
        get() = shouldSwitchEpgTvShowsByGenreListFragment

    val singleLiveShouldSwitchFavoriteMoviesByGenreListFragment: LiveData<Boolean>
        get() = shouldSwitchFavoriteMoviesByGenreListFragment

    val singleLiveShouldSwitchFavoriteTvShowsByGenreListFragment: LiveData<Boolean>
        get() = shouldSwitchFavoriteTvShowsByGenreListFragment

    val singleLiveShouldSwitchMoviesAndTvShowsActorFragment: LiveData<Boolean>
        get() = shouldSwitchMovieAndTvShowActorFragment

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
    private fun transformMovieByGenre(original: MoviesByGenrePage.MovieByGenre?): Movie {
        val gson = Gson()
        return gson.fromJson(gson.toJson(original), Movie::class.java)
    }
    private fun transformMovieWithPersonCast(original: MoviesWithPerson.Cast?): Movie {
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
    private fun transformTvShowByGenre(original: TvShowsByGenrePage.TvShowByGenre?): TvShow {
        val gson = Gson()
        return gson.fromJson(gson.toJson(original), TvShow::class.java)
    }
    private fun transformTvShowSearchResult(original: TvShowSearchResult.TvShow?): TvShow {
        val gson = Gson()
        return gson.fromJson(gson.toJson(original), TvShow::class.java)
    }
    private fun transformTvShowWithPersonCast(original: TvShowWithPerson.Cast?): TvShow {
        val gson = Gson()
        return gson.fromJson(gson.toJson(original), TvShow::class.java)
    }
    private fun transformMovieFavorite(original: MovieFavourite?): Movie {
        val gson = Gson()
        return gson.fromJson(gson.toJson(original), Movie::class.java)
    }
    private fun transformTvShowFavorite(original: TvShowFavourite?): TvShow {
        val gson = Gson()
        return gson.fromJson(gson.toJson(original), TvShow::class.java)
    }
}