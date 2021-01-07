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

    public Integer getTotal_results() {
        return total_results;
    }

    public void setTotal_results(Integer total_results) {
        this.total_results = total_results;
    }

    public Integer getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(Integer total_pages) {
        this.total_pages = total_pages;
    }

    public List<TopRatedMovie> getResults() {
        return results;
    }

    public void setResults(List<TopRatedMovie> results) {
        this.results = results;
    }
}
