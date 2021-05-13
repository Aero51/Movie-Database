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
    private List<TvShowGenresResponse.TvShowGenre> genres;
    private Integer id;
    private String overview;
    private Double popularity;
    private List<TvShowDetailsResponse.ProductionCompany> production_companies;
    private String tagline;
    private Double vote_average;
    private Integer vote_count;
    private String backdrop_path;
    private String poster_path;
    private String first_air_date;
    private String next_episode_to_air;
    private String original_name;
    private Integer number_of_episodes;
    private Integer number_of_seasons;
    private String status;
    private Boolean in_production;
    private List<CreatedBy> created_by;
    private List<Season> seasons;


    public TvShowDetailsResponse() {
    }

    public int getDb_id() {
        return db_id;
    }

    public void setDb_id(int db_id) {
        this.db_id = db_id;
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

    public List<ProductionCompany> getProduction_companies() {
        return production_companies;
    }

    public void setProduction_companies(List<ProductionCompany> production_companies) {
        this.production_companies = production_companies;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
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

    public String getFirst_air_date() {
        return first_air_date;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
    }

    public String getNext_episode_to_air() {
        return next_episode_to_air;
    }

    public void setNext_episode_to_air(String next_episode_to_air) {
        this.next_episode_to_air = next_episode_to_air;
    }

    public String getOriginal_name() {
        return original_name;
    }

    public void setOriginal_name(String original_name) {
        this.original_name = original_name;
    }

    public Integer getNumber_of_episodes() {
        return number_of_episodes;
    }

    public void setNumber_of_episodes(Integer number_of_episodes) {
        this.number_of_episodes = number_of_episodes;
    }

    public Integer getNumber_of_seasons() {
        return number_of_seasons;
    }

    public void setNumber_of_seasons(Integer number_of_seasons) {
        this.number_of_seasons = number_of_seasons;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getIn_production() {
        return in_production;
    }

    public void setIn_production(Boolean in_production) {
        this.in_production = in_production;
    }

    public List<CreatedBy> getCreated_by() {
        return created_by;
    }

    public void setCreated_by(List<CreatedBy> created_by) {
        this.created_by = created_by;
    }

    public List<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<Season> seasons) {
        this.seasons = seasons;
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

    public static class CreatedBy {

        private Integer id;
        private String credit_id;
        private String name;
        private Integer gender;
        private String profile_path;

        public CreatedBy() {
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getCredit_id() {
            return credit_id;
        }

        public void setCredit_id(String credit_id) {
            this.credit_id = credit_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getGender() {
            return gender;
        }

        public void setGender(Integer gender) {
            this.gender = gender;
        }

        public String getProfile_path() {
            return profile_path;
        }

        public void setProfile_path(String profile_path) {
            this.profile_path = profile_path;
        }
    }
    public static class Season{

        private String air_date;
        private Integer episode_count;
        private Integer id;
        private String name;
        private String overview;
        private String poster_path;
        private Integer season_number;


        public Season() {
        }

        public String getAir_date() {
            return air_date;
        }

        public void setAir_date(String air_date) {
            this.air_date = air_date;
        }

        public Integer getEpisode_count() {
            return episode_count;
        }

        public void setEpisode_count(Integer episode_count) {
            this.episode_count = episode_count;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOverview() {
            return overview;
        }

        public void setOverview(String overview) {
            this.overview = overview;
        }

        public String getPoster_path() {
            return poster_path;
        }

        public void setPoster_path(String poster_path) {
            this.poster_path = poster_path;
        }

        public Integer getSeason_number() {
            return season_number;
        }

        public void setSeason_number(Integer season_number) {
            this.season_number = season_number;
        }
    }
}
