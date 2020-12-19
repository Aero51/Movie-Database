package com.aero51.moviedatabase.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aero51.moviedatabase.R;
import com.aero51.moviedatabase.repository.model.tmdb.credits.Cast;
import com.aero51.moviedatabase.repository.model.tmdb.movie.TopRatedMovie;
import com.aero51.moviedatabase.ui.adapter.CastAdapter;
import com.aero51.moviedatabase.utils.Constants;
import com.aero51.moviedatabase.utils.Resource;
import com.aero51.moviedatabase.viewmodel.MovieDetailsViewModel;
import com.aero51.moviedatabase.viewmodel.SharedViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.aero51.moviedatabase.utils.Constants.BACKDROP_SIZE_W780;
import static com.aero51.moviedatabase.utils.Constants.BASE_IMAGE_URL;

public class TopRatedMovieDetailsFragment extends Fragment implements CastAdapter.ItemClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private MovieDetailsViewModel movieDetailsViewModel;
    private ImageView cover_image_view;
    private TextView title_text_view;
    private TextView release_date_text_view;
    private TextView overview_text_view;

    private RecyclerView castRecyclerView;
    private CastAdapter castAdapter;

    private SharedViewModel sharedViewModel;

    public static TopRatedMovieDetailsFragment newInstance(String param1, String param2) {
        TopRatedMovieDetailsFragment fragment = new TopRatedMovieDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedViewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);
        movieDetailsViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(MovieDetailsViewModel.class);
        if (getArguments() != null) {
            //  topRatedMovie = (TopRatedMovie) getArguments().getSerializable("TopRatedMovie");
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // viewModel = new ViewModelProvider(requireActivity()).get(MovieDetailsViewModel.class);
        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);
        // Log.d(Constants.LOG, "TopRatedMovieDetailsFragment onCreateView " );
        cover_image_view = getActivity().findViewById(R.id.expandedImage);
        title_text_view = view.findViewById(R.id.title);
        release_date_text_view = view.findViewById(R.id.releaseDate);
        overview_text_view = view.findViewById(R.id.overview);

        castRecyclerView = view.findViewById(R.id.cast_recycler_view);
        castRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        castRecyclerView.setLayoutManager(linearLayoutManager);
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

        return view;
    }

    public void showBackButton(boolean show) {
        if (getActivity() instanceof AppCompatActivity) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(show);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        //  Log.d(Constants.LOG, "TopRatedMovieDetailsFragment onStop " );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Log.d(Constants.LOG, "TopRatedMovieDetailsFragment onDestroyView " );
        //sharedViewModel.getLiveDataTopRatedMovie().removeObservers(getViewLifecycleOwner());
        // movieDetailsViewModel.getMovieCast(topRatedMovieIds).removeObservers(getViewLifecycleOwner());
    }

    private void registerSharedViewModelObserver() {
        sharedViewModel.getLiveDataTopRatedMovie().observe(getViewLifecycleOwner(), new Observer<TopRatedMovie>() {
            @Override
            public void onChanged(TopRatedMovie topRatedMovie) {
                // sharedViewModel.getLiveDataTopRatedMovie().removeObserver(this);
                title_text_view.setText(topRatedMovie.getTitle());
                release_date_text_view.setText(String.valueOf(topRatedMovie.getId()));
                overview_text_view.setText(topRatedMovie.getOverview());

                String imageUrl = BASE_IMAGE_URL + BACKDROP_SIZE_W780 + topRatedMovie.getBackdrop_path();
                Picasso.get().load(imageUrl).fit().centerCrop().placeholder(R.drawable.picture_template).into(cover_image_view);
                cover_image_view.setVisibility(View.VISIBLE);
                registerMovieDetailsObserver(topRatedMovie.getId());
            }
        });
    }

    private void registerMovieDetailsObserver(Integer topRatedMovieId) {
        movieDetailsViewModel.getMovieCast(topRatedMovieId).observe(getViewLifecycleOwner(), new Observer<Resource<List<Cast>>>() {
            @Override
            public void onChanged(Resource<List<Cast>> listResource) {
                // movieDetailsViewModel.getMovieCast(topRatedMovieId).removeObserver(this);
                Log.d(Constants.LOG, "getMovieCast code: " + listResource.code + " , status: " + listResource.status + " list size: " + listResource.data.size());
                castAdapter = new CastAdapter(getContext(), listResource.data);
                castAdapter.setClickListener(TopRatedMovieDetailsFragment.this::onItemClick);
                castRecyclerView.setAdapter(castAdapter);
            }
        });
    }

    public static void setImageUrl(ImageView view, String url) {
        if (url != null) {
            // Picasso.get().load(BIG_IMAGE_URL_PREFIX + url).into(view);
        }
    }

    @Override
    public void onItemClick(View view, Cast cast, int position) {
        sharedViewModel.changeToActorFragment(position, cast);
    }
}
