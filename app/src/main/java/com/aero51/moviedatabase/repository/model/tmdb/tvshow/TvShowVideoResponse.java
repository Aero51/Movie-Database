package com.aero51.moviedatabase.repository.model.tmdb.tvshow;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

public class TvShowVideoResponse {

    private Integer id;
    private List<TvShowVideo> results;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<TvShowVideo> getResults() {
        return results;
    }

    public void setResults(List<TvShowVideo> results) {
        this.results = results;
    }


    @Entity(tableName = "tv_show_video")
    public static class TvShowVideo {
        @PrimaryKey(autoGenerate = true)
        private int db_id;
        private int tv_show_id;
        private String id;
        private String key;
        private String name;
        private String site;
        private String type;
        private Integer size;

        public TvShowVideo() {
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

        public int getDb_id() {
            return db_id;
        }

        public void setDb_id(int db_id) {
            this.db_id = db_id;
        }

        public int getTv_show_id() {
            return tv_show_id;
        }

        public void setTv_show_id(int tv_show_id) {
            this.tv_show_id = tv_show_id;
        }
    }
}
