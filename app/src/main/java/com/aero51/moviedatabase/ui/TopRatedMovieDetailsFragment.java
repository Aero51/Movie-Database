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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aero51.moviedatabase.R;
import com.aero51.moviedatabase.repository.model.credits.Cast;
import com.aero51.moviedatabase.repository.model.movie.TopRatedMovie;
import com.aero51.moviedatabase.ui.adapter.CastAdapter;
import com.aero51.moviedatabase.utils.Resource;
import com.aero51.moviedatabase.viewmodel.MovieDetailsViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.aero51.moviedatabase.utils.Constants.BACKDROP_SIZE_W780;
import static com.aero51.moviedatabase.utils.Constants.BASE_IMAGE_URL;

public class TopRatedMovieDetailsFragment extends Fragment implements CastAdapter.ItemClickListener{
    private MovieDetailsViewModel viewModel;
    private ImageView cover_image_view;
    private TextView title_text_view;
    private TextView release_date_text_view;
    private TextView overview_text_view;

    private RecyclerView castRecyclerView;
    private CastAdapter castAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate view and obtain an instance of the binding class.
        // viewModel = new ViewModelProvider(requireActivity()).get(MovieDetailsViewModel.class);
        viewModel = new ViewModelProvider(getActivity(), ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(MovieDetailsViewModel.class);
        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);

        cover_image_view = view.findViewById(R.id.cover);
        title_text_view = view.findViewById(R.id.title);
        release_date_text_view = view.findViewById(R.id.releaseDate);
        overview_text_view = view.findViewById(R.id.overview);

        castRecyclerView = view.findViewById(R.id.cast_recycler_view);
        castRecyclerView.setHasFixedSize(true);
        castRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));

        viewModel.getTopRatedMovie().observe(getViewLifecycleOwner(), new Observer<TopRatedMovie>() {
            @Override
            public void onChanged(TopRatedMovie top_rated_movie) {
                title_text_view.setText(top_rated_movie.getTitle());
                release_date_text_view.setText(String.valueOf(top_rated_movie.getId()));
                overview_text_view.setText(top_rated_movie.getOverview());

                String imageUrl = BASE_IMAGE_URL + BACKDROP_SIZE_W780 + top_rated_movie.getBackdrop_path();
                Picasso.get().load(imageUrl).into(cover_image_view);
            }
        });
        viewModel.getTopRatedMovieCast().observe(getViewLifecycleOwner(), new Observer<Resource<List<Cast>>>() {
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
    public void onItemClick(View view, int position) {
        Log.d("moviedatabaselog", "Cast item: " +position);
    }
}
