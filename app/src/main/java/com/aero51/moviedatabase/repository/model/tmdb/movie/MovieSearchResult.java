package com.aero51.moviedatabase.repository.model.tmdb.movie;

import java.util.List;

public class MovieSearchResult {

    private Integer page;
    private Integer total_results;
    private Integer total_pages;

    private List<TopRatedMovie> results;

    public MovieSearchResult(Integer page, Integer total_results, Integer total_pages, List<TopRatedMovie> results) {
        this.page = page;
        this.total_results = total_results;
        this.total_pages = total_pages;
        this.results = results;
    }


    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<TopRatedMovie> getResults() {
        return results;
    }

}
