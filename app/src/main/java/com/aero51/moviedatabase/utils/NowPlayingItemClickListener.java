package com.aero51.moviedatabase.utils;


import com.aero51.moviedatabase.repository.model.tmdb.movie.NowPlayingMovie;

public interface NowPlayingItemClickListener {
    void OnItemClick(NowPlayingMovie result, int position) ;
}