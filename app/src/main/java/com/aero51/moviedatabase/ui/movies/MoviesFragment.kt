package com.aero51.moviedatabase.ui.movies

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aero51.moviedatabase.databinding.FragmentMoviesBinding
import com.aero51.moviedatabase.ui.adapter.*
import com.aero51.moviedatabase.ui.listeners.GenreObjectClickListener
import com.aero51.moviedatabase.ui.listeners.MediaClickListener
import com.aero51.moviedatabase.utils.Status
import com.aero51.moviedatabase.viewmodel.MoviesViewModel
import com.aero51.moviedatabase.viewmodel.SharedViewModel

class MoviesFragment : Fragment(),
    MediaClickListener,
    GenreObjectClickListener {
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var moviesViewModel: MoviesViewModel
    private var topRatedAdapter: TopRatedMoviesPagedListAdapter? = null
    private var nowPlayingAdapter: NowPlayingMoviesPagedListAdapter? = null
    private var popularAdapter: PopularMoviesPagedListAdapter? = null
    private var upcomingAdapter: UpcomingMoviesPagedListAdapter? = null

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

        registerHasEpgTvFragmentFinishedLoadingObserver()

        val handler = Handler()
        handler.postDelayed({
            //Do something after 100ms
        }, 3000)

        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun registerHasEpgTvFragmentFinishedLoadingObserver() {
        sharedViewModel.hasEpgTvFragmentFinishedLoading.observe(viewLifecycleOwner, { aBoolean ->
            if (aBoolean) {
                sharedViewModel.hasEpgTvFragmentFinishedLoading.removeObservers(viewLifecycleOwner)
                registerTopRatedMoviesObservers()
                registerPopularMoviesObservers()
                registerNowPlayingMoviesObservers()
                registerUpcomingMoviesObservers()
                registerMovieGenresObservers()
            }
        })
    }

    private fun registerTopRatedMoviesObservers() {
        //to check if data is more then week old, then app should refresh with new data from network
        moviesViewModel.topRatedMoviesDataValidationCheck()

        moviesViewModel!!.topRatedResultsPagedList?.observe(viewLifecycleOwner, { top_rated_results ->
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
        })
        moviesViewModel!!.topRatedMoviesNetworkState.observe(viewLifecycleOwner, {
            //topRatedAdapter.setNetworkState(networkState);
        })
    }

    private fun registerPopularMoviesObservers() {
        moviesViewModel.popularMoviesDataValidationCheck()
        moviesViewModel!!.popularResultsPagedList?.observe(viewLifecycleOwner, { popularMovies ->
            popularAdapter!!.submitList(popularMovies)
        })
        moviesViewModel!!.popularLiveMoviePage.observe(viewLifecycleOwner, { popularMoviesPage ->
            val page_number: Int
            page_number = if (popularMoviesPage == null) {
                0
            } else {
                popularMoviesPage.page
            }
        })
    }

    private fun registerNowPlayingMoviesObservers() {
        moviesViewModel.nowPlayingMoviesDataValidationCheck()
        moviesViewModel!!.nowPlayingResultsPagedList?.observe(viewLifecycleOwner, { now_playing_results ->
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
        })
        moviesViewModel!!.nowPlayingMoviesNetworkState.observe(viewLifecycleOwner, {
            //topRatedAdapter.setNetworkState(networkState);
        })
    }

    private fun registerUpcomingMoviesObservers() {
        //to check if data is more then week old, then app should refresh with new data from network
        moviesViewModel.upcomingMoviesDataValidationCheck()
        moviesViewModel!!.upcomingResultsPagedList?.observe(viewLifecycleOwner, { upcoming_results ->
            upcomingAdapter!!.submitList(upcoming_results)
        })
        moviesViewModel!!.upcomingLiveMoviePage.observe(viewLifecycleOwner, { upcoming_movies_page ->
            val page_number: Int
            page_number = if (upcoming_movies_page == null) {
                0
            } else {
                upcoming_movies_page.page
            }
        })
        moviesViewModel!!.upcomingMoviesNetworkState.observe(viewLifecycleOwner, {
            //upcomingAdapter.setNetworkState(networkState);
        })
    }

    private fun registerMovieGenresObservers() {
        moviesViewModel!!.moviesGenres.observe(viewLifecycleOwner, { (status, data) ->
            if (status === Status.SUCCESS) {
                //TODO   correctly implement not null data
                val movieGenresAdapter = MovieGenresAdapter(data!!,this)
                binding!!.movieGenresRecyclerViewHorizontal.adapter = movieGenresAdapter
            }
        })
    }




    /*
        @Override
        public void OnItemClick(TopRatedMovie result, int position) {
            //  this.OnObjectItemClick(result,position);
            sharedViewModel.changeToTopRatedMovieFragment(position, result);
            //Movie movie= transformTopRatedMovie(result);
        }

        @Override
        public void OnItemClick(PopularMovie result, int position) {
            sharedViewModel.changeToPopularMovieFragment(position, result);

        }
    */


    override fun onGenreItemClick(genreId: Int, position: Int) {
        sharedViewModel.changeToMoviesByGenreListFragment(genreId)
    }

    override fun onMediaItemClick(movie: Any?, position: Int) {
        sharedViewModel.changeToMoviedetailsFragment(movie)
    }


}

