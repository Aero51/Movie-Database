package com.aero51.moviedatabase.ui.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.aero51.moviedatabase.R;
import com.aero51.moviedatabase.repository.model.NetworkState;
import com.aero51.moviedatabase.repository.model.PopularMovie;
import com.aero51.moviedatabase.ui.viewholder.NetworkStateItemViewHolder;
import com.aero51.moviedatabase.ui.viewholder.PopularMovieHolder;
import com.aero51.moviedatabase.utils.PopularItemClickListener;


public class PopularMoviesPagedListAdapter extends PagedListAdapter<PopularMovie, RecyclerView.ViewHolder> {
    private PopularItemClickListener itemClickListener;
    private NetworkState networkState;

    public PopularMoviesPagedListAdapter(PopularItemClickListener itemClickListener) {
        super(DIFF_CALLBACK);
        this.itemClickListener = itemClickListener;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == R.layout.movie_item) {
            View view = layoutInflater.inflate(R.layout.movie_item, parent, false);
            PopularMovieHolder viewHolder = new PopularMovieHolder(view, itemClickListener);
            return viewHolder;
        } else if (viewType == R.layout.network_state_item) {
            View view = layoutInflater.inflate(R.layout.network_state_item, parent, false);
            return new NetworkStateItemViewHolder(view);
        } else {
            throw new IllegalArgumentException("unknown view type");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        PopularMovie currentResult = getItem(position);
        //  ((TopRatedMovieHolder) holder).bindTo(currentResult,position);
        switch (getItemViewType(position)) {
            case R.layout.movie_item:
                ((PopularMovieHolder) holder).bindTo(currentResult, position);
                break;
            case R.layout.network_state_item:
                ((NetworkStateItemViewHolder) holder).bindView(networkState);
                break;
        }
    }
    @Override
    public int getItemViewType(int position) {

        if (hasExtraRow() && position == getItemCount() - 1) {
            Log.d("moviedatabaselog", "position: " + position + " itemcount: " + getItemCount());
            return R.layout.network_state_item;
        } else {
            return R.layout.movie_item;
        }
    }
    private boolean hasExtraRow() {
        if (networkState != null && networkState != NetworkState.LOADED) {
            return true;
        } else {
            return false;
        }
    }


    private static DiffUtil.ItemCallback<PopularMovie> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<PopularMovie>() {
                @Override
                public boolean areItemsTheSame(PopularMovie oldItem, PopularMovie newItem) {
                    return oldItem.getId().equals(newItem.getId());
                }

                @Override
                public boolean areContentsTheSame(PopularMovie oldItem, PopularMovie newItem) {
                    return oldItem.getTitle().equals(newItem.getTitle());
                }
            };
}
