package com.aero51.moviedatabase.repository.model.tmdb.credits;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

public class ActorSearchResponse {

    private Integer page;
    private List<ActorSearch> results;
    private Integer total_pages;
    private Integer total_results;

    public ActorSearchResponse(Integer page, List<ActorSearch> results, Integer total_pages, Integer total_results) {
        this.page = page;
        this.results = results;
        this.total_pages = total_pages;
        this.total_results = total_results;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<ActorSearch> getResults() {
        return results;
    }

    public void setResults(List<ActorSearch> results) {
        this.results = results;
    }

    public Integer getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(Integer total_pages) {
        this.total_pages = total_pages;
    }

    public Integer getTotal_results() {
        return total_results;
    }

    public void setTotal_results(Integer total_results) {
        this.total_results = total_results;
    }

    @Entity(tableName = "actor_search")
    public static class ActorSearch {


        @PrimaryKey(autoGenerate = true)
        private Integer db_id;
        private Boolean adult;
        private Integer gender;
        private Integer id;
        @Ignore
        private List<Movie> known_for;
        private String known_for_department;
        private String name;
        private Double popularity;
        private String profile_path;


        public ActorSearch(Boolean adult, Integer gender, Integer id, String known_for_department, String name, Double popularity, String profile_path) {

            this.adult = adult;
            this.gender = gender;
            this.id = id;
            this.known_for = known_for;
            this.known_for_department = known_for_department;
            this.name = name;
            this.popularity = popularity;
            this.profile_path = profile_path;
        }

        public Integer getDb_id() {
            return db_id;
        }

        public void setDb_id(Integer db_id) {
            this.db_id = db_id;
        }
        public Boolean getAdult() {
            return adult;
        }

        public void setAdult(Boolean adult) {
            this.adult = adult;
        }

        public Integer getGender() {
            return gender;
        }

        public void setGender(Integer gender) {
            this.gender = gender;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getKnown_for_department() {
            return known_for_department;
        }

        public void setKnown_for_department(String known_for_department) {
            this.known_for_department = known_for_department;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Double getPopularity() {
            return popularity;
        }

        public void setPopularity(Double popularity) {
            this.popularity = popularity;
        }

        public String getProfile_path() {
            return profile_path;
        }

        public void setProfile_path(String profile_path) {
            this.profile_path = profile_path;
        }

        private class Movie {
            /*
             "adult": false,
                     "backdrop_path": "/hkBaDkMWbLaf8B1lsWsKX7Ew3Xq.jpg",
                     "genre_ids": [
                     18,
                     28,
                     80,
                     53
                     ],
                     "id": 155,
                     "media_type": "movie",
                     "original_language": "en",
                     "original_title": "The Dark Knight",
                     "overview": "Batman raises the stakes in his war on crime. With the help of Lt. Jim Gordon and District Attorney Harvey Dent, Batman sets out to dismantle the remaining criminal organizations that plague the streets. The partnership proves to be effective, but they soon find themselves prey to a reign of chaos unleashed by a rising criminal mastermind known to the terrified citizens of Gotham as the Joker.",
                     "poster_path": "/qJ2tW6WMUDux911r6m7haRef0WH.jpg",
                     "release_date": "2008-07-16",
                     "title": "The Dark Knight",
                     "video": false,
                     "vote_average": 8.5,
                     "vote_count": 23897
           */
        }


    }
}
