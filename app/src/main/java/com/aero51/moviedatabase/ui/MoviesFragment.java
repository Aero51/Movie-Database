package com.aero51.moviedatabase.ui;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aero51.moviedatabase.R;
import com.aero51.moviedatabase.databinding.FragmentMoviesBinding;
import com.aero51.moviedatabase.repository.model.NetworkState;
import com.aero51.moviedatabase.repository.model.tmdb.movie.MovieGenresResponse;
import com.aero51.moviedatabase.repository.model.tmdb.movie.PopularMoviesPage;
import com.aero51.moviedatabase.repository.model.tmdb.movie.NowPlayingMoviesPage;
import com.aero51.moviedatabase.repository.model.tmdb.movie.TopRatedMoviesPage;
import com.aero51.moviedatabase.repository.model.tmdb.movie.UpcomingMoviesPage;
import com.aero51.moviedatabase.ui.adapter.MovieGenresAdapter;
import com.aero51.moviedatabase.ui.adapter.PopularMoviesPagedListAdapter;
import com.aero51.moviedatabase.ui.adapter.NowPlayingMoviesPagedListAdapter;
import com.aero51.moviedatabase.ui.adapter.TopRatedMoviesPagedListAdapter;
import com.aero51.moviedatabase.ui.adapter.UpcomingMoviesPagedListAdapter;
import com.aero51.moviedatabase.utils.Constants;
import com.aero51.moviedatabase.utils.MovieClickListener;
import com.aero51.moviedatabase.utils.Resource;
import com.aero51.moviedatabase.utils.Status;
import com.aero51.moviedatabase.viewmodel.MoviesViewModel;
import com.aero51.moviedatabase.viewmodel.SharedViewModel;

import java.util.List;

public class MoviesFragment extends Fragment implements MovieClickListener {



    private MoviesViewModel moviesViewModel;
    private TopRatedMoviesPagedListAdapter topRatedAdapter;
    private NowPlayingMoviesPagedListAdapter nowPlayingAdapter;
    private PopularMoviesPagedListAdapter popularAdapter;
    private UpcomingMoviesPagedListAdapter upcomingAdapter;
    private SharedViewModel sharedViewModel;

    private FragmentMoviesBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        moviesViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(MoviesViewModel.class);
        sharedViewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentMoviesBinding.inflate(inflater,container,false);

        binding.textViewTopRatedMovie.setText("Top rated movies:");
        binding.textViewPopularMovie.setText("Popular movies:");
        binding.textViewNowPlayingMovie.setText("Now playing movies:");
        binding.textViewUpcomingMovie.setText("Upcoming movies:");

        //TextView emptyViewText = view.findViewById(R.id.empty_view);

        binding.topRatedMoviesRecyclerViewHorizontal.setHasFixedSize(true);
        binding.popularMoviesRecyclerViewHorizontal.setHasFixedSize(true);
        binding.nowPlayingMoviesRecyclerViewHorizontal.setHasFixedSize(true);
        binding.upcomingMoviesRecyclerViewHorizontal.setHasFixedSize(true);
        binding.movieGenresRecyclerViewHorizontal.setHasFixedSize(true);

        binding.topRatedMoviesRecyclerViewHorizontal.setNestedScrollingEnabled(false);
        binding.popularMoviesRecyclerViewHorizontal.setNestedScrollingEnabled(false);
        binding.nowPlayingMoviesRecyclerViewHorizontal.setNestedScrollingEnabled(false);
        binding.upcomingMoviesRecyclerViewHorizontal.setNestedScrollingEnabled(false);
        binding.movieGenresRecyclerViewHorizontal.setNestedScrollingEnabled(false);



