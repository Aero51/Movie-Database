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
import com.aero51.moviedatabase.repository.model.tmdb.movie.Movie;
import com.aero51.moviedatabase.repository.model.tmdb.movie.PopularMovie;
import com.aero51.moviedatabase.repository.model.tmdb.movie.PopularMoviesPage;
import com.aero51.moviedatabase.repository.model.tmdb.movie.TopRatedMovie;
import com.aero51.moviedatabase.repository.model.tmdb.movie.TopRatedMoviesPage;
import com.aero51.moviedatabase.ui.adapter.PopularMoviesPagedListAdapter;
import com.aero51.moviedatabase.ui.adapter.TopRatedMoviesPagedListAdapter;
import com.aero51.moviedatabase.utils.Constants;
import com.aero51.moviedatabase.utils.MovieClickListener;
import com.aero51.moviedatabase.utils.PopularItemClickListener;
import com.aero51.moviedatabase.utils.TopRatedItemClickListener;
import com.aero51.moviedatabase.viewmodel.MoviesViewModel;
import com.aero51.moviedatabase.viewmodel.SharedViewModel;

import com.google.gson.Gson;

public class MoviesFragment extends Fragment implements MovieClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private MoviesViewModel moviesViewModel;
    private TopRatedMoviesPagedListAdapter topRatedAdapter;
    private PopularMoviesPagedListAdapter popularAdapter;
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
        TextView textViewTopRatedMovie = view.findViewById(R.id.text_view_top_rated_movie);
        TextView textViewPopularMovie = view.findViewById(R.id.text_view_popular_movie);
        textViewTopRatedMovie.setText("Top rated movies:");
        textViewPopularMovie.setText("Popular movies:");
        TextView emptyViewText = view.findViewById(R.id.empty_view);

        RecyclerView topRatedRecyclerView = view.findViewById(R.id.top_rated_movies_recycler_view_horizontal);
        RecyclerView popularRecyclerView = view.findViewById(R.id.popular_movies_recycler_view_horizontal);
        topRatedRecyclerView.setHasFixedSize(true);
        popularRecyclerView.setHasFixedSize(true);
        topRatedAdapter = new TopRatedMoviesPagedListAdapter(this);
        topRatedRecyclerView.setAdapter(topRatedAdapter);
        popularAdapter = new PopularMoviesPagedListAdapter(this);
        popularRecyclerView.setAdapter(popularAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        topRatedRecyclerView.setLayoutManager(linearLayoutManager);
        LinearLayoutManager newlinearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        popularRecyclerView.setLayoutManager(newlinearLayoutManager);


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
                    registerTopRatedMoviesObservers();
                    registerPopularMoviesObservers();

                }
            }
        });
    }


    private void registerTopRatedMoviesObservers() {
        moviesViewModel.getTopRatedResultsPagedList().observe(getViewLifecycleOwner(), new Observer<PagedList<TopRatedMovie>>() {
            @Override
            public void onChanged(PagedList<TopRatedMovie> top_rated_results) {
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
