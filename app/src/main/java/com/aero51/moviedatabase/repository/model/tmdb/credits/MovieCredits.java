package com.aero51.moviedatabase.repository.model.tmdb.credits;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;

@Entity(tableName = "movie_credit")
public class MovieCredits {
    @PrimaryKey(autoGenerate = false)
    private Integer id;

    @Ignore
    private List<Cast> cast;

    @Ignore
    private List<Crew> crew;

    @Ignore
    public MovieCredits(Integer id, List<Cast> cast, List<Crew> crew) {
        this.id = id;
        this.cast = cast;
        this.crew = crew;
    }

    public MovieCredits(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public List<Cast> getCast() {
        return cast;
    }

    public List<Crew> getCrew() {
        return crew;
    }

    public void setCast(List<Cast> cast) { this.cast = cast; }

    public void setCrew(List<Crew> crew) { this.crew = crew; }

    @Entity(tableName = "crew")
    public static class Crew {


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

    @Entity(tableName = "cast")
    public static class Cast implements Serializable {


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

        public Integer getDb_id() {
            return db_id;
        }

        public void setDb_id(Integer db_id) {
            this.db_id = db_id;
        }
    }
}
