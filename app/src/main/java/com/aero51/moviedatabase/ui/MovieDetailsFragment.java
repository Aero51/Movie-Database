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

import com.aero51.moviedatabase.R;
import com.aero51.moviedatabase.repository.model.Top_Rated_Result;
import com.aero51.moviedatabase.viewmodel.MovieDetailsViewModel;
import com.squareup.picasso.Picasso;

public class MovieDetailsFragment extends Fragment {
    private MovieDetailsViewModel viewModel;

    private ImageView cover_image_view;
    private TextView title_text_view;
    private TextView release_date_text_view;
    private TextView overview_text_view;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate view and obtain an instance of the binding class.
        viewModel = new ViewModelProvider(requireActivity()).get(MovieDetailsViewModel.class);
        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);

        cover_image_view = view.findViewById(R.id.cover);
        title_text_view = view.findViewById(R.id.title);
        release_date_text_view = view.findViewById(R.id.releaseDate);
        overview_text_view = view.findViewById(R.id.overview);


        viewModel.getMovie().observe(this, new Observer<Top_Rated_Result>() {
            @Override
            public void onChanged(Top_Rated_Result top_rated_result) {
                Log.d("moviedatabaselog", "MovieDetailsFragment onChanged movie_page: title: " +top_rated_result.getTitle());
                title_text_view.setText(top_rated_result.getTitle());
                release_date_text_view.setText(top_rated_result.getRelease_date().toString());
                overview_text_view.setText(top_rated_result.getOverview());

                //poster
                //  String baseUrl = "https://image.tmdb.org/t/p/w92";
                //backdrop  "backdrop_sizes": [
                //      "w300",
                //      "w780",
                //      "w1280",
                //      "original"
                // "poster_sizes": [
                //      "w92",
                //      "w154",
                //      "w185",
                //      "w342",
                //      "w500",
                //      "w780",
                //      "original"

                String baseUrl = "https://image.tmdb.org/t/p/w780";
                String imageUrl = baseUrl + top_rated_result.getBackdrop_path();
                Picasso.get().load(imageUrl).into(cover_image_view);

            }
        });


        return view;
    }

    public static void setImageUrl(ImageView view, String url) {
        if (url != null) {
           // Picasso.get().load(BIG_IMAGE_URL_PREFIX + url).into(view);
        }
    }
}
