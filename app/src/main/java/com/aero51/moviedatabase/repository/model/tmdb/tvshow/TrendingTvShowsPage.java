package com.aero51.moviedatabase.repository.model.tmdb.tvshow;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "trending_tv_shows_page")
public class TrendingTvShowsPage {

    @PrimaryKey(autoGenerate = false)
    private Integer page;
    private Integer total_results;
    private Integer total_pages;
    @Ignore
    private List<TrendingTvShow> results;

    @Ignore
    public TrendingTvShowsPage(Integer page, Integer total_results, Integer total_pages, List<TrendingTvShow> results) {
        this.page = page;
        this.total_results = total_results;
        this.total_pages = total_pages;
        this.results = results;
    }

    public TrendingTvShowsPage(Integer page, Integer total_results, Integer total_pages) {
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

    public List<TrendingTvShow> getResults() {
        return results;
    }

    @Entity(tableName = "trending_tv_show")
    public static class TrendingTvShow {

        @PrimaryKey(autoGenerate = true)
        private int db_id;
        private String backdrop_path;
        private String first_air_date;
        private List<Integer> genre_ids;
        private Integer id;
        private String name;
        private List<String> origin_country;
        private String original_language;
        private String original_name;
        private String overview;
        private Double popularity;
        private String poster_path;
        private Double vote_average;
        private Integer vote_count;


        public TrendingTvShow(String backdrop_path, String first_air_date, List<Integer> genre_ids, Integer id, String name, List<String> origin_country, String original_language, String original_name, String overview, Double popularity, String poster_path, Double vote_average, Integer vote_count) {
            this.backdrop_path = backdrop_path;
            this.first_air_date = first_air_date;
            this.genre_ids = genre_ids;
            this.id = id;
            this.name = name;
            this.origin_country = origin_country;
            this.original_language = original_language;
            this.original_name = original_name;
            this.overview = overview;
            this.popularity = popularity;
            this.poster_path = poster_path;
            this.vote_average = vote_average;
            this.vote_count = vote_count;
        }


        public String getBackdrop_path() {
            return backdrop_path;
        }

        public String getFirst_air_date() {
            return first_air_date;
        }

        public List<Integer> getGenre_ids() {
            return genre_ids;
        }

        public Integer getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public List<String> getOrigin_country() {
            return origin_country;
        }

        public String getOriginal_language() {
            return original_language;
        }

        public String getOriginal_name() {
            return original_name;
        }

        public String getOverview() {
            return overview;
        }

        public Double getPopularity() {
            return popularity;
        }

        public String getPoster_path() {
            return poster_path;
        }

        public Double getVote_average() {
            return vote_average;
        }

        public Integer getVote_count() {
            return vote_count;
        }

        public int getDb_id() {
            return db_id;
        }

        public void setDb_id(int db_id) {
            this.db_id = db_id;
        }
    }


}
