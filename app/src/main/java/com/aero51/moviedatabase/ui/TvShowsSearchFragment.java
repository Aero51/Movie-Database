package com.aero51.moviedatabase.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aero51.moviedatabase.R;
import com.aero51.moviedatabase.repository.model.tmdb.movie.NowPlayingMoviesPage;
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.TvShowSearchResult;
import com.aero51.moviedatabase.ui.adapter.TvShowsPagedListAdapter;
import com.aero51.moviedatabase.utils.NowPlayingItemClickListener;
import com.aero51.moviedatabase.viewmodel.SearchViewModel;

public class TvShowsSearchFragment extends Fragment implements NowPlayingItemClickListener {

    private SearchViewModel searchViewModel;
    private TvShowsPagedListAdapter tvShowsSearchPagedListAdapter;

    public TvShowsSearchFragment() {
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
        View view = inflater.inflate(R.layout.fragment_tv_shows_search, container, false);
        RecyclerView tvShowSearchRecyclerView = view.findViewById(R.id.tv_shows_search_recycler_view);
        tvShowSearchRecyclerView.setHasFixedSize(true);
        tvShowsSearchPagedListAdapter= new TvShowsPagedListAdapter();
        tvShowSearchRecyclerView.setAdapter(tvShowsSearchPagedListAdapter);
        tvShowSearchRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        registerTvShowsSearchObserver();
        return view;
    }

    private void registerTvShowsSearchObserver() {
        searchViewModel.getTvShowSearchResult().observe(getViewLifecycleOwner(), new Observer<PagedList<TvShowSearchResult.TvShow>>() {
            @Override
            public void onChanged(PagedList<TvShowSearchResult.TvShow> tvShows) {
                tvShowsSearchPagedListAdapter.submitList(tvShows);
            }
        });

    }

    @Override
    public void OnItemClick(NowPlayingMoviesPage.NowPlayingMovie result, int position) {

    }
}