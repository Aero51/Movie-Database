package com.aero51.moviedatabase.ui

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aero51.moviedatabase.databinding.FragmentMoviesBinding
import com.aero51.moviedatabase.ui.adapter.*
import com.aero51.moviedatabase.utils.Constants
import com.aero51.moviedatabase.utils.MovieClickListener
import com.aero51.moviedatabase.utils.Status
import com.aero51.moviedatabase.viewmodel.MoviesViewModel
import com.aero51.moviedatabase.viewmodel.SharedViewModel

class MoviesFragment : Fragment(), MovieClickListener {
    private var moviesViewModel: MoviesViewModel? = null
    private var topRatedAdapter: TopRatedMoviesPagedListAdapter? = null
    private var nowPlayingAdapter: NowPlayingMoviesPagedListAdapter? = null
    private var popularAdapter: PopularMoviesPagedListAdapter? = null
    private var upcomingAdapter: UpcomingMoviesPagedListAdapter? = null
    private var sharedViewModel: SharedViewModel? = null
    private var binding: FragmentMoviesBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        moviesViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)).get(MoviesViewModel::class.java)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentMoviesBinding.inflate(inflater, container, false)
        binding!!.textViewTopRatedMovie.text = "Top rated movies:"
        binding!!.textViewPopularMovie.text = "Popular movies:"
        binding!!.textViewNowPlayingMovie.text = "Now playing movies:"
        binding!!.textViewUpcomingMovie.text = "Upcoming movies:"

        //TextView emptyViewText = view.findViewById(R.id.empty_view);
        binding!!.topRatedMoviesRecyclerViewHorizontal.setHasFixedSize(true)
        binding!!.popularMoviesRecyclerViewHorizontal.setHasFixedSize(true)
        binding!!.nowPlayingMoviesRecyclerViewHorizontal.setHasFixedSize(true)
        binding!!.upcomingMoviesRecyclerViewHorizontal.setHasFixedSize(true)
        binding!!.movieGenresRecyclerViewHorizontal.setHasFixedSize(true)
        binding!!.topRatedMoviesRecyclerViewHorizontal.isNestedScrollingEnabled = false
        binding!!.popularMoviesRecyclerViewHorizontal.isNestedScrollingEnabled = false
        binding!!.nowPlayingMoviesRecyclerViewHorizontal.isNestedScrollingEnabled = false
        binding!!.upcomingMoviesRecyclerViewHorizontal.isNestedScrollingEnabled = false
        binding!!.movieGenresRecyclerViewHorizontal.isNestedScrollingEnabled = false
        topRatedAdapter = TopRatedMoviesPagedListAdapter(this)
        binding!!.topRatedMoviesRecyclerViewHorizontal.adapter = topRatedAdapter
        popularAdapter = PopularMoviesPagedListAdapter(this)
        binding!!.popularMoviesRecyclerViewHorizontal.adapter = popularAdapter
        nowPlayingAdapter = NowPlayingMoviesPagedListAdapter(this)
        binding!!.nowPlayingMoviesRecyclerViewHorizontal.adapter = nowPlayingAdapter
        upcomingAdapter = UpcomingMoviesPagedListAdapter(this)
        binding!!.upcomingMoviesRecyclerViewHorizontal.adapter = upcomingAdapter
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding!!.topRatedMoviesRecyclerViewHorizontal.layoutManager = linearLayoutManager
        val popularlinearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding!!.popularMoviesRecyclerViewHorizontal.layoutManager = popularlinearLayoutManager
        val nowPlayingLinearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding!!.nowPlayingMoviesRecyclerViewHorizontal.layoutManager = nowPlayingLinearLayoutManager
        val upcomingLinearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding!!.upcomingMoviesRecyclerViewHorizontal.layoutManager = upcomingLinearLayoutManager
        val genresLinearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding!!.movieGenresRecyclerViewHorizontal.layoutManager = genresLinearLayoutManager
        val handler = Handler()
        handler.postDelayed({
            //Do something after 100ms
            //   Log.d(Constants.LOG, "runnable runned! " );
        }, 3000)
        registerHasEpgTvFragmentFinishedLoadingObserver()
        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun registerHasEpgTvFragmentFinishedLoadingObserver() {
        sharedViewModel!!.hasEpgTvFragmentFinishedLoading.observe(viewLifecycleOwner, { aBoolean ->
            if (aBoolean) {
                registerTopRatedMoviesObservers()
                registerPopularMoviesObservers()
                registerNowPlayingMoviesObservers()
                registerUpcomingMoviesObservers()
                registerMovieGenresObservers()
            }
        })
    }

    private fun registerTopRatedMoviesObservers() {
        moviesViewModel!!.topRatedResultsPagedList.observe(viewLifecycleOwner, { top_rated_results ->
            Log.d(Constants.LOG, "topRated MoviesFragment  onChanged list size: " + top_rated_results.size)
            topRatedAdapter!!.submitList(top_rated_results)
            /*
                if (top_rated_results.isEmpty()) {
                    topRatedRecyclerView.setVisibility(View.GONE);
                    emptyViewText.setVisibility(View.VISIBLE);
                } else {
                    topRatedRecyclerView.setVisibility(View.VISIBLE);
                    emptyViewText.setVisibility(View.GONE);
                }
*/
        })
        moviesViewModel!!.topRatedLiveMoviePage.observe(viewLifecycleOwner, { top_rated_movies_page ->
            val page_number: Int
            page_number = if (top_rated_movies_page == null) {
                0
            } else {
                top_rated_movies_page.page
            }
            Log.d(Constants.LOG, "topRated MoviesFragment onChanged movie_page: $page_number")
        })
        moviesViewModel!!.topRatedMoviesNetworkState.observe(viewLifecycleOwner, {
            // Log.d(Constants.LOG, "MainActivity onChanged network state: "+networkState.getMsg());
            //topRatedAdapter.setNetworkState(networkState);
        })
    }

    private fun registerPopularMoviesObservers() {
        moviesViewModel!!.popularResultsPagedList.observe(viewLifecycleOwner, { popularMovies -> //Log.d(Constants.LOG, "popular MoviesFragment  onChanged list size: " + popularMovies.size());
            popularAdapter!!.submitList(popularMovies)
        })
        moviesViewModel!!.popularLiveMoviePage.observe(viewLifecycleOwner, { popularMoviesPage ->
            val page_number: Int
            page_number = if (popularMoviesPage == null) {
                0
            } else {
                popularMoviesPage.page
            }
            Log.d(Constants.LOG, "popular MoviesFragment onChanged movie_page: $page_number")
        })
    }

    private fun registerNowPlayingMoviesObservers() {
        moviesViewModel!!.nowPlayingResultsPagedList.observe(viewLifecycleOwner, { now_playing_results ->
            Log.d(Constants.LOG, "now playing MoviesFragment  onChanged list size: " + now_playing_results.size)
            nowPlayingAdapter!!.submitList(now_playing_results)
            /*
                if (top_rated_results.isEmpty()) {
                    topRatedRecyclerView.setVisibility(View.GONE);
                    emptyViewText.setVisibility(View.VISIBLE);
                } else {
                    topRatedRecyclerView.setVisibility(View.VISIBLE);
                    emptyViewText.setVisibility(View.GONE);
                }
*/
        })
        moviesViewModel!!.nowPlayingLiveMoviePage.observe(viewLifecycleOwner, { now_playing_movies_page ->
            val page_number: Int
            page_number = if (now_playing_movies_page == null) {
                0
            } else {
                now_playing_movies_page.page
            }
            Log.d(Constants.LOG, "now playing MoviesFragment onChanged movie_page: $page_number")
        })
        moviesViewModel!!.nowPlayingMoviesNetworkState.observe(viewLifecycleOwner, {
            // Log.d(Constants.LOG, "MainActivity onChanged network state: "+networkState.getMsg());
            //topRatedAdapter.setNetworkState(networkState);
        })
    }

    private fun registerUpcomingMoviesObservers() {
        moviesViewModel!!.upcomingResultsPagedList.observe(viewLifecycleOwner, { upcoming_results ->
            Log.d(Constants.LOG, "upcoming MoviesFragment  onChanged list size: " + upcoming_results.size)
            upcomingAdapter!!.submitList(upcoming_results)
        })
        moviesViewModel!!.upcomingLiveMoviePage.observe(viewLifecycleOwner, { upcoming_movies_page ->
            val page_number: Int
            page_number = if (upcoming_movies_page == null) {
                0
            } else {
                upcoming_movies_page.page
            }
            Log.d(Constants.LOG, "upcoming MoviesFragment onChanged movie_page: $page_number")
        })
        moviesViewModel!!.upcomingMoviesNetworkState.observe(viewLifecycleOwner, {
            // Log.d(Constants.LOG, "MainActivity onChanged network state: "+networkState.getMsg());
            //upcomingAdapter.setNetworkState(networkState);
        })
    }

    private fun registerMovieGenresObservers() {
        moviesViewModel!!.moviesGenres.observe(viewLifecycleOwner, { (status, data) ->
            if (status === Status.SUCCESS) {
                Log.d(Constants.LOG, "MovieGenresObservers list size  " + data!!.size)
                val movieGenresAdapter = MovieGenresAdapter(data)
                binding!!.movieGenresRecyclerViewHorizontal.adapter = movieGenresAdapter
            }
        })
    }

    /*
        @Override
        public void OnItemClick(TopRatedMovie result, int position) {
            //  this.OnObjectItemClick(result,position);
            sharedViewModel.changeToTopRatedMovieFragment(position, result);
            Log.d(Constants.LOG, "TopRatedMovie OnItemClick title: "+result.getTitle());
            //Movie movie= transformTopRatedMovie(result);
            Log.d(Constants.LOG, "Movie title "+movie.getTitle()+" , vote average"+ movie.getVote_average());
        }

        @Override
        public void OnItemClick(PopularMovie result, int position) {
            sharedViewModel.changeToPopularMovieFragment(position, result);

        }
    */
    override fun onObjectItemClick(movie: Any, position: Int) {
        sharedViewModel!!.changeToMoviedetailsFragment(movie, position)

    }
}

