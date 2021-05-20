package com.aero51.moviedatabase.repository.model.tmdb.credits;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

public class ActorImagesResponse {

    private List<ActorImage> profiles;
    private Integer id;


    public List<ActorImage> getImages() {
        return profiles;
    }

    public void setImages(List<ActorImage> profiles) {
        this.profiles = profiles;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }



    @Entity(tableName = "actor_image")
    public static class ActorImage {

        @PrimaryKey(autoGenerate = true)
        private Integer db_id;
        private Integer width;
        private Integer height;
        private String file_path;
        private Integer actor_id;

        public Integer getWidth() {
            return width;
        }

        public void setWidth(Integer width) {
            this.width = width;
        }

        public Integer getHeight() {
            return height;
        }

        public void setHeight(Integer height) {
            this.height = height;
        }

        public String getFile_path() {
            return file_path;
        }

        public void setFile_path(String file_path) {
            this.file_path = file_path;
        }

        public Integer getDb_id() {
            return db_id;
        }

        public void setDb_id(Integer db_id) {
            this.db_id = db_id;
        }

        public Integer getActor_id() {
            return actor_id;
        }

        public void setActor_id(Integer actor_id) {
            this.actor_id = actor_id;
        }

    }
}
