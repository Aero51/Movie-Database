package com.aero51.moviedatabase.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aero51.moviedatabase.R;
import com.aero51.moviedatabase.repository.model.NetworkState;
import com.aero51.moviedatabase.repository.model.Top_Rated_Movies_Page;
import com.aero51.moviedatabase.repository.model.Top_Rated_Result;
import com.aero51.moviedatabase.ui.adapter.HomeAdapter;
import com.aero51.moviedatabase.utils.ItemClickListener;
import com.aero51.moviedatabase.viewmodel.MovieDetailsViewModel;
import com.aero51.moviedatabase.viewmodel.TopRatedResultViewModel;

public class MoviesFragment extends Fragment implements ItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private TextView emptyViewText;
    private MovieDetailsViewModel detailsViewModel;
    private HomeAdapter homeAdapter;

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
        Log.d("moviedatabaselog", "MoviesFragment onCreate ");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("moviedatabaselog", "MoviesFragment onCreateView ");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        recyclerView = view.findViewById(R.id.main_recycler_view);

        recyclerView.setHasFixedSize(true);
        emptyViewText = view.findViewById(R.id.empty_view);
        homeAdapter = new HomeAdapter(getContext(), this);

        recyclerView.setAdapter(homeAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                Toast.makeText(view.getContext(), "onMove", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                //  noteViewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(view.getContext(), "Result deleted", Toast.LENGTH_SHORT).show();
            }
        });//.attachToRecyclerView(recyclerView);

        registerObservers();

        detailsViewModel = new ViewModelProvider(requireActivity()).get(MovieDetailsViewModel.class);

        return view;
    }

    private void registerObservers() {
        TopRatedResultViewModel viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(TopRatedResultViewModel.class);

        viewModel.getTopRatedResultsPagedList().observe(getViewLifecycleOwner(), new Observer<PagedList<Top_Rated_Result>>() {
            @Override
            public void onChanged(PagedList<Top_Rated_Result> top_rated_results) {
                Log.d("moviedatabaselog", "MoviesFragment onChanged list size: " + top_rated_results.size());

                homeAdapter.submitInsideList(top_rated_results);

                if (top_rated_results.isEmpty()) {
                    recyclerView.setVisibility(View.GONE);
                    emptyViewText.setVisibility(View.VISIBLE);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    emptyViewText.setVisibility(View.GONE);
                }
            }
        });
        viewModel.getLiveMoviePage().observe(getViewLifecycleOwner(), new Observer<Top_Rated_Movies_Page>() {
            @Override
            public void onChanged(Top_Rated_Movies_Page top_rated_movies_page) {
                Integer page_number;
                if (top_rated_movies_page == null) {
                    page_number = 0;
                } else {
                    page_number = top_rated_movies_page.getPage();
                }
                Log.d("moviedatabaselog", "MoviesFragment onChanged movie_page: " + page_number);
            }
        });
        viewModel.getNetworkState().observe(getViewLifecycleOwner(), new Observer<NetworkState>() {
            @Override
            public void onChanged(NetworkState networkState) {
                // Log.d("moviedatabaselog", "MainActivity onChanged network state: "+networkState.getMsg());
                // adapter.setNetworkState(networkState);
            }
        });

    }


    @Override
    public void OnItemClick(Top_Rated_Result result, int position) {
        detailsViewModel.select(result);
        if (!detailsViewModel.getMovie().hasActiveObservers()) {
            // Create fragment and give it an argument specifying the article it should show
            MovieDetailsFragment detailsFragment = new MovieDetailsFragment();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.fragmentsContainer, detailsFragment);
            transaction.addToBackStack(null);
            // Commit the transaction
            transaction.commit();

        }
    }
}
