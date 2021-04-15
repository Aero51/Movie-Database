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
import com.aero51.moviedatabase.repository.model.tmdb.movie.NowPlayingMoviesPage;
import com.aero51.moviedatabase.utils.Constants;
import com.aero51.moviedatabase.utils.MovieClickListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import static com.aero51.moviedatabase.utils.Constants.BASE_IMAGE_URL;
import static com.aero51.moviedatabase.utils.Constants.POSTER_SIZE_W154;

public class NowPlayingMoviesPagedListAdapter extends PagedListAdapter<NowPlayingMoviesPage.NowPlayingMovie, RecyclerView.ViewHolder> {
    private MovieClickListener itemClickListener;
    private NetworkState networkState;

    public NowPlayingMoviesPagedListAdapter(MovieClickListener itemClickListener) {
        super(DIFF_CALLBACK);
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.movie_item, parent, false);
        NowPlayingngMovieHolder viewHolder = new NowPlayingngMovieHolder(view, itemClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        NowPlayingMoviesPage.NowPlayingMovie currentResult = getItem(position);
        ((NowPlayingngMovieHolder) holder).bindTo(currentResult, position);
    }


    private static DiffUtil.ItemCallback<NowPlayingMoviesPage.NowPlayingMovie> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<NowPlayingMoviesPage.NowPlayingMovie>() {
                @Override
                public boolean areItemsTheSame(NowPlayingMoviesPage.NowPlayingMovie oldItem, NowPlayingMoviesPage.NowPlayingMovie newItem) {
                    return oldItem.getId().equals(newItem.getId());
                }

                @Override
                public boolean areContentsTheSame(NowPlayingMoviesPage.NowPlayingMovie oldItem, NowPlayingMoviesPage.NowPlayingMovie newItem) {
                    return oldItem.getTitle().equals(newItem.getTitle());
                }
            };



    public static class NowPlayingngMovieHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private NowPlayingMoviesPage.NowPlayingMovie result;
        
        private int adapterPosition;

        private ImageView imageView;
        //private TextView textViewPosition;
        private TextView textViewtitle;
        private TextView textViewVoteAverage;
        private MovieClickListener itemClickListener;


        public NowPlayingngMovieHolder(@NonNull View itemView, MovieClickListener itemClickListener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view_program);
            //textViewPosition = itemView.findViewById(R.id.text_view_position);
            textViewtitle = itemView.findViewById(R.id.text_view_title);

            this.itemClickListener = itemClickListener;
            itemView.setOnClickListener(this);

        }

        public void bindTo(NowPlayingMoviesPage.NowPlayingMovie result, int position) {
            this.result = result;
            this.adapterPosition = position;

            //textViewPosition.setText(String.valueOf(position + 1));
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
            if (itemClickListener != null&& adapterPosition != RecyclerView.NO_POSITION) {
                itemClickListener.onObjectItemClick(result, adapterPosition); // call the onClick in the OnItemClickListener
                Log.d(Constants.LOG, " Item clicked inside now playing holder : " + adapterPosition);
            }
        }
    }
}
