package com.aero51.moviedatabase.repository.model.epg;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "epg_program")
public class EpgProgram implements Serializable {


    @PrimaryKey(autoGenerate = true)
    private Integer db_id;
    private String title;
    private String subTitle;
    private String desc;
    private String credits;
    private Integer date;
    private String category;
    private String icon;
    private String channel;
    private String channel_display_name;
    private String start;
    private String stop;
    private Integer programDuration;


    public Integer getProgramDuration() {
        return programDuration;
    }

    public void setProgramDuration(Integer programDuration) {
        this.programDuration = programDuration;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getStop() {
        return stop;
    }

    public void setStop(String stop) {
        this.stop = stop;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getDb_id() {
        return db_id;
    }

    public void setDb_id(Integer db_id) {
        this.db_id = db_id;
    }

    public String getChannel_display_name() {
        return channel_display_name;
    }

    public void setChannel_display_name(String channel_display_name) {
        this.channel_display_name = channel_display_name;
    }
}
