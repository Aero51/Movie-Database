package com.aero51.moviedatabase.repository.model.tmdb.movie;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;


@Entity(tableName = "now_playing_movies_page")
public class NowPlayingMoviesPage {

    @PrimaryKey(autoGenerate = false)
    private Integer page;
    private Integer total_results;
    private Integer total_pages;
    @Ignore
    private List<NowPlayingMovie> results;

    @Ignore
    public NowPlayingMoviesPage(Integer page, Integer total_results, Integer total_pages, List<NowPlayingMovie> results) {
        this.page = page;
        this.total_results = total_results;
        this.total_pages = total_pages;
        this.results = results;
    }

    public NowPlayingMoviesPage(Integer page, Integer total_results, Integer total_pages) {
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

    public List<NowPlayingMovie> getResults_list() {
        return results;
    }

}
