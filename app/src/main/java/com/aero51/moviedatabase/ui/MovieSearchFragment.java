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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieSearchFragment extends Fragment implements MovieClickListener {

    private SearchViewModel searchViewModel;
    private NowPlayingMoviesPagedListAdapter moviesSearchAdapter;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MovieSearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MovieSearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MovieSearchFragment newInstance(String param1, String param2) {
        MovieSearchFragment fragment = new MovieSearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchViewModel = new ViewModelProvider(getActivity()).get(SearchViewModel.class);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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