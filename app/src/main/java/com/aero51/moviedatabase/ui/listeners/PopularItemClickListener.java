package com.aero51.moviedatabase.ui.listeners;

import com.aero51.moviedatabase.repository.model.tmdb.movie.PopularMoviesPage;

public interface PopularItemClickListener {
    void OnItemClick(PopularMoviesPage.PopularMovie result, int position);
}