        topRatedAdapter = new TopRatedMoviesPagedListAdapter(this);
        binding.topRatedMoviesRecyclerViewHorizontal.setAdapter(topRatedAdapter);
        popularAdapter = new PopularMoviesPagedListAdapter(this);
        binding.popularMoviesRecyclerViewHorizontal.setAdapter(popularAdapter);
        nowPlayingAdapter = new NowPlayingMoviesPagedListAdapter(this);
        binding.nowPlayingMoviesRecyclerViewHorizontal.setAdapter(nowPlayingAdapter);
        upcomingAdapter = new UpcomingMoviesPagedListAdapter(this);
        binding.upcomingMoviesRecyclerViewHorizontal.setAdapter(upcomingAdapter);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.topRatedMoviesRecyclerViewHorizontal.setLayoutManager(linearLayoutManager);
        LinearLayoutManager popularlinearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.popularMoviesRecyclerViewHorizontal.setLayoutManager(popularlinearLayoutManager);
        LinearLayoutManager nowPlayingLinearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.nowPlayingMoviesRecyclerViewHorizontal.setLayoutManager(nowPlayingLinearLayoutManager);
        LinearLayoutManager upcomingLinearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.upcomingMoviesRecyclerViewHorizontal.setLayoutManager(upcomingLinearLayoutManager);
        LinearLayoutManager genresLinearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.movieGenresRecyclerViewHorizontal.setLayoutManager(genresLinearLayoutManager);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                //   Log.d(Constants.LOG, "runnable runned! " );

            }
        }, 3000);
        registerHasEpgTvFragmentFinishedLoadingObserver();
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }


    private void registerHasEpgTvFragmentFinishedLoadingObserver() {
        sharedViewModel.getHasEpgTvFragmentFinishedLoading().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    registerTopRatedMoviesObservers();
                    registerPopularMoviesObservers();
                    registerNowPlayingMoviesObservers();
                    registerUpcomingMoviesObservers();
                    registerMovieGenresObservers();

                }
            }
        });
    }


    private void registerTopRatedMoviesObservers() {
        moviesViewModel.getTopRatedResultsPagedList().observe(getViewLifecycleOwner(), new Observer<PagedList<TopRatedMoviesPage.TopRatedMovie>>() {
            @Override
            public void onChanged(PagedList<TopRatedMoviesPage.TopRatedMovie> top_rated_results) {
                Log.d(Constants.LOG, "topRated MoviesFragment  onChanged list size: " + top_rated_results.size());
                topRatedAdapter.submitList(top_rated_results);
                /*
                if (top_rated_results.isEmpty()) {
                    topRatedRecyclerView.setVisibility(View.GONE);
                    emptyViewText.setVisibility(View.VISIBLE);
                } else {
                    topRatedRecyclerView.setVisibility(View.VISIBLE);
                    emptyViewText.setVisibility(View.GONE);
                }
*/

            }
        });
        moviesViewModel.getTopRatedLiveMoviePage().observe(getViewLifecycleOwner(), new Observer<TopRatedMoviesPage>() {
            @Override
            public void onChanged(TopRatedMoviesPage top_rated_movies_page) {
                Integer page_number;
                if (top_rated_movies_page == null) {
                    page_number = 0;
                } else {
                    page_number = top_rated_movies_page.getPage();
                }
                Log.d(Constants.LOG, "topRated MoviesFragment onChanged movie_page: " + page_number);
            }
        });
        moviesViewModel.getTopRatedMoviesNetworkState().observe(getViewLifecycleOwner(), new Observer<NetworkState>() {
            @Override
            public void onChanged(NetworkState networkState) {
                // Log.d(Constants.LOG, "MainActivity onChanged network state: "+networkState.getMsg());
                //topRatedAdapter.setNetworkState(networkState);
            }
        });

    }


    private void registerPopularMoviesObservers() {

        moviesViewModel.getPopularResultsPagedList().observe(getViewLifecycleOwner(), new Observer<PagedList<PopularMoviesPage.PopularMovie>>() {
            @Override
            public void onChanged(PagedList<PopularMoviesPage.PopularMovie> popularMovies) {
                //Log.d(Constants.LOG, "popular MoviesFragment  onChanged list size: " + popularMovies.size());
                popularAdapter.submitList(popularMovies);
            }
        });
        moviesViewModel.getPopularLiveMoviePage().observe(getViewLifecycleOwner(), new Observer<PopularMoviesPage>() {
            @Override
            public void onChanged(PopularMoviesPage popularMoviesPage) {
                Integer page_number;
                if (popularMoviesPage == null) {
                    page_number = 0;
                } else {
                    page_number = popularMoviesPage.getPage();
                }
                Log.d(Constants.LOG, "popular MoviesFragment onChanged movie_page: " + page_number);
            }

        });
    }

    private void registerNowPlayingMoviesObservers() {
        moviesViewModel.getNowPlayingResultsPagedList().observe(getViewLifecycleOwner(), new Observer<PagedList<NowPlayingMoviesPage.NowPlayingMovie>>() {
            @Override
            public void onChanged(PagedList<NowPlayingMoviesPage.NowPlayingMovie> now_playing_results) {
                Log.d(Constants.LOG, "now playing MoviesFragment  onChanged list size: " + now_playing_results.size());
                nowPlayingAdapter.submitList(now_playing_results);
                /*
                if (top_rated_results.isEmpty()) {
                    topRatedRecyclerView.setVisibility(View.GONE);
                    emptyViewText.setVisibility(View.VISIBLE);
                } else {
                    topRatedRecyclerView.setVisibility(View.VISIBLE);
                    emptyViewText.setVisibility(View.GONE);
                }
*/
            }
        });
        moviesViewModel.getNowPlayingLiveMoviePage().observe(getViewLifecycleOwner(), new Observer<NowPlayingMoviesPage>() {
            @Override
            public void onChanged(NowPlayingMoviesPage now_playing_movies_page) {
                Integer page_number;
                if (now_playing_movies_page == null) {
                    page_number = 0;
                } else {
                    page_number = now_playing_movies_page.getPage();
                }
                Log.d(Constants.LOG, "now playing MoviesFragment onChanged movie_page: " + page_number);
            }
        });
        moviesViewModel.getNowPlayingMoviesNetworkState().observe(getViewLifecycleOwner(), new Observer<NetworkState>() {
            @Override
            public void onChanged(NetworkState networkState) {
                // Log.d(Constants.LOG, "MainActivity onChanged network state: "+networkState.getMsg());
                //topRatedAdapter.setNetworkState(networkState);
            }
        });

    }

    private void registerUpcomingMoviesObservers() {
        moviesViewModel.getUpcomingResultsPagedList().observe(getViewLifecycleOwner(), new Observer<PagedList<UpcomingMoviesPage.UpcomingMovie>>() {
            @Override
            public void onChanged(PagedList<UpcomingMoviesPage.UpcomingMovie> upcoming_results) {
                Log.d(Constants.LOG, "upcoming MoviesFragment  onChanged list size: " + upcoming_results.size());
                upcomingAdapter.submitList(upcoming_results);
            }
        });
        moviesViewModel.getUpcomingLiveMoviePage().observe(getViewLifecycleOwner(), new Observer<UpcomingMoviesPage>() {
            @Override
            public void onChanged(UpcomingMoviesPage upcoming_movies_page) {
                Integer page_number;
                if (upcoming_movies_page == null) {
                    page_number = 0;
                } else {
                    page_number = upcoming_movies_page.getPage();
                }
                Log.d(Constants.LOG, "upcoming MoviesFragment onChanged movie_page: " + page_number);
            }
        });
        moviesViewModel.getUpcomingMoviesNetworkState().observe(getViewLifecycleOwner(), new Observer<NetworkState>() {
            @Override
            public void onChanged(NetworkState networkState) {
                // Log.d(Constants.LOG, "MainActivity onChanged network state: "+networkState.getMsg());
                //upcomingAdapter.setNetworkState(networkState);
            }
        });

    }

    private void registerMovieGenresObservers() {
        moviesViewModel.getMoviesGenres().observe(getViewLifecycleOwner(), new Observer<Resource<List<MovieGenresResponse.MovieGenre>>>() {
            @Override
            public void onChanged(Resource<List<MovieGenresResponse.MovieGenre>> listResource) {
                if (listResource.getStatus() == Status.SUCCESS) {
                    Log.d(Constants.LOG, "MovieGenresObservers list size  " + listResource.getData().size());
                    MovieGenresAdapter movieGenresAdapter = new MovieGenresAdapter(listResource.getData());
                    binding.movieGenresRecyclerViewHorizontal.setAdapter(movieGenresAdapter);
                }

            }
        });
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
    @Override
    public void onObjectItemClick(Object movie, int position) {
        sharedViewModel.changeToMoviedetailsFragment(movie, position);
    }


}
