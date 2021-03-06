package com.aero51.moviedatabase.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aero51.moviedatabase.R;
import com.aero51.moviedatabase.repository.model.tmdb.movie.NowPlayingMoviesPage;
import com.aero51.moviedatabase.ui.adapter.NowPlayingMoviesPagedListAdapter;
import com.aero51.moviedatabase.utils.Constants;
import com.aero51.moviedatabase.utils.MovieClickListener;
import com.aero51.moviedatabase.viewmodel.SearchViewModel;

public class MovieSearchFragment extends Fragment implements MovieClickListener {

    private SearchViewModel searchViewModel;
    private NowPlayingMoviesPagedListAdapter moviesSearchAdapter;


    public MovieSearchFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchViewModel = new ViewModelProvider(getActivity()).get(SearchViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_search, container, false);
        RecyclerView movieSearchRecyclerView = view.findViewById(R.id.movies_search_recycler_view);
        movieSearchRecyclerView.setHasFixedSize(true);
        moviesSearchAdapter= new NowPlayingMoviesPagedListAdapter(this);
        movieSearchRecyclerView.setAdapter(moviesSearchAdapter);
        movieSearchRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        registerMovieSearchObserver();
        return view;
    }

    private void registerMovieSearchObserver(){
        searchViewModel.getMovieSearchResult().observe(getViewLifecycleOwner(), new Observer<PagedList<NowPlayingMoviesPage.NowPlayingMovie>>() {
            @Override
            public void onChanged(PagedList<NowPlayingMoviesPage.NowPlayingMovie> nowPlayingMovies) {
                moviesSearchAdapter.submitList(nowPlayingMovies);
            }
        });

    }

    @Override
    public void onObjectItemClick(Object movie, int position) {
        Log.d(Constants.LOG, "MovieSearchFragment OnItemClick on position:"+position);
    }
}