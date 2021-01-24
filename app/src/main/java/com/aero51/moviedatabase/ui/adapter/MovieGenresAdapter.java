package com.aero51.moviedatabase.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.aero51.moviedatabase.R;
import com.aero51.moviedatabase.repository.model.tmdb.movie.MovieGenresResponse;

import java.util.List;


public class MovieGenresAdapter extends RecyclerView.Adapter<MovieGenresAdapter.ViewHolder> {

    private List<MovieGenresResponse.MovieGenre> movieGenreList;

    // data is passed into the constructor
    public MovieGenresAdapter( List<MovieGenresResponse.MovieGenre> movieGenreList) {
        this.movieGenreList = movieGenreList;
    }

    // inflates the row layout from xml when needed
    @Override
    public MovieGenresAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.genre_item, parent, false);
        return new MovieGenresAdapter.ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(MovieGenresAdapter.ViewHolder holder, int position) {
        String genre= movieGenreList.get(position).getName();
        holder.genreTextView.setText(genre);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return movieGenreList.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView genreTextView;


        ViewHolder(View itemView) {
            super(itemView);
            genreTextView =itemView.findViewById(R.id.tv_genre);
        }


    }

    // convenience method for getting data at click position
   // private ActorImagesResponse.ActorImage getItem(int id) {
    //    return imagesList.get(id);
    //}


}
