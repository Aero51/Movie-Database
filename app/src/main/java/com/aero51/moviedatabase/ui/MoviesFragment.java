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
import com.aero51.moviedatabase.repository.model.NetworkState;
import com.aero51.moviedatabase.repository.model.tmdb.movie.PopularMovie;
import com.aero51.moviedatabase.repository.model.tmdb.movie.PopularMoviesPage;
import com.aero51.moviedatabase.repository.model.tmdb.movie.NowPlayingMovie;
import com.aero51.moviedatabase.repository.model.tmdb.movie.NowPlayingMoviesPage;
import com.aero51.moviedatabase.repository.model.tmdb.movie.UpcomingMovie;
import com.aero51.moviedatabase.repository.model.tmdb.movie.UpcomingMoviesPage;
import com.aero51.moviedatabase.ui.adapter.PopularMoviesPagedListAdapter;
import com.aero51.moviedatabase.ui.adapter.NowPlayingMoviesPagedListAdapter;
import com.aero51.moviedatabase.ui.adapter.UpcomingMoviesPagedListAdapter;
import com.aero51.moviedatabase.utils.Constants;
import com.aero51.moviedatabase.utils.MovieClickListener;
import com.aero51.moviedatabase.viewmodel.MoviesViewModel;
import com.aero51.moviedatabase.viewmodel.SharedViewModel;

public class MoviesFragment extends Fragment implements MovieClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private MoviesViewModel moviesViewModel;
    private NowPlayingMoviesPagedListAdapter nowPlayingAdapter;
    private PopularMoviesPagedListAdapter popularAdapter;
    private UpcomingMoviesPagedListAdapter upcomingAdapter;
    private SharedViewModel sharedViewModel;



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment top_rated_movies_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MoviesFragment newInstance(String param1, String param2) {
        MoviesFragment fragment = new MoviesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        moviesViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(MoviesViewModel.class);
        sharedViewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        TextView textViewPopularMovie = view.findViewById(R.id.text_view_popular_movie);
        TextView textViewNowPlayingMovie = view.findViewById(R.id.text_view_now_playing_movie);
        TextView textViewUpcomingMovie = view.findViewById(R.id.text_view_upcoming_movie);

        textViewPopularMovie.setText("Popular movies:");
        textViewNowPlayingMovie.setText("Now playing movies:");
        textViewUpcomingMovie.setText("Upcoming movies:");
        TextView emptyViewText = view.findViewById(R.id.empty_view);

        RecyclerView popularRecyclerView = view.findViewById(R.id.popular_movies_recycler_view_horizontal);
        RecyclerView nowPlayingRecyclerView = view.findViewById(R.id.now_playing_movies_recycler_view_horizontal);
        RecyclerView upcomingRecyclerView = view.findViewById(R.id.upcoming_movies_recycler_view_horizontal);


        popularRecyclerView.setHasFixedSize(true);
        nowPlayingRecyclerView.setHasFixedSize(true);

        popularAdapter = new PopularMoviesPagedListAdapter(this);
        popularRecyclerView.setAdapter(popularAdapter);
        nowPlayingAdapter = new NowPlayingMoviesPagedListAdapter(this);
        nowPlayingRecyclerView.setAdapter(nowPlayingAdapter);
        upcomingAdapter=new UpcomingMoviesPagedListAdapter(this);
        upcomingRecyclerView.setAdapter(upcomingAdapter);

        LinearLayoutManager popularlinearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        popularRecyclerView.setLayoutManager(popularlinearLayoutManager);
        LinearLayoutManager nowPlayingLinearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        nowPlayingRecyclerView.setLayoutManager(nowPlayingLinearLayoutManager);
        LinearLayoutManager upcomingLinearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        upcomingRecyclerView.setLayoutManager(upcomingLinearLayoutManager);


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                //   Log.d(Constants.LOG, "runnable runned! " );

            }
        }, 3000);
        registerHasEpgTvFragmentFinishedLoadingObserver();
        return view;
    }


    private void registerHasEpgTvFragmentFinishedLoadingObserver() {
        sharedViewModel.getHasEpgTvFragmentFinishedLoading().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    registerPopularMoviesObservers();
                    registerNowPlayingMoviesObservers();
                    registerUpcomingMoviesObservers();

                }
            }
        });
    }




    private void registerPopularMoviesObservers() {

        moviesViewModel.getPopularResultsPagedList().observe(getViewLifecycleOwner(), new Observer<PagedList<PopularMovie>>() {
            @Override
            public void onChanged(PagedList<PopularMovie> popularMovies) {
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
        moviesViewModel.getNowPlayingResultsPagedList().observe(getViewLifecycleOwner(), new Observer<PagedList<NowPlayingMovie>>() {
            @Override
            public void onChanged(PagedList<NowPlayingMovie> now_playing_results) {
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
        moviesViewModel.getUpcomingResultsPagedList().observe(getViewLifecycleOwner(), new Observer<PagedList<UpcomingMovie>>() {
            @Override
            public void onChanged(PagedList<UpcomingMovie> upcoming_results) {
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
    public  void OnObjectItemClick(Object movie, int position) {
        sharedViewModel.changeToMoviedetailsFragment(movie,position);
    }


}
