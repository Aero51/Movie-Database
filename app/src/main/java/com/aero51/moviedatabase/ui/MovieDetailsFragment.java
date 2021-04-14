package com.aero51.moviedatabase.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.aero51.moviedatabase.R;
import com.aero51.moviedatabase.databinding.FragmentMovieDetailsBinding;
import com.aero51.moviedatabase.repository.model.tmdb.credits.MovieCredits;
import com.aero51.moviedatabase.repository.model.tmdb.movie.Movie;
import com.aero51.moviedatabase.ui.adapter.CastAdapter;
import com.aero51.moviedatabase.utils.Constants;
import com.aero51.moviedatabase.utils.Resource;
import com.aero51.moviedatabase.viewmodel.MovieDetailsViewModel;
import com.aero51.moviedatabase.viewmodel.SharedViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.aero51.moviedatabase.utils.Constants.BACKDROP_SIZE_W780;
import static com.aero51.moviedatabase.utils.Constants.BASE_IMAGE_URL;

public class MovieDetailsFragment extends Fragment implements CastAdapter.ItemClickListener {
    private FragmentMovieDetailsBinding binding;
    private MovieDetailsViewModel movieDetailsViewModel;
    private CastAdapter castAdapter;
    private SharedViewModel sharedViewModel;

    public MovieDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedViewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);
        movieDetailsViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(MovieDetailsViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false);
        // Log.d(Constants.LOG, "TopRatedMovieDetailsFragment onCreateView " );
        //cover_image_view = getActivity().findViewById(R.id.expandedImage);


        binding.castRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.castRecyclerView.setLayoutManager(linearLayoutManager);
        //castRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        registerSharedViewModelObserver();
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        //toolbar.setTitle("text");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
                Log.d(Constants.LOG, "Toolbar clicked!");
                showBackButton(false);
            }
        });
        showBackButton(true);

        return binding.getRoot();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }

    public void showBackButton(boolean show) {
        if (getActivity() instanceof AppCompatActivity) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(show);
        }
    }

    private void registerSharedViewModelObserver() {
        sharedViewModel.getLiveDataMovie().observe(getViewLifecycleOwner(), new Observer<Movie>() {
            @Override
            public void onChanged(Movie movie) {
                binding.title.setText(movie.getTitle());
                binding.releaseDate.setText(String.valueOf(movie.getId()));
                binding.overview.setText(movie.getOverview());

                String imageUrl = BASE_IMAGE_URL + BACKDROP_SIZE_W780 + movie.getBackdrop_path();
                Picasso.get().load(imageUrl).fit().centerCrop().placeholder(R.drawable.picture_template).into(binding.coverImageView);
                binding.coverImageView.setVisibility(View.VISIBLE);
                registerMovieDetailsObserver(movie.getId());
            }
        });
    }

    private void registerMovieDetailsObserver(Integer movieId) {
        movieDetailsViewModel.getMovieCast(movieId).observe(getViewLifecycleOwner(), new Observer<Resource<List<MovieCredits.Cast>>>() {
            @Override
            public void onChanged(Resource<List<MovieCredits.Cast>> listResource) {
                // movieDetailsViewModel.getMovieCast(topRatedMovieId).removeObserver(this);
                if (listResource.getData() != null) {
                    Log.d(Constants.LOG, " status: " + listResource.getStatus() + " list size: " + listResource.getData().size());
                    castAdapter = new CastAdapter(getContext(), listResource.getData());
                    castAdapter.setClickListener(MovieDetailsFragment.this::onItemClick);
                    binding.castRecyclerView.setAdapter(castAdapter);
                }
            }
        });
    }

    @Override
    public void onItemClick(View view, Integer actorId, int position) {
        sharedViewModel.changeToActorFragment(position, actorId);
    }
}