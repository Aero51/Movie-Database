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
import com.aero51.moviedatabase.repository.model.PopularMovie;
import com.aero51.moviedatabase.repository.model.PopularMoviesPage;
import com.aero51.moviedatabase.repository.model.TopRatedMovie;
import com.aero51.moviedatabase.repository.model.TopRatedMoviesPage;
import com.aero51.moviedatabase.ui.adapter.PopularMoviesPagedListAdapter;
import com.aero51.moviedatabase.ui.adapter.TopRatedMoviesPagedListAdapter;
import com.aero51.moviedatabase.utils.PopularItemClickListener;
import com.aero51.moviedatabase.utils.TopRatedItemClickListener;
import com.aero51.moviedatabase.viewmodel.MovieDetailsViewModel;
import com.aero51.moviedatabase.viewmodel.MoviesViewModel;

public class MoviesFragment extends Fragment implements TopRatedItemClickListener, PopularItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView textViewTopRatedMovie;
    private TextView textViewPopularMovie;
    private RecyclerView topRatedRecyclerView;
    private RecyclerView popularRecyclerView;
    private TextView emptyViewText;
    private MoviesViewModel moviesViewModel;
    private MovieDetailsViewModel detailsViewModel;
    private TopRatedMoviesPagedListAdapter topRatedAdapter;
    private PopularMoviesPagedListAdapter popularAdapter;

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
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movies, container, false);

        textViewTopRatedMovie = view.findViewById(R.id.text_view_top_rated_movie);
        textViewPopularMovie = view.findViewById(R.id.text_view_popular_movie);

        topRatedRecyclerView = view.findViewById(R.id.top_rated_movies_recycler_view_horizontal);
        popularRecyclerView = view.findViewById(R.id.popular_movies_recycler_view_horizontal);

        textViewTopRatedMovie.setText("Top rated movies:");
        textViewPopularMovie.setText("Popular movies:");

        topRatedRecyclerView.setHasFixedSize(true);
        popularRecyclerView.setHasFixedSize(true);
        emptyViewText = view.findViewById(R.id.empty_view);

        topRatedAdapter = new TopRatedMoviesPagedListAdapter(this);
        topRatedRecyclerView.setAdapter(topRatedAdapter);
        popularAdapter = new PopularMoviesPagedListAdapter(this);
        popularRecyclerView.setAdapter(popularAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        topRatedRecyclerView.setLayoutManager(linearLayoutManager);

        LinearLayoutManager newlinearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        popularRecyclerView.setLayoutManager(newlinearLayoutManager);

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

        moviesViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(MoviesViewModel.class);
        registerTopRatedMoviesObservers();

        detailsViewModel = new ViewModelProvider(requireActivity()).get(MovieDetailsViewModel.class);
        registerPopularMoviesObservers();
        return view;
    }

    private void registerTopRatedMoviesObservers() {
        moviesViewModel.getTopRatedResultsPagedList().observe(getViewLifecycleOwner(), new Observer<PagedList<TopRatedMovie>>() {
            @Override
            public void onChanged(PagedList<TopRatedMovie> top_rated_results) {
                Log.d("moviedatabaselog", "MoviesFragment,topRated  onChanged list size: " + top_rated_results.size());

                topRatedAdapter.submitList(top_rated_results);

                if (top_rated_results.isEmpty()) {
                    topRatedRecyclerView.setVisibility(View.GONE);
                    emptyViewText.setVisibility(View.VISIBLE);
                } else {
                    topRatedRecyclerView.setVisibility(View.VISIBLE);
                    emptyViewText.setVisibility(View.GONE);
                }
            }
        });
        moviesViewModel.getTopRatedLiveMoviePage().observe(getViewLifecycleOwner(), new Observer<TopRatedMoviesPage>() {
            @Override
            public void onChanged(TopRatedMoviesPage top_rated_movies_page) {
                Integer page_number;
                if (top_rated_movies_page == null) {
                    page_number = 0;
                } else {
                    page_number = top_rated_movies_page.getPage();
                }
                Log.d("moviedatabaselog", "MoviesFragment,topRated onChanged movie_page: " + page_number);
            }
        });
        moviesViewModel.getTopRatedMoviesNetworkState().observe(getViewLifecycleOwner(), new Observer<NetworkState>() {
            @Override
            public void onChanged(NetworkState networkState) {
                // Log.d("moviedatabaselog", "MainActivity onChanged network state: "+networkState.getMsg());
                topRatedAdapter.setNetworkState(networkState);
            }
        });

    }

    private void registerPopularMoviesObservers() {
        moviesViewModel.getPopularResultsPagedList().observe(getViewLifecycleOwner(), new Observer<PagedList<PopularMovie>>() {
            @Override
            public void onChanged(PagedList<PopularMovie> popularMovies) {
                Log.d("moviedatabaselog", "MoviesFragment,popular  onChanged list size: " + popularMovies.size());
                popularAdapter.submitList(popularMovies);
            }
        });
        moviesViewModel.getPopularLiveMoviePage().observe(getViewLifecycleOwner(), new Observer<PopularMoviesPage>() {
            @Override
            public void onChanged(PopularMoviesPage popularMoviesPage) {
                Integer page_number;
                if (popularMoviesPage == null) {
                    page_number = 0;
                } else {
                    page_number = popularMoviesPage.getPage();
                }
                Log.d("moviedatabaselog", "MoviesFragment,popular onChanged movie_page: " + page_number);
            }

        });
    }


    @Override
    public void OnItemClick(TopRatedMovie result, int position) {
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

    @Override
    public void OnItemClick(PopularMovie result, int position) {

    }
}
