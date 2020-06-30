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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aero51.moviedatabase.R;
import com.aero51.moviedatabase.repository.model.tmdb.credits.Cast;
import com.aero51.moviedatabase.repository.model.tmdb.movie.TopRatedMovie;
import com.aero51.moviedatabase.ui.adapter.CastAdapter;
import com.aero51.moviedatabase.utils.Resource;
import com.aero51.moviedatabase.viewmodel.MovieDetailsViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.aero51.moviedatabase.utils.Constants.BACKDROP_SIZE_W780;
import static com.aero51.moviedatabase.utils.Constants.BASE_IMAGE_URL;

public class TopRatedMovieDetailsFragment extends Fragment implements CastAdapter.ItemClickListener{
    private MovieDetailsViewModel movieDetailsViewModel;
    private ImageView cover_image_view;
    private TextView title_text_view;
    private TextView release_date_text_view;
    private TextView overview_text_view;

    private RecyclerView castRecyclerView;
    private CastAdapter castAdapter;

    private TopRatedMovie topRatedMovie;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            topRatedMovie = (TopRatedMovie) getArguments().getSerializable("TopRatedMovie");
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // viewModel = new ViewModelProvider(requireActivity()).get(MovieDetailsViewModel.class);
        movieDetailsViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(MovieDetailsViewModel.class);
        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);

        cover_image_view = view.findViewById(R.id.cover);
        title_text_view = view.findViewById(R.id.title);
        release_date_text_view = view.findViewById(R.id.releaseDate);
        overview_text_view = view.findViewById(R.id.overview);

        castRecyclerView = view.findViewById(R.id.cast_recycler_view);
        castRecyclerView.setHasFixedSize(true);
        castRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));

        title_text_view.setText(topRatedMovie.getTitle());
        release_date_text_view.setText(String.valueOf(topRatedMovie.getId()));
        overview_text_view.setText(topRatedMovie.getOverview());

        String imageUrl = BASE_IMAGE_URL + BACKDROP_SIZE_W780 + topRatedMovie.getBackdrop_path();
        Picasso.get().load(imageUrl).into(cover_image_view);

        movieDetailsViewModel.getMovieCast(topRatedMovie.getId()).observe(getViewLifecycleOwner(), new Observer<Resource<List<Cast>>>() {
            @Override
            public void onChanged(Resource<List<Cast>> listResource) {
                Log.d("moviedatabaselog", "getMovieCast code: " + listResource.code + " , status: " + listResource.status + " list size: " + listResource.data.size());
                castAdapter=new CastAdapter(getContext(),listResource.data);
                castAdapter.setClickListener(TopRatedMovieDetailsFragment.this::onItemClick);
                castRecyclerView.setAdapter(castAdapter);
            }
        });
        return view;
    }

    public static void setImageUrl(ImageView view, String url) {
        if (url != null) {
            // Picasso.get().load(BIG_IMAGE_URL_PREFIX + url).into(view);
        }
    }

    @Override
    public void onItemClick(View view,Cast cast, int position) {
        Log.d("moviedatabaselog", "Cast item: " +position);
        ActorFragment actorFragment=ActorFragment.newInstance("test");
        Bundle bundle = new Bundle();
        bundle.putSerializable("Cast", cast);
        actorFragment.setArguments(bundle);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
       // transaction.replace(R.id.fragmentsContainer, actorFragment);
        transaction.addToBackStack(null);
        // Commit the transaction
        transaction.commit();
    }
}
