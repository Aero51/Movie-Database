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
import com.aero51.moviedatabase.repository.model.tmdb.movie.TopRatedMovie;
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.TvShow;
import com.aero51.moviedatabase.ui.adapter.TvShowsPagedListAdapter;
import com.aero51.moviedatabase.utils.TopRatedItemClickListener;
import com.aero51.moviedatabase.viewmodel.SearchViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TvShowsSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TvShowsSearchFragment extends Fragment implements TopRatedItemClickListener {

    private SearchViewModel searchViewModel;
    private TvShowsPagedListAdapter tvShowsSearchPagedListAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TvShowsSearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TvShowsSearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TvShowsSearchFragment newInstance(String param1, String param2) {
        TvShowsSearchFragment fragment = new TvShowsSearchFragment();
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
        searchViewModel.getTvShowSearchResult().observe(getViewLifecycleOwner(), new Observer<PagedList<TvShow>>() {
            @Override
            public void onChanged(PagedList<TvShow> tvShows) {
                tvShowsSearchPagedListAdapter.submitList(tvShows);
            }
        });

    }

    @Override
    public void OnItemClick(TopRatedMovie result, int position) {

    }
}