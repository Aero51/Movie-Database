package com.aero51.moviedatabase.repository.model.tmdb.movie;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;


@Entity(tableName = "now_playing_movies_page")
public class NowPlayingMoviesPage {

    @PrimaryKey(autoGenerate = false)
    private Integer page;
    private Integer total_results;
    private Integer total_pages;
    @Ignore
    private List<NowPlayingMovie> results;

    public NowPlayingMoviesPage() { }


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

    public void setPage(Integer page) {
        this.page = page;
    }

    public void setTotal_results(Integer total_results) {
        this.total_results = total_results;
    }

    public void setTotal_pages(Integer total_pages) {
        this.total_pages = total_pages;
    }

    public void setResults(List<NowPlayingMovie> results) {
        this.results = results;
    }

    @Entity(tableName = "now_playing_movie")
    public static class NowPlayingMovie implements Serializable {

        @PrimaryKey(autoGenerate = true)
        private int db_id;
        private Double popularity;
        private Integer vote_count;
        private boolean video;
        private String poster_path;
        private Integer id;
        private boolean adult;
        private String backdrop_path;
        private String original_language;
        private String original_title;
        private List<Integer> genre_ids;
        private String title;
        private Double vote_average;
        private String overview;
        private String release_date;
        private Long timestamp;





        public NowPlayingMovie() {
        }


        public int getDb_id() {
            return db_id;
        }

        public void setDb_id(int db_id) {
            this.db_id = db_id;
        }

        public Double getPopularity() {
            return popularity;
        }

        public void setPopularity(Double popularity) {
            this.popularity = popularity;
        }

        public Integer getVote_count() {
            return vote_count;
        }

        public void setVote_count(Integer vote_count) {
            this.vote_count = vote_count;
        }

        public boolean isVideo() {
            return video;
        }

        public void setVideo(boolean video) {
            this.video = video;
        }

        public String getPoster_path() {
            return poster_path;
        }

        public void setPoster_path(String poster_path) {
            this.poster_path = poster_path;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public boolean isAdult() {
            return adult;
        }

        public void setAdult(boolean adult) {
            this.adult = adult;
        }

        public String getBackdrop_path() {
            return backdrop_path;
        }

        public void setBackdrop_path(String backdrop_path) {
            this.backdrop_path = backdrop_path;
        }

        public String getOriginal_language() {
            return original_language;
        }

        public void setOriginal_language(String original_language) {
            this.original_language = original_language;
        }

        public String getOriginal_title() {
            return original_title;
        }

        public void setOriginal_title(String original_title) {
            this.original_title = original_title;
        }

        public List<Integer> getGenre_ids() {
            return genre_ids;
        }

        public void setGenre_ids(List<Integer> genre_ids) {
            this.genre_ids = genre_ids;
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

        public String getOverview() {
            return overview;
        }

        public void setOverview(String overview) {
            this.overview = overview;
        }

        public String getRelease_date() {
            return release_date;
        }

        public void setRelease_date(String release_date) {
            this.release_date = release_date;
        }

        public Long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Long timestamp) {
            this.timestamp = timestamp;
        }
    }
}
