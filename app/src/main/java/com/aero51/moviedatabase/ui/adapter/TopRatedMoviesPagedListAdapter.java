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

import com.aero51.moviedatabase.repository.model.tmdb.movie.TopRatedMoviesPage;
import com.aero51.moviedatabase.utils.Constants;
import com.aero51.moviedatabase.utils.MovieClickListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import static com.aero51.moviedatabase.utils.Constants.BASE_IMAGE_URL;
import static com.aero51.moviedatabase.utils.Constants.POSTER_SIZE_W154;


public class TopRatedMoviesPagedListAdapter extends PagedListAdapter<TopRatedMoviesPage.TopRatedMovie, RecyclerView.ViewHolder> {
    private MovieClickListener itemClickListener;
    private NetworkState networkState;

    public TopRatedMoviesPagedListAdapter(MovieClickListener itemClickListener) {
        super(DIFF_CALLBACK);
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.movie_item, parent, false);
        TopRatedMovieHolder viewHolder = new TopRatedMovieHolder(view, itemClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TopRatedMoviesPage.TopRatedMovie currentResult = getItem(position);
        ((TopRatedMovieHolder) holder).bindTo(currentResult, position);
    }


    private static DiffUtil.ItemCallback<TopRatedMoviesPage.TopRatedMovie> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<TopRatedMoviesPage.TopRatedMovie>() {
                @Override
                public boolean areItemsTheSame(TopRatedMoviesPage.TopRatedMovie oldItem, TopRatedMoviesPage.TopRatedMovie newItem) {
                    return oldItem.getId().equals(newItem.getId());
                }

                @Override
                public boolean areContentsTheSame(TopRatedMoviesPage.TopRatedMovie oldItem, TopRatedMoviesPage.TopRatedMovie newItem) {
                    return oldItem.getTitle().equals(newItem.getTitle());
                }
            };


    public static class TopRatedMovieHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TopRatedMoviesPage.TopRatedMovie result;
        private int position;

        private ImageView imageView;
        private TextView textViewPosition;
        private TextView textViewtitle;
        private TextView textViewVoteAverage;
        private MovieClickListener itemClickListener;


        public TopRatedMovieHolder(@NonNull View itemView, MovieClickListener itemClickListener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view_program);
            textViewPosition = itemView.findViewById(R.id.text_view_position);
            textViewtitle = itemView.findViewById(R.id.text_view_title);
            this.itemClickListener = itemClickListener;
            itemView.setOnClickListener(this);

        }

        public void bindTo(TopRatedMoviesPage.TopRatedMovie result, int position) {
            this.result = result;
            this.position=position;

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
            if (itemClickListener != null&& position != RecyclerView.NO_POSITION) {
                itemClickListener.onObjectItemClick(result,position); // call the onClick in the OnItemClickListener
                Log.d(Constants.LOG, " Item clicked inside top rated holder : " + position);
            }
        }
    }
}
