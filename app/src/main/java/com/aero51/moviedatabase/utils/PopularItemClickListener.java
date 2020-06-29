package com.aero51.moviedatabase.utils;

import com.aero51.moviedatabase.repository.model.tmdb.movie.PopularMovie;

public interface PopularItemClickListener {
    void OnItemClick(PopularMovie result, int position);
}
