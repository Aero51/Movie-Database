package com.aero51.moviedatabase.repository.model.epg;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "epg_channel")
public class EpgChannel {


    @PrimaryKey(autoGenerate = true)
    private Integer channel_db_id;

    private String id;

    private String display_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public Integer getChannel_db_id() {
        return channel_db_id;
    }

    public void setChannel_db_id(Integer channel_db_id) {
        this.channel_db_id = channel_db_id;
    }
}
