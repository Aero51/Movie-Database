package com.aero51.moviedatabase.repository.networkonlynotused.test;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aero51.moviedatabase.R;
import com.aero51.moviedatabase.viewmodel.SharedViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewMoviesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewMoviesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private MoviesNewViewModel moviesViewModel;
    private RecyclerView topRatedRecyclerView;
    private RecyclerView popularRecyclerView;
    private TextView emptyViewText;
    private MSimpleAdapter topRatedAdapter;
    private MSimpleAdapter popularAdapter;

    private GroupAdapter groupAdapter;

    private SharedViewModel sharedViewModel;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NewMoviesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewMoviesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewMoviesFragment newInstance(String param1, String param2) {
        NewMoviesFragment fragment = new NewMoviesFragment();
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
        moviesViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(MoviesNewViewModel.class);
        sharedViewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_movies, container, false);
        emptyViewText = view.findViewById(R.id.empty_view);
        topRatedRecyclerView = view.findViewById(R.id.top_rated_movies_recycler_view_horizontal);
        popularRecyclerView = view.findViewById(R.id.popular_movies_recycler_view_horizontal);


        setUpRecyclerViews();


        registerTopRatedMoviesObservers();
      //  registerPopularMoviesObservers();

        return view;

    }


    private void registerTopRatedMoviesObservers() {
        moviesViewModel.getTopRatedMoviesPagedList().observe(getViewLifecycleOwner(), new Observer<PagedList<Movie>>() {
            @Override
            public void onChanged(PagedList<Movie> movies) {
                Log.d("moviedatabaselog", " getTopRatedMoviesPagedList onChanged   size: " + movies.size());

              topRatedAdapter.submitList(movies);
                if (movies.isEmpty()) {
                    topRatedRecyclerView.setVisibility(View.GONE);
                    emptyViewText.setVisibility(View.VISIBLE);
                } else {
                    topRatedRecyclerView.setVisibility(View.VISIBLE);
                    emptyViewText.setVisibility(View.GONE);
                }
                // secondChildAdapter.submitList(movies);
            }
        });
    }
    private void registerPopularMoviesObservers() {
      moviesViewModel.getPopularMoviesPagedList().observe(getViewLifecycleOwner(), new Observer<PagedList<Movie>>() {
          @Override
          public void onChanged(PagedList<Movie> movies) {
              popularAdapter.submitList(movies);
          }
      });
    }
    private void setUpRecyclerViews() {
        topRatedRecyclerView.setHasFixedSize(true);
        popularRecyclerView.setHasFixedSize(true);
        topRatedAdapter = new MSimpleAdapter();
        topRatedRecyclerView.setAdapter(topRatedAdapter);
        popularAdapter = new MSimpleAdapter();
        popularRecyclerView.setAdapter(popularAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        topRatedRecyclerView.setLayoutManager(linearLayoutManager);
        LinearLayoutManager newlinearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        popularRecyclerView.setLayoutManager(newlinearLayoutManager);

    }

    private ArrayList<Group> initGetGroupData() {
        ArrayList<Group> groups = new ArrayList<>();
        groups.add(new Group("Top rated movies", "View All"));
        groups.add(new Group("Popular movies", "View All"));
        return groups;
    }

}