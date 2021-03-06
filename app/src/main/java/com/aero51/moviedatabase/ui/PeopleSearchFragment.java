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
import com.aero51.moviedatabase.repository.model.tmdb.credits.ActorSearchResponse;
import com.aero51.moviedatabase.ui.adapter.PeopleSearchPagedListAdapter;
import com.aero51.moviedatabase.viewmodel.SearchViewModel;

public class PeopleSearchFragment extends Fragment {

    private SearchViewModel searchViewModel;
    private PeopleSearchPagedListAdapter peopleAdapter;

    public PeopleSearchFragment() {
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
        View view = inflater.inflate(R.layout.fragment_people_search, container, false);
        RecyclerView peopleRecyclerView = view.findViewById(R.id.people_recycler_view);
        peopleRecyclerView.setHasFixedSize(true);
        peopleAdapter = new PeopleSearchPagedListAdapter();
        peopleRecyclerView.setAdapter(peopleAdapter);
        peopleRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        registerPeopleSearchObserver();
        return view;
    }


    private void registerPeopleSearchObserver() {
        searchViewModel.getPeopleSearchResult().observe(getViewLifecycleOwner(), new Observer<PagedList<ActorSearchResponse.ActorSearch>>() {
            @Override
            public void onChanged(PagedList<ActorSearchResponse.ActorSearch> actorSearches) {
                peopleAdapter.submitList(actorSearches);
            }
        });


    }

}