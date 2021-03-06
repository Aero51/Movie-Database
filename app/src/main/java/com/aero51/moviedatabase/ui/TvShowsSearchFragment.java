package com.aero51.moviedatabase.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aero51.moviedatabase.databinding.FragmentTvShowsSearchBinding;
import com.aero51.moviedatabase.repository.model.tmdb.movie.NowPlayingMoviesPage;
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.TvShowSearchResult;
import com.aero51.moviedatabase.ui.adapter.TvShowsPagedListAdapter;
import com.aero51.moviedatabase.utils.NowPlayingItemClickListener;
import com.aero51.moviedatabase.viewmodel.SearchViewModel;

public class TvShowsSearchFragment extends Fragment implements NowPlayingItemClickListener {
    private FragmentTvShowsSearchBinding binding;
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
        binding = FragmentTvShowsSearchBinding.inflate(inflater, container, false);

        binding.tvShowsSearchRecyclerView.setHasFixedSize(true);
        tvShowsSearchPagedListAdapter = new TvShowsPagedListAdapter();
        binding.tvShowsSearchRecyclerView.setAdapter(tvShowsSearchPagedListAdapter);
        binding.tvShowsSearchRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        registerTvShowsSearchObserver();
        return binding.getRoot();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
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