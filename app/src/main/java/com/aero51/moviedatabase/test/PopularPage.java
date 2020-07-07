package com.aero51.moviedatabase.test;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "popular_page")
public class PopularPage {

    @PrimaryKey(autoGenerate = false)
    private Integer page;


    public PopularPage(Integer page) {
        this.page = page;
    }


    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
}
