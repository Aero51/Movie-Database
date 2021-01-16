package com.aero51.moviedatabase.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.aero51.moviedatabase.R;
import com.aero51.moviedatabase.repository.model.NetworkState;
import com.aero51.moviedatabase.repository.model.tmdb.movie.NowPlayingMovie;
import com.aero51.moviedatabase.ui.viewholder.NowPlayingngMovieHolder;
import com.aero51.moviedatabase.utils.MovieClickListener;

public class NowPlayingMoviesPagedListAdapter extends PagedListAdapter<NowPlayingMovie, RecyclerView.ViewHolder> {
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
        NowPlayingMovie currentResult = getItem(position);
        ((NowPlayingngMovieHolder) holder).bindTo(currentResult, position);
    }


    private static DiffUtil.ItemCallback<NowPlayingMovie> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<NowPlayingMovie>() {
                @Override
                public boolean areItemsTheSame(NowPlayingMovie oldItem, NowPlayingMovie newItem) {
                    return oldItem.getId().equals(newItem.getId());
                }

                @Override
                public boolean areContentsTheSame(NowPlayingMovie oldItem, NowPlayingMovie newItem) {
                    return oldItem.getTitle().equals(newItem.getTitle());
                }
            };


}
