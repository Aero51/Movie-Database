package com.aero51.moviedatabase.repository.model.tmdb.tvshow;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


import java.util.List;

public class TvShowGenresResponse {

    private List<TvShowGenre> genres;

    public TvShowGenresResponse(List<TvShowGenre> genres) {
        this.genres = genres;
    }

    public List<TvShowGenre> getGenres() {
        return genres;
    }

    public void setGenres(List<TvShowGenre> genres) {
        this.genres = genres;
    }

    @Entity(tableName = "tv_show_genre")
    public static class TvShowGenre {


        @PrimaryKey(autoGenerate = true)
        private int db_id;
        private Integer id;
        private String name;

        public TvShowGenre(Integer id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getDb_id() {
            return db_id;
        }

        public void setDb_id(int db_id) {
            this.db_id = db_id;
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
    }
}

