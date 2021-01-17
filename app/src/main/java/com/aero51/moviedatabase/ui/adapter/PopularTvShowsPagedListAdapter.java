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
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.PopularTvShowsPage;
import com.aero51.moviedatabase.ui.viewholder.PopularTvShowHolder;
import com.aero51.moviedatabase.utils.MovieClickListener;

public class PopularTvShowsPagedListAdapter  extends PagedListAdapter<PopularTvShowsPage.PopularTvShow, RecyclerView.ViewHolder> {
    private MovieClickListener itemClickListener;
    private NetworkState networkState;



    public PopularTvShowsPagedListAdapter(MovieClickListener itemClickListener) {
        super(DIFF_CALLBACK);
        this.itemClickListener = itemClickListener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.movie_item, parent, false);
        PopularTvShowHolder viewHolder = new PopularTvShowHolder(view, itemClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        PopularTvShowsPage.PopularTvShow currentResult = getItem(position);
        ((PopularTvShowHolder) holder).bindTo(currentResult, position);
    }


    private static DiffUtil.ItemCallback<PopularTvShowsPage.PopularTvShow> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<PopularTvShowsPage.PopularTvShow>() {
                @Override
                public boolean areItemsTheSame(PopularTvShowsPage.PopularTvShow oldItem, PopularTvShowsPage.PopularTvShow newItem) {
                    return oldItem.getId().equals(newItem.getId());
                }

                @Override
                public boolean areContentsTheSame(PopularTvShowsPage.PopularTvShow oldItem, PopularTvShowsPage.PopularTvShow newItem) {
                    return oldItem.getName().equals(newItem.getName());
                }
            };
}
