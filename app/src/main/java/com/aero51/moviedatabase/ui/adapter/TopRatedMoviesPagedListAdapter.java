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
import com.aero51.moviedatabase.repository.model.tmdb.movie.TopRatedMovie;
import com.aero51.moviedatabase.ui.viewholder.TopRatedMovieHolder;
import com.aero51.moviedatabase.utils.TopRatedItemClickListener;

public class TopRatedMoviesPagedListAdapter extends PagedListAdapter<TopRatedMovie, RecyclerView.ViewHolder> {
    private TopRatedItemClickListener itemClickListener;
    private NetworkState networkState;

    public TopRatedMoviesPagedListAdapter(TopRatedItemClickListener itemClickListener) {
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
        TopRatedMovie currentResult = getItem(position);
          ((TopRatedMovieHolder) holder).bindTo(currentResult,position);
    }




    private static DiffUtil.ItemCallback<TopRatedMovie> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<TopRatedMovie>() {
                @Override
                public boolean areItemsTheSame(TopRatedMovie oldItem, TopRatedMovie newItem) {
                    return oldItem.getId().equals(newItem.getId());
                }

                @Override
                public boolean areContentsTheSame(TopRatedMovie oldItem, TopRatedMovie newItem) {
                    return oldItem.getTitle().equals(newItem.getTitle());
                }
            };



}
