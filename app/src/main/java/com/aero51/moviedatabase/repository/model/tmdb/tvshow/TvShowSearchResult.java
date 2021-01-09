package com.aero51.moviedatabase.repository.model.tmdb.tvshow;

import com.aero51.moviedatabase.repository.model.tmdb.movie.TopRatedMovie;

import java.util.List;

public class TvShowSearchResult {

    private Integer page;
    private Integer total_results;
    private Integer total_pages;

    private List<TvShow> results;

    public TvShowSearchResult(Integer page, Integer total_results, Integer total_pages, List<TvShow> results) {
        this.page = page;
        this.total_results = total_results;
        this.total_pages = total_pages;
        this.results = results;
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

    public List<TvShow> getResults() {
        return results;
    }
}
