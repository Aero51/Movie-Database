package com.aero51.moviedatabase.repository.model.tmdb.credits;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

@Entity(tableName = "tv_show_credits")
public class TvShowCredits {


    @PrimaryKey(autoGenerate = false)
    private Integer id;

    @Ignore
    @SerializedName("cast")
    private List<TvShowCast> tvShowCast;

    @Ignore
    @SerializedName("crew")
    private List<TvShowCrew> tvShowCrew;


    public TvShowCredits() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<TvShowCast> getTvShowCast() {
        return tvShowCast;
    }

    public List<TvShowCrew> getTvShowCrew() {
        return tvShowCrew;
    }

    public void setTvShowCast(List<TvShowCast> tvShowCast) {
        this.tvShowCast = tvShowCast;
    }

    public void setTvShowCrew(List<TvShowCrew> tvShowCrew) {
        this.tvShowCrew = tvShowCrew;
    }

    @Entity(tableName = "tv_show_crew")
    public static class TvShowCrew {


        @PrimaryKey(autoGenerate = true)
        private Integer db_id;
        private String credit_id;
        private String department;
        private Integer gender;
        private Integer id;
        private String job;
        private String name;
        private String profile_path;
        private Integer tv_show_id;


        public String getDepartment() {
            return department;
        }

        public Integer getGender() {
            return gender;
        }

        public Integer getId() {
            return id;
        }

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

        public Integer getTv_show_id() {
            return tv_show_id;
        }

        public void setTv_show_id(Integer tv_show_id) {
            this.tv_show_id = tv_show_id;
        }
    }

    @Entity(tableName = "tv_show_cast")
    public static class TvShowCast implements Serializable {


        @PrimaryKey(autoGenerate = true)
        private Integer db_id;
        private Integer cast_id;
        private String character;
        private String credit_id;
        private Integer gender;
        private Integer id;
        private String name;
        private Integer order;
        private String profile_path;
        private Integer tv_show_id;


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

        public Integer getTv_show_id() {
            return tv_show_id;
        }

        public void setTv_show_id(Integer tv_show_id) {
            this.tv_show_id = tv_show_id;
        }

        public Integer getDb_id() {
            return db_id;
        }

        public void setDb_id(Integer db_id) {
            this.db_id = db_id;
        }
    }
}