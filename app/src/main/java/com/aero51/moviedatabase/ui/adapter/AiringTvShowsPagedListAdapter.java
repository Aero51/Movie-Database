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
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.AiringTvShowsPage;
import com.aero51.moviedatabase.ui.viewholder.AiringTvShowHolder;
import com.aero51.moviedatabase.utils.MovieClickListener;


public class AiringTvShowsPagedListAdapter extends PagedListAdapter<AiringTvShowsPage.AiringTvShow, RecyclerView.ViewHolder> {
    private MovieClickListener itemClickListener;
    private NetworkState networkState;



    public AiringTvShowsPagedListAdapter(MovieClickListener itemClickListener) {
        super(DIFF_CALLBACK);
        this.itemClickListener = itemClickListener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.movie_item, parent, false);
        AiringTvShowHolder viewHolder = new AiringTvShowHolder(view, itemClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        AiringTvShowsPage.AiringTvShow currentResult = getItem(position);
        ((AiringTvShowHolder) holder).bindTo(currentResult, position);
    }


    private static DiffUtil.ItemCallback<AiringTvShowsPage.AiringTvShow> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<AiringTvShowsPage.AiringTvShow>() {
                @Override
                public boolean areItemsTheSame(AiringTvShowsPage.AiringTvShow oldItem, AiringTvShowsPage.AiringTvShow newItem) {
                    return oldItem.getId().equals(newItem.getId());
                }

                @Override
                public boolean areContentsTheSame(AiringTvShowsPage.AiringTvShow oldItem, AiringTvShowsPage.AiringTvShow newItem) {
                    return oldItem.getName().equals(newItem.getName());
                }
            };
}
