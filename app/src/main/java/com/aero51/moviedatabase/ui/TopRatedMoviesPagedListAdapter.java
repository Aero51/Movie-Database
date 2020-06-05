package com.aero51.moviedatabase.ui;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;

import com.aero51.moviedatabase.R;
import com.aero51.moviedatabase.repository.Top_Rated_Result;
import com.aero51.moviedatabase.utils.ItemClickListener;

public class TopRatedMoviesPagedListAdapter extends PagedListAdapter<Top_Rated_Result,TopRatedMovieHolder> {
    private ItemClickListener itemClickListener;;

    protected TopRatedMoviesPagedListAdapter(ItemClickListener itemClickListener) {
        super(DIFF_CALLBACK);
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public TopRatedMovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_item, parent, false);
        return new TopRatedMovieHolder(itemView, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TopRatedMovieHolder holder, int position) {
        Top_Rated_Result currentResult = getItem(position);
        //  ((TopRatedMovieHolder) holder).bindTo(currentResult,position);
        holder.bindTo(currentResult, position);
/*
        switch (getItemViewType(position)) {
            case R.layout.movie_item:
                ((MovieViewHolder) holder).bindTo(getItem(position));
                break;
            case R.layout.network_state_item:
                ((NetworkStateItemViewHolder) holder).bindView(networkState);
                break;
        }
*/

    }
    private static DiffUtil.ItemCallback<Top_Rated_Result> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Top_Rated_Result>() {
                @Override
                public boolean areItemsTheSame(Top_Rated_Result oldItem, Top_Rated_Result newItem) {
                    Log.d("moviedatabaselog", "areItemsTheSame: oldItem.getId(): " + oldItem.getId()+" ,newItem.getId(): "+newItem.getId());
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(Top_Rated_Result oldItem, Top_Rated_Result newItem) {
                    Log.d("moviedatabaselog", "areContentsTheSame: oldItem.getTitle: " + oldItem.getTitle()+" ,newItem.getTitle: "+newItem.getTitle());
                    return oldItem.getTitle()==newItem.getTitle();
                  // return oldItem.equals(newItem);
                }
            };
}
