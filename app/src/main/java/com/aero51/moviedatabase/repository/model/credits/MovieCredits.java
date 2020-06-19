package com.aero51.moviedatabase.repository.model.credits;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

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
}
