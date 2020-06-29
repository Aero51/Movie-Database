package com.aero51.moviedatabase.repository.model.tmdb.credits;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "crew")
public class Crew {


    @PrimaryKey(autoGenerate = true)
    private Integer db_id;
    private String credit_id;
    private String department;
    private Integer gender;
    private Integer id;
    private String job;
    private String name;
    private String profile_path;
    private Integer movie_id;


    public String getDepartment() {
        return department;
    }

    public Integer getGender() {
        return gender;
    }

    public Integer getId() { return id; }

    public String getJob() {
        return job;
    }

    public String getName() {
        return name;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    public Integer getDb_id() {
        return db_id;
    }

    public void setDb_id(Integer db_id) {
        this.db_id = db_id;
    }

    public void setCredit_id(String credit_id) {
        this.credit_id = credit_id;
    }

    public String getCredit_id() {
        return credit_id;
    }

    public Integer getMovie_id() { return movie_id; }

    public void setMovie_id(Integer movie_id) { this.movie_id = movie_id; }
}
