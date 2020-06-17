package com.aero51.moviedatabase.utils;


import com.aero51.moviedatabase.repository.model.TopRatedMovie;

public interface ItemClickListener {
    void OnItemClick(TopRatedMovie result, int position);
}