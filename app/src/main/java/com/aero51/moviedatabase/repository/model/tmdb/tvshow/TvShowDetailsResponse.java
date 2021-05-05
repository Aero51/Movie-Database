package com.aero51.moviedatabase.repository.model.tmdb.tvshow;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.aero51.moviedatabase.repository.model.tmdb.movie.MovieDetailsResponse;
import com.aero51.moviedatabase.repository.model.tmdb.movie.MovieGenresResponse;

import java.util.List;

@Entity(tableName = "tv_show_details")
public class TvShowDetailsResponse {

    //TODO change values according to json below
    @PrimaryKey(autoGenerate = true)
    private int db_id;
    private Integer budget;
    private List<TvShowGenresResponse.TvShowGenre> genres;
    private Integer id;
    private String imdb_id;
    private String original_title;
    private String overview;
    private Double popularity;
    private List<TvShowDetailsResponse.ProductionCompany> production_companies;
    private String release_date;
    private Integer revenue;
    private Integer runtime;
    private String tagline;
    private String title;
    private Double vote_average;
    private Integer vote_count;
    private String backdrop_path;
    private String poster_path;


    /*
    "backdrop_path": "/sP4jYXcK11YGpoyF698Cs219VZp.jpg",
            "created_by": [
    {
        "id": 1076694,
            "credit_id": "52534acc19c29579400f39f8",
            "name": "Jeffrey Jarrett",
            "gender": 2,
            "profile_path": "/sbepieYzkAqlwQF65Bh9mks2Lc2.jpg"
    },
    {
        "id": 1215100,
            "credit_id": "52534acc19c29579400f39b4",
            "name": "Vince Russo",
            "gender": 0,
            "profile_path": "/jc0ZL99Pj7TAVtFKwF1KSBQjoXq.jpg"
    }
    ],
            "episode_run_time": [],
            "first_air_date": null,
            "genres": [],
            "homepage": "",
            "id": 276,
            "in_production": true,
            "languages": [],
            "last_air_date": null,
            "last_episode_to_air": null,
            "name": "TNA Global Impact!",
            "next_episode_to_air": null,
            "networks": [],
            "number_of_episodes": 0,
            "number_of_seasons": 0,
            "origin_country": [],
            "original_language": "en",
            "original_name": "TNA Global Impact!",
            "overview": "Global iMPACT! was a free online professional wrestling show produced by Total Nonstop Action Wrestling that existed in 2006. Global iMPACT!, which was hosted by TNA interviewers Jeremy Borash and Christy Hemme, featured \"exclusive footage, interviews, news updates, matches and more\".",
            "popularity": 0.6,
            "poster_path": "/2J4DLJBFl4PwdNNrnt6UokVhqLi.jpg",
            "production_companies": [],
            "production_countries": [],
            "seasons": [],
            "spoken_languages": [],
            "status": "Ended",
            "tagline": "",
            "type": "Scripted",
            "vote_average": 0.0,
            "vote_count": 0
}

    */


    public TvShowDetailsResponse() {
    }


    public Integer getBudget() {
        return budget;
    }

    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    public List<TvShowGenresResponse.TvShowGenre> getGenres() {
        return genres;
    }

    public void setGenres(List<TvShowGenresResponse.TvShowGenre> genres) {
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

    public List<TvShowDetailsResponse.ProductionCompany> getProduction_companies() {
        return production_companies;
    }

    public void setProduction_companies(List<TvShowDetailsResponse.ProductionCompany> production_companies) {
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

    public int getDb_id() {
        return db_id;
    }

    public void setDb_id(int db_id) {
        this.db_id = db_id;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }


    public static class ProductionCompany {

        private Integer id;
        private String logo_path;
        private String name;
        private String origin_country;


        public ProductionCompany() {
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getLogo_path() {
            return logo_path;
        }

        public void setLogo_path(String logo_path) {
            this.logo_path = logo_path;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOrigin_country() {
            return origin_country;
        }

        public void setOrigin_country(String origin_country) {
            this.origin_country = origin_country;
        }
    }

}
