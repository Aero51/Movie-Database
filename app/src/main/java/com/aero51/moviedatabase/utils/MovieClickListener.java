package com.aero51.moviedatabase.utils;

import com.aero51.moviedatabase.repository.model.tmdb.movie.Movie;
import com.aero51.moviedatabase.repository.model.tmdb.movie.TopRatedMovie;

public interface MovieClickListener {

     void OnObjectItemClick(Object  movie, int position);
}
