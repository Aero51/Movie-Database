package com.aero51.moviedatabase.repository.model.credits;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "cast")
public class Cast implements Serializable {
    @PrimaryKey(autoGenerate = false)
    private Integer cast_id;
    private String character;
    private String credit_id;
    private Integer gender;
    private Integer id;
    private String name;
    private Integer order;
    private String profile_path;
    private Integer movie_id;


    public Integer getCast_id() {
        return cast_id;
    }

    public String getCharacter() {
        return character;
    }

    public String getCredit_id() {
        return credit_id;
    }

    public Integer getGender() {
        return gender;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getOrder() {
        return order;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setCast_id(Integer cast_id) {
        this.cast_id = cast_id;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public void setCredit_id(String credit_id) {
        this.credit_id = credit_id;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    public Integer getMovie_id() { return movie_id; }

    public void setMovie_id(Integer movie_id) { this.movie_id = movie_id; }
}
