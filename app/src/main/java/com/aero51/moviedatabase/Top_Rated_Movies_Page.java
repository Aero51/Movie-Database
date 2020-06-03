package com.aero51.moviedatabase;

import androidx.lifecycle.LiveData;

import java.util.List;

public class Top_Rated_Movies_Page {

private Integer page;
private Integer total_results;
private Integer total_pages;
private List<Top_Rated_Result> results;

    public Top_Rated_Movies_Page(Integer page, Integer total_results, Integer total_pages, List<Top_Rated_Result> results) {
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

    public List<Top_Rated_Result> getResults_list() {
        return results;
    }
}
