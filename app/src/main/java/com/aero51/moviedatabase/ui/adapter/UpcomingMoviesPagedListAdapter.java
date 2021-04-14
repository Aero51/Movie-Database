package com.aero51.moviedatabase.ui.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.aero51.moviedatabase.R;
import com.aero51.moviedatabase.repository.model.NetworkState;
import com.aero51.moviedatabase.repository.model.tmdb.movie.UpcomingMoviesPage;
import com.aero51.moviedatabase.utils.Constants;
import com.aero51.moviedatabase.utils.MovieClickListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import static com.aero51.moviedatabase.utils.Constants.BASE_IMAGE_URL;
import static com.aero51.moviedatabase.utils.Constants.POSTER_SIZE_W154;

public class UpcomingMoviesPagedListAdapter extends PagedListAdapter<UpcomingMoviesPage.UpcomingMovie, RecyclerView.ViewHolder> {
    private MovieClickListener itemClickListener;
    private NetworkState networkState;

    public UpcomingMoviesPagedListAdapter(MovieClickListener itemClickListener) {
        super(DIFF_CALLBACK);
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.movie_item, parent, false);
        UpcomingMovieHolder viewHolder = new UpcomingMovieHolder(view, itemClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        UpcomingMoviesPage.UpcomingMovie currentResult = getItem(position);
        ((UpcomingMovieHolder) holder).bindTo(currentResult, position);
    }


    private static DiffUtil.ItemCallback<UpcomingMoviesPage.UpcomingMovie> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<UpcomingMoviesPage.UpcomingMovie>() {
                @Override
                public boolean areItemsTheSame(UpcomingMoviesPage.UpcomingMovie oldItem, UpcomingMoviesPage.UpcomingMovie newItem) {
                    return oldItem.getId().equals(newItem.getId());
                }

                @Override
                public boolean areContentsTheSame(UpcomingMoviesPage.UpcomingMovie oldItem, UpcomingMoviesPage.UpcomingMovie newItem) {
                    return oldItem.getTitle().equals(newItem.getTitle());
                }
            };

    public static class UpcomingMovieHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private UpcomingMoviesPage.UpcomingMovie result;
        private int position;

        private ImageView imageView;
        private TextView textViewPosition;
        private TextView textViewtitle;
        private TextView textViewVoteAverage;
        private MovieClickListener itemClickListener;


        public UpcomingMovieHolder(@NonNull View itemView, MovieClickListener itemClickListener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view_program);
            textViewPosition = itemView.findViewById(R.id.text_view_position);
            textViewtitle = itemView.findViewById(R.id.text_view_title);

            this.itemClickListener = itemClickListener;
            itemView.setOnClickListener(this);

        }

        public void bindTo(UpcomingMoviesPage.UpcomingMovie result, int position) {
            this.result = result;
            this.position = position;

            textViewPosition.setText(String.valueOf(position + 1));
            textViewtitle.setText(result.getTitle());

            String imageUrl = BASE_IMAGE_URL + POSTER_SIZE_W154 + result.getPoster_path();
            //.placeholder(R.drawable.picture_template)
            Picasso.get().load(imageUrl).fit().centerCrop().into(imageView, new Callback() {
                @Override
                public void onSuccess() {
                }

                @Override
                public void onError(Exception e) {
                    imageView.setBackgroundResource(R.drawable.picture_template);
                }
            });

        }

        @Override
        public void onClick(View v) {
            if (itemClickListener != null && position != RecyclerView.NO_POSITION) {
                itemClickListener.onObjectItemClick(result, position); // call the onClick in the OnItemClickListener
                Log.d(Constants.LOG, " Item clicked inside upcoming holder : " + position);
            }
        }

    }
}
