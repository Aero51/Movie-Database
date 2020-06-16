package com.aero51.moviedatabase.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.aero51.moviedatabase.R;
import com.aero51.moviedatabase.repository.NetworkState;
import com.aero51.moviedatabase.repository.Top_Rated_Movies_Page;
import com.aero51.moviedatabase.repository.Top_Rated_Result;
import com.aero51.moviedatabase.utils.ItemClickListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TopRatedMoviesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TopRatedMoviesFragment extends Fragment implements ItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private TextView emptyViewText;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment top_rated_movies_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TopRatedMoviesFragment newInstance(String param1, String param2) {
        TopRatedMoviesFragment fragment = new TopRatedMoviesFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top_rated_movies_fragment, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        emptyViewText = view.findViewById(R.id.empty_view);

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
        return view;
    }

    private void registerObservers() {
        TopRatedResultViewModel viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(TopRatedResultViewModel.class);
        TopRatedMoviesPagedListAdapter adapter = new TopRatedMoviesPagedListAdapter(this);
        viewModel.getTopRatedResultsPagedList().observe(getViewLifecycleOwner(), new Observer<PagedList<Top_Rated_Result>>() {
            @Override
            public void onChanged(PagedList<Top_Rated_Result> top_rated_results) {
                Log.d("moviedatabaselog", "MainActivity onChanged list size: " + top_rated_results.size());
                adapter.submitList(top_rated_results);

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
                Log.d("moviedatabaselog", "MainActivity onChanged movie_page: " + page_number);
            }
        });
        viewModel.getNetworkState().observe(getViewLifecycleOwner(), new Observer<NetworkState>() {
            @Override
            public void onChanged(NetworkState networkState) {
                // Log.d("moviedatabaselog", "MainActivity onChanged network state: "+networkState.getMsg());
                adapter.setNetworkState(networkState);
            }
        });
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void OnItemClick(Top_Rated_Result result, int position) {

    }
}