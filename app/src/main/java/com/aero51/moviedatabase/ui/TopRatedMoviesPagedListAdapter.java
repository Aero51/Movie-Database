package com.aero51.moviedatabase.ui;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.aero51.moviedatabase.R;
import com.aero51.moviedatabase.repository.NetworkState;
import com.aero51.moviedatabase.repository.Top_Rated_Result;
import com.aero51.moviedatabase.utils.ItemClickListener;

public class TopRatedMoviesPagedListAdapter extends PagedListAdapter<Top_Rated_Result, RecyclerView.ViewHolder> {
    private ItemClickListener itemClickListener;
    private NetworkState networkState;

    protected TopRatedMoviesPagedListAdapter(ItemClickListener itemClickListener) {
        super(DIFF_CALLBACK);
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == R.layout.movie_item) {
            View view = layoutInflater.inflate(R.layout.movie_item, parent, false);
            TopRatedMovieHolder viewHolder = new TopRatedMovieHolder(view, itemClickListener);
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
        Top_Rated_Result currentResult = getItem(position);
        //  ((TopRatedMovieHolder) holder).bindTo(currentResult,position);
        switch (getItemViewType(position)) {
            case R.layout.movie_item:
                ((TopRatedMovieHolder) holder).bindTo(currentResult, position);
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


    public void setNetworkState(NetworkState newNetworkState) {
        Log.d("moviedatabaselog", "setNetworkState status: " + newNetworkState.getStatus() + " ,msg: " + newNetworkState.getMsg());
        NetworkState previousState = this.networkState;
        boolean previousExtraRow = hasExtraRow();
        this.networkState = newNetworkState;
        boolean newExtraRow = hasExtraRow();
        if (previousExtraRow != newExtraRow) {
            if (previousExtraRow) {
                notifyItemRemoved(getItemCount());
            } else {
                notifyItemInserted(getItemCount());
            }
        } else if (newExtraRow && previousState != newNetworkState) {
            notifyItemChanged(getItemCount() - 1);
        }
    }

    private boolean hasExtraRow() {
        if (networkState != null && networkState != NetworkState.LOADED) {
            return true;
        } else {
            return false;
        }
    }


    private static DiffUtil.ItemCallback<Top_Rated_Result> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Top_Rated_Result>() {
                @Override
                public boolean areItemsTheSame(Top_Rated_Result oldItem, Top_Rated_Result newItem) {

                    // Log.d("moviedatabaselog", "areItemsTheSame: oldItem.getId(): " + oldI+" ,newItem.getId(): "+newI);
                    //  Log.d("moviedatabaselog",String.valueOf(oldItem.getId().equals(newItem.getId())) );
                    // Log.d("moviedatabaselog",String.valueOf(oldItem.getId()==newItem.getId() ));
                    // return oldItem.getId().equals(newItem.getId());
                    int a = oldItem.getId();
                    int b = newItem.getId();
                    boolean c = (a == b);
                    Log.d("moviedatabaselog", c + " ,oldid: " + a + " ,newid: " + b);
                    return c;
                }

                @Override
                public boolean areContentsTheSame(Top_Rated_Result oldItem, Top_Rated_Result newItem) {
                    Log.d("moviedatabaselog", "areContentsTheSame: oldItem.getTitle: " + oldItem.getTitle() + " ,newItem.getTitle: " + newItem.getTitle());
                    return oldItem.getTitle().equals(newItem.getTitle());
                    // return oldItem.equals(newItem);
                }
            };



}
