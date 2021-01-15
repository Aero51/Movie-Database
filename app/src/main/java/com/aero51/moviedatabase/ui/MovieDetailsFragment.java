package com.aero51.moviedatabase.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aero51.moviedatabase.R;
import com.aero51.moviedatabase.repository.model.tmdb.credits.Cast;
import com.aero51.moviedatabase.repository.model.tmdb.movie.Movie;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieDetailsFragment extends Fragment implements CastAdapter.ItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
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



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MovieDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MovieDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MovieDetailsFragment newInstance(String param1, String param2) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedViewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);
        movieDetailsViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(MovieDetailsViewModel.class);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);
        // Log.d(Constants.LOG, "TopRatedMovieDetailsFragment onCreateView " );
        //cover_image_view = getActivity().findViewById(R.id.expandedImage);
        cover_image_view = view.findViewById(R.id.cover);
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

    private void registerSharedViewModelObserver() {
        sharedViewModel.getLiveDataMovie().observe(getViewLifecycleOwner(), new Observer<Movie>() {
            @Override
            public void onChanged(Movie movie) {
                title_text_view.setText(movie.getTitle());
                release_date_text_view.setText(String.valueOf(movie.getId()));
                overview_text_view.setText(movie.getOverview());

                String imageUrl = BASE_IMAGE_URL + BACKDROP_SIZE_W780 + movie.getBackdrop_path();
                Picasso.get().load(imageUrl).fit().centerCrop().placeholder(R.drawable.picture_template).into(cover_image_view);
                cover_image_view.setVisibility(View.VISIBLE);
                registerMovieDetailsObserver(movie.getId());
            }
        });
    }
    private void registerMovieDetailsObserver(Integer movieId) {
        movieDetailsViewModel.getMovieCast(movieId).observe(getViewLifecycleOwner(), new Observer<Resource<List<Cast>>>() {
            @Override
            public void onChanged(Resource<List<Cast>> listResource) {
                // movieDetailsViewModel.getMovieCast(topRatedMovieId).removeObserver(this);
                Log.d(Constants.LOG, "getMovieCast code: " + listResource.code + " , status: " + listResource.status + " list size: " + listResource.data.size());
                castAdapter = new CastAdapter(getContext(), listResource.data);
                castAdapter.setClickListener(MovieDetailsFragment.this::onItemClick);
                castRecyclerView.setAdapter(castAdapter);
            }
        });
    }

    @Override
    public void onItemClick(View view, Integer actorId, int position) {
        sharedViewModel.changeToActorFragment(position, actorId);
    }
}