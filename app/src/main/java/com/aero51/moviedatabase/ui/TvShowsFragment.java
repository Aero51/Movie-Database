package com.aero51.moviedatabase.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aero51.moviedatabase.databinding.FragmentTvShowsBinding;
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


public class TvShowsFragment extends Fragment implements MovieClickListener {

    private FragmentTvShowsBinding binding;
    private TvShowsViewModel tvShowsViewModel;
    private PopularTvShowsPagedListAdapter popularAdapter;
    private AiringTvShowsPagedListAdapter airingAdapter;
    private TrendingTvShowsPagedListAdapter trendingAdapter;
    private SharedViewModel sharedViewModel;


    public TvShowsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvShowsViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(TvShowsViewModel.class);
        sharedViewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTvShowsBinding.inflate(inflater, container, false);
        binding.textViewPopularTvShow.setText("Popular tv shows:");
        binding.textViewAiringTvShow.setText("Airing tv shows:");
        binding.textViewTrendingTvShow.setText("trending tv shows:");

        binding.popularTvShowsRecyclerViewHorizontal.setHasFixedSize(true);
        popularAdapter = new PopularTvShowsPagedListAdapter(this);
        binding.popularTvShowsRecyclerViewHorizontal.setAdapter(popularAdapter);
        LinearLayoutManager popularlinearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.popularTvShowsRecyclerViewHorizontal.setLayoutManager(popularlinearLayoutManager);
        binding.popularTvShowsRecyclerViewHorizontal.setNestedScrollingEnabled(false);


        binding.airingTvShowsRecyclerViewHorizontal.setHasFixedSize(true);
        airingAdapter = new AiringTvShowsPagedListAdapter(this);
        binding.airingTvShowsRecyclerViewHorizontal.setAdapter(airingAdapter);
        LinearLayoutManager airinglinearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.airingTvShowsRecyclerViewHorizontal.setLayoutManager(airinglinearLayoutManager);
        binding.airingTvShowsRecyclerViewHorizontal.setNestedScrollingEnabled(false);


        binding.trendingTvShowsRecyclerViewHorizontal.setHasFixedSize(true);
        trendingAdapter = new TrendingTvShowsPagedListAdapter(this);
        binding.trendingTvShowsRecyclerViewHorizontal.setAdapter(trendingAdapter);
        LinearLayoutManager trendinglinearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.trendingTvShowsRecyclerViewHorizontal.setLayoutManager(trendinglinearLayoutManager);
        binding.trendingTvShowsRecyclerViewHorizontal.setNestedScrollingEnabled(false);


        binding.tvShowGenresRecyclerViewHorizontal.setHasFixedSize(true);
        binding.tvShowGenresRecyclerViewHorizontal.setNestedScrollingEnabled(false);
        LinearLayoutManager genreslinearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.tvShowGenresRecyclerViewHorizontal.setLayoutManager(genreslinearLayoutManager);

        registerHasEpgTvFragmentFinishedLoadingObserver();
        return binding.getRoot();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
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
                    binding.tvShowGenresRecyclerViewHorizontal.setAdapter(tvShowGenresAdapter);
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