package com.aero51.moviedatabase.repository.networkonlynotused.test;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "top_rated_page")
public class TopRatedPage {
    @PrimaryKey(autoGenerate = false)
    private Integer page;
    public TopRatedPage(Integer page) {
        this.page = page;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }


}
