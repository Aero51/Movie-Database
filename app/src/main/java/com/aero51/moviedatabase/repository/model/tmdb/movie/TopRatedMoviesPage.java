package com.aero51.moviedatabase.repository.model.tmdb.movie;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;


@Entity(tableName = "top_rated_movies_page")
public class TopRatedMoviesPage {

    @PrimaryKey(autoGenerate = false)
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

    @Entity(tableName = "top_rated_movie")
    public static class TopRatedMovie  implements Serializable {

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



        public TopRatedMovie( Double popularity, Integer vote_count, boolean video, String poster_path, Integer id, boolean adult, String backdrop_path, String original_language, String original_title, List<Integer> genre_ids, String title, Double vote_average, String overview, String release_date) {
            this.popularity = popularity;
            this.vote_count = vote_count;
            this.video = video;
            this.poster_path = poster_path;
            this.id = id;
            this.adult = adult;
            this.backdrop_path = backdrop_path;
            this.original_language = original_language;
            this.original_title = original_title;
            this.genre_ids = genre_ids;
            this.title = title;
            this.vote_average = vote_average;
            this.overview = overview;
            this.release_date = release_date;

        }

        //for testing purposes
        @Ignore
        public TopRatedMovie(Integer id, String title) {
            this.id = id;
            this.title = title;
        }


        public int getDb_id() {
            return db_id;
        }


        public Double getPopularity() {
            return popularity;
        }

        public Integer getVote_count() {
            return vote_count;
        }

        public boolean isVideo() {
            return video;
        }

        public String getPoster_path() {
            return poster_path;
        }

        public Integer getId() {
            return id;
        }

        public boolean isAdult() {
            return adult;
        }

        public String getBackdrop_path() {
            return backdrop_path;
        }

        public String getOriginal_language() {
            return original_language;
        }

        public String getOriginal_title() {
            return original_title;
        }

        public List<Integer> getGenre_ids() {
            return genre_ids;
        }

        public String getTitle() {
            return title;
        }

        public Double getVote_average() {
            return vote_average;
        }

        public String getOverview() {
            return overview;
        }

        public String getRelease_date() {
            return release_date;
        }

        public void setDb_id(int db_id) {
            this.db_id = db_id;
        }

        public Long getTimestamp() { return timestamp; }

        public void setTimestamp(Long timestamp) { this.timestamp = timestamp; }

    }
}
