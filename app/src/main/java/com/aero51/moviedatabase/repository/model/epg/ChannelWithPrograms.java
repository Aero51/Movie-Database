package com.aero51.moviedatabase.repository.model.epg;

import java.util.List;

public class ChannelWithPrograms {
    private Integer nearestTimePosition;
    private Integer nowPlayingPercentage;
    private List<EpgProgram> programsList;



    private  EpgChannel channel;

    public Integer getNearestTimePosition() {
        return nearestTimePosition;
    }

    public void setNearestTimePosition(Integer nearestTimePosition) {
        this.nearestTimePosition = nearestTimePosition;
    }

    public List<EpgProgram> getProgramsList() {
        return programsList;
    }

    public void setProgramsList(List<EpgProgram> programsList) {
        this.programsList = programsList;
    }
    public Integer getNowPlayingPercentage() {
        return nowPlayingPercentage;
    }

    public void setNowPlayingPercentage(Integer nowPlayingPercentage) {
        this.nowPlayingPercentage = nowPlayingPercentage;
    }

    public EpgChannel getChannel() {
        return channel;
    }

    public void setChannel(EpgChannel channel) {
        this.channel = channel;
    }

}
