package com.aero51.moviedatabase.utils;


import com.aero51.moviedatabase.repository.model.tmdb.movie.TopRatedMovie;

public interface TopRatedItemClickListener {
    void OnItemClick(TopRatedMovie result, int position);
}