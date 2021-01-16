package com.aero51.moviedatabase.repository.model.tmdb.movie;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "upcoming_movies_page")
public class UpcomingMoviesPage {

    @PrimaryKey(autoGenerate = false)
    private Integer page;
    private Integer total_results;
    private Integer total_pages;
    @Ignore
    private List<UpcomingMovie> results;

    @Ignore
    public UpcomingMoviesPage(Integer page, Integer total_results, Integer total_pages, List<UpcomingMovie> results) {
        this.page = page;
        this.total_results = total_results;
        this.total_pages = total_pages;
        this.results = results;
    }

    public UpcomingMoviesPage(Integer page, Integer total_results, Integer total_pages) {
        this.page = page;
        this.total_results = total_results;
        this.total_pages = total_pages;

    }


    public Integer getPage() {
        return page;
    }

    public Integer getTotal_results() {
        return total_results;
    }

    public Integer getTotal_pages() {
        return total_pages;
    }

    public List<UpcomingMovie> getResults_list() {
        return results;
    }
}
