package com.aero51.moviedatabase.repository.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;


@Entity(tableName = "top_rated_movie_page")
public class TopRatedMoviesPage {

    @PrimaryKey(autoGenerate = true)
    private int db_id;
    private Integer page;
    private Integer total_results;
    private Integer total_pages;
    @Ignore
    private List<TopRatedMovie> results;

    @Ignore
    public TopRatedMoviesPage(Integer page, Integer total_results, Integer total_pages, List<TopRatedMovie> results) {
        this.page = page;
        this.total_results = total_results;
        this.total_pages = total_pages;
        this.results = results;
    }

    public TopRatedMoviesPage(Integer page, Integer total_results, Integer total_pages) {
        this.page = page;
        this.total_results = total_results;
        this.total_pages = total_pages;

    }

    public int getDb_id() {
        return db_id;
    }

    public void setDb_id(int db_id) {
        this.db_id = db_id;
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

    public List<TopRatedMovie> getResults_list() {
        return results;
    }

}
