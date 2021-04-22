package com.aero51.moviedatabase.repository.model.tmdb.movie;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

public class MovieVideosResponse {

    private Integer id;
    private List<MovieVideo> results;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<MovieVideo> getResults() {
        return results;
    }

    public void setResults(List<MovieVideo> results) {
        this.results = results;
    }


    @Entity(tableName = "movie_video")
    public static class MovieVideo {


        @PrimaryKey(autoGenerate = true)
        private int db_id;



        private int movie_id;
        private String id;
        private String key;
        private String name;
        private String site;
        private String type;
        private Integer size;


        public MovieVideo() {
        }


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSite() {
            return site;
        }

        public void setSite(String site) {
            this.site = site;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Integer getSize() {
            return size;
        }

        public void setSize(Integer size) {
            this.size = size;
        }

        public int getDb_id() { return db_id; }

        public void setDb_id(int db_id) { this.db_id = db_id; }

        public int getMovie_id() { return movie_id; }

        public void setMovie_id(int movie_id) { this.movie_id = movie_id; }
    }

}
