package com.aero51.moviedatabase.repository.model.tmdb.movie;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "movie_favourite")
public class MovieFavourite {


    @PrimaryKey(autoGenerate = true)
    private int db_id;
    private Integer budget;
    private List<MovieGenresResponse.MovieGenre> genres;
    private Integer id;
    private String imdb_id;
    private String original_title;
    private String overview;
    private Double popularity;
    private List<MovieDetailsResponse.ProductionCompany> production_companies;
    private String release_date;
    private Integer revenue;
    private Integer runtime;
    private String tagline;
    private String title;
    private Double vote_average;
    private Integer vote_count;



    public MovieFavourite() {
    }



    public int getDb_id() {
        return db_id;
    }

    public void setDb_id(int db_id) {
        this.db_id = db_id;
    }

    public Integer getBudget() {
        return budget;
    }

    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    public List<MovieGenresResponse.MovieGenre> getGenres() {
        return genres;
    }

    public void setGenres(List<MovieGenresResponse.MovieGenre> genres) {
        this.genres = genres;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImdb_id() {
        return imdb_id;
    }

    public void setImdb_id(String imdb_id) {
        this.imdb_id = imdb_id;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public List<MovieDetailsResponse.ProductionCompany> getProduction_companies() {
        return production_companies;
    }

    public void setProduction_companies(List<MovieDetailsResponse.ProductionCompany> production_companies) {
        this.production_companies = production_companies;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public Integer getRevenue() {
        return revenue;
    }

    public void setRevenue(Integer revenue) {
        this.revenue = revenue;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getVote_average() {
        return vote_average;
    }

    public void setVote_average(Double vote_average) {
        this.vote_average = vote_average;
    }

    public Integer getVote_count() {
        return vote_count;
    }

    public void setVote_count(Integer vote_count) {
        this.vote_count = vote_count;
    }
}
