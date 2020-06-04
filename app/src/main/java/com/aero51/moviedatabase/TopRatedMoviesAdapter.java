package com.aero51.moviedatabase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;
import java.util.List;

public class TopRatedMoviesAdapter extends RecyclerView.Adapter<TopRatedMovieHolder> {
    //RecyclerView.ViewHolder

    private List<Top_Rated_Result> mlist;
    private ItemClickListener itemClickListener;

    public TopRatedMoviesAdapter(ItemClickListener itemClickListener) {
        mlist = new ArrayList<>();
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

        Top_Rated_Result currentResult = mlist.get(position);
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


    @Override
    public int getItemCount() {
        // return  mlist == null ? 0 : mlist.size();

        // if (mlist == null) return 0;
        //else return  mlist.size();
        return mlist.size();
    }

    public void addData(List<Top_Rated_Result> list) {
        final int positionStart = mlist.size();
        mlist.addAll(list);
        notifyItemRangeInserted(positionStart, mlist.size());
    }


}
