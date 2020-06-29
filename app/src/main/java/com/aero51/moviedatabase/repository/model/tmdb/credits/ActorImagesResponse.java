package com.aero51.moviedatabase.repository.model.tmdb.credits;

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
}
