package com.aero51.moviedatabase.ui.listeners;


import com.aero51.moviedatabase.repository.model.tmdb.movie.NowPlayingMoviesPage;

public interface NowPlayingItemClickListener {
    void OnItemClick(NowPlayingMoviesPage.NowPlayingMovie result, int position) ;
}