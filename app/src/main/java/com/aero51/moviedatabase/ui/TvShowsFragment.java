package com.aero51.moviedatabase.ui;

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
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.AiringTvShowsPage;
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.PopularTvShowsPage;
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.TrendingTvShowsPage;
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.TvShowGenresResponse;
import com.aero51.moviedatabase.ui.adapter.AiringTvShowsPagedListAdapter;
import com.aero51.moviedatabase.ui.adapter.PopularTvShowsPagedListAdapter;
import com.aero51.moviedatabase.ui.adapter.TrendingTvShowsPagedListAdapter;
import com.aero51.moviedatabase.ui.adapter.TvShowGenresAdapter;
import com.aero51.moviedatabase.utils.Constants;
import com.aero51.moviedatabase.utils.MovieClickListener;
import com.aero51.moviedatabase.utils.Resource;
import com.aero51.moviedatabase.utils.Status;
import com.aero51.moviedatabase.viewmodel.SharedViewModel;
import com.aero51.moviedatabase.viewmodel.TvShowsViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TvShowsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TvShowsFragment extends Fragment implements MovieClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private TvShowsViewModel tvShowsViewModel;
    private PopularTvShowsPagedListAdapter popularAdapter;
    private AiringTvShowsPagedListAdapter airingAdapter;
    private TrendingTvShowsPagedListAdapter trendingAdapter;
    private SharedViewModel sharedViewModel;

    private RecyclerView tvShowGenreRecyclerView;

    public TvShowsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TvShowsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TvShowsFragment newInstance(String param1, String param2) {
        TvShowsFragment fragment = new TvShowsFragment();
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
        tvShowsViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(TvShowsViewModel.class);
        sharedViewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tv_shows, container, false);
        TextView textViewPopularTvShow = view.findViewById(R.id.text_view_popular_tv_show);
        textViewPopularTvShow.setText("Popular tv shows:");
        TextView textViewAiringTvShow = view.findViewById(R.id.text_view_airing_tv_show);
        textViewAiringTvShow.setText("Airing tv shows:");
        TextView textViewTrendingTvShow = view.findViewById(R.id.text_view_trending_tv_show);
        textViewTrendingTvShow.setText("trending tv shows:");
        TextView emptyViewText = view.findViewById(R.id.empty_view);

        RecyclerView popularRecyclerView = view.findViewById(R.id.popular_tv_shows_recycler_view_horizontal);
        popularRecyclerView.setHasFixedSize(true);
        popularAdapter = new PopularTvShowsPagedListAdapter(this);
        popularRecyclerView.setAdapter(popularAdapter);
        LinearLayoutManager popularlinearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        popularRecyclerView.setLayoutManager(popularlinearLayoutManager);
        popularRecyclerView.setNestedScrollingEnabled(false);

        RecyclerView airingRecyclerView = view.findViewById(R.id.airing_tv_shows_recycler_view_horizontal);
        airingRecyclerView.setHasFixedSize(true);
        airingAdapter = new AiringTvShowsPagedListAdapter(this);
        airingRecyclerView.setAdapter(airingAdapter);
        LinearLayoutManager airinglinearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        airingRecyclerView.setLayoutManager(airinglinearLayoutManager);
        airingRecyclerView.setNestedScrollingEnabled(false);

        RecyclerView trendingRecyclerView = view.findViewById(R.id.trending_tv_shows_recycler_view_horizontal);
        trendingRecyclerView.setHasFixedSize(true);
        trendingAdapter = new TrendingTvShowsPagedListAdapter(this);
        trendingRecyclerView.setAdapter(trendingAdapter);
        LinearLayoutManager trendinglinearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        trendingRecyclerView.setLayoutManager(trendinglinearLayoutManager);
        trendingRecyclerView.setNestedScrollingEnabled(false);

        tvShowGenreRecyclerView = view.findViewById(R.id.tv_show_genres_recycler_view_horizontal);
        tvShowGenreRecyclerView.setHasFixedSize(true);
        tvShowGenreRecyclerView.setNestedScrollingEnabled(false);
        LinearLayoutManager genreslinearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        tvShowGenreRecyclerView.setLayoutManager(genreslinearLayoutManager);

        registerHasEpgTvFragmentFinishedLoadingObserver();
        return view;
    }


    private void registerHasEpgTvFragmentFinishedLoadingObserver() {
        sharedViewModel.getHasEpgTvFragmentFinishedLoading().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                //Log.d(Constants.LOG, "getHasEpgTvFragmentFinishedLoading: " + aBoolean);
                if (aBoolean) {
                    registerPopularTvShowsObservers();
                    registerTrendingTvShowsObservers();
                    registerTvShowGenresObservers();
                    registerAiringTvShowsObservers();

                }
            }
        });
    }

    private void registerPopularTvShowsObservers() {
        tvShowsViewModel.getPopularLiveTvShowPage().observe(getViewLifecycleOwner(), new Observer<PopularTvShowsPage>() {
            @Override
            public void onChanged(PopularTvShowsPage popularTvShowsPage) {
                Integer page_number;
                if (popularTvShowsPage == null) {
                    page_number = 0;
                } else {
                    page_number = popularTvShowsPage.getPage();
                }
                Log.d(Constants.LOG, "popular Tv shows Fragment onChanged popular tv show page: " + page_number);
            }

        });
        tvShowsViewModel.getTvPopularResultsPagedList().observe(getViewLifecycleOwner(), new Observer<PagedList<PopularTvShowsPage.PopularTvShow>>() {
            @Override
            public void onChanged(PagedList<PopularTvShowsPage.PopularTvShow> popularMovies) {
                //Log.d(Constants.LOG, "popular Tv shows Fragment  onChanged list size: " + popularMovies.size());
                popularAdapter.submitList(popularMovies);
            }
        });

    }

    private void registerAiringTvShowsObservers() {

        tvShowsViewModel.getTvAiringResultsPagedList().observe(getViewLifecycleOwner(), new Observer<PagedList<AiringTvShowsPage.AiringTvShow>>() {
            @Override
            public void onChanged(PagedList<AiringTvShowsPage.AiringTvShow> airingTvShows) {
                airingAdapter.submitList(airingTvShows);
            }
        });
        tvShowsViewModel.getAiringLiveTvShowPage().observe(getViewLifecycleOwner(), new Observer<AiringTvShowsPage>() {
            @Override
            public void onChanged(AiringTvShowsPage airingTvShowsPage) {
                Integer page_number;
                if (airingTvShowsPage == null) {
                    page_number = 0;
                } else {
                    page_number = airingTvShowsPage.getPage();
                }
                Log.d(Constants.LOG, "airing Tv shows Fragment onChanged tv_page: " + page_number);
            }
        });
    }

    private void registerTrendingTvShowsObservers() {
        tvShowsViewModel.getTvTrendingResultsPagedList().observe(getViewLifecycleOwner(), new Observer<PagedList<TrendingTvShowsPage.TrendingTvShow>>() {
            @Override
            public void onChanged(PagedList<TrendingTvShowsPage.TrendingTvShow> trendingTvShows) {
                trendingAdapter.submitList(trendingTvShows);
            }
        });
        tvShowsViewModel.getTrendingLiveTvShowPage().observe(getViewLifecycleOwner(), new Observer<TrendingTvShowsPage>() {
            @Override
            public void onChanged(TrendingTvShowsPage trendingTvShowsPage) {
                Integer page_number;
                if (trendingTvShowsPage == null) {
                    page_number = 0;
                } else {
                    page_number = trendingTvShowsPage.getPage();
                }
                Log.d(Constants.LOG, "trending Tv shows Fragment onChanged tv_page: " + page_number);
            }
        });
    }

    private void registerTvShowGenresObservers() {
        tvShowsViewModel.getTvShowsGenres().observe(getViewLifecycleOwner(), new Observer<Resource<List<TvShowGenresResponse.TvShowGenre>>>() {
            @Override
            public void onChanged(Resource<List<TvShowGenresResponse.TvShowGenre>> listResource) {
                if (listResource.getStatus() == Status.SUCCESS) {
                    Log.d(Constants.LOG, "TvShowGenresObserver list size  " + listResource.getData().size());
                    TvShowGenresAdapter tvShowGenresAdapter = new TvShowGenresAdapter(listResource.getData());
                    tvShowGenreRecyclerView.setAdapter(tvShowGenresAdapter);
                }

            }
        });
    }

    @Override
    public void onObjectItemClick(Object tvShow, int position) {
        //sharedViewModel.changeToMoviedetailsFragment(movie,position);
        Log.d(Constants.LOG, "popular TvShowsFragment  position: " + position);
    }

}