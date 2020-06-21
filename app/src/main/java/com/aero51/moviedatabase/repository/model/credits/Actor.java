package com.aero51.moviedatabase.repository.model.credits;

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
}
