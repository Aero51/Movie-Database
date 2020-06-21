package com.aero51.moviedatabase.repository.model.credits;

import java.util.List;

public class ActorImagesRequest {

    private List<ActorImage> profiles;


    public List<ActorImage> getImages() {
        return profiles;
    }

    public void setImages(List<ActorImage> profiles) {
        this.profiles = profiles;
    }
}
