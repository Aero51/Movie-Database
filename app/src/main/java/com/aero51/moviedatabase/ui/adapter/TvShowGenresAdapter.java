package com.aero51.moviedatabase.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.aero51.moviedatabase.R;
import com.aero51.moviedatabase.repository.model.tmdb.tvshow.TvShowGenresResponse;
import com.aero51.moviedatabase.utils.GenreObjectClickListener;

import java.util.List;

public class TvShowGenresAdapter extends RecyclerView.Adapter<TvShowGenresAdapter.ViewHolder>{

    private List<TvShowGenresResponse.TvShowGenre> tvShowGenreList;
    private GenreObjectClickListener mClickListener;

    // data is passed into the constructor
    public TvShowGenresAdapter( List<TvShowGenresResponse.TvShowGenre> tvShowGenreList,  GenreObjectClickListener clickListener) {
        this.tvShowGenreList = tvShowGenreList;
        this.mClickListener=clickListener;
    }

    // inflates the row layout from xml when needed
    @Override
    public TvShowGenresAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.genre_item, parent, false);
        return new TvShowGenresAdapter.ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(TvShowGenresAdapter.ViewHolder holder, int position) {
        String genre= tvShowGenreList.get(position).getName();
        holder.genreTextView.setText(genre);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return tvShowGenreList.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView genreTextView;

        ViewHolder(View itemView) {
            super(itemView);
            genreTextView =itemView.findViewById(R.id.tv_genre);
            genreTextView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Integer adapter_position = getBindingAdapterPosition();
            mClickListener.onGenreItemClick(tvShowGenreList.get(adapter_position).getId(), adapter_position);
        }
    }
}
