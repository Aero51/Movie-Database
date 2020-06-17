package com.aero51.moviedatabase.utils;


import com.aero51.moviedatabase.repository.model.Top_Rated_Result;

public interface ItemClickListener {
    void OnItemClick(Top_Rated_Result result, int position);
}