package com.aero51.moviedatabase.utils;

import com.aero51.moviedatabase.repository.model.PopularMovie;
import com.aero51.moviedatabase.repository.model.TopRatedMovie;

public interface PopularItemClickListener {
    void OnItemClick(PopularMovie result, int position);
}
