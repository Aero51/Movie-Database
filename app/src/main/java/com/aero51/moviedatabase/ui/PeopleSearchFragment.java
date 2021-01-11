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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PeopleSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PeopleSearchFragment extends Fragment {

    private SearchViewModel searchViewModel;
    private PeopleSearchPagedListAdapter peopleAdapter;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PeopleSearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PersonsSearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PeopleSearchFragment newInstance(String param1, String param2) {
        PeopleSearchFragment fragment = new PeopleSearchFragment();
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