package com.aero51.moviedatabase.repository.model.tmdb.credits;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "actor")
public class Actor {

    private String birthday;
    @PrimaryKey(autoGenerate = false)
    private Integer id;
    private String name;
    private String biography;
    private String place_of_birth;
    private String profile_path;
    private String imdb_id;
    private String homepage;




    public String getBirthday() {
        return birthday;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBiography() {
        return biography;
    }

    public String getPlace_of_birth() {
        return place_of_birth;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public String getImdb_id() {
        return imdb_id;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public void setPlace_of_birth(String place_of_birth) {
        this.place_of_birth = place_of_birth;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    public void setImdb_id(String imdb_id) {
        this.imdb_id = imdb_id;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }
}
