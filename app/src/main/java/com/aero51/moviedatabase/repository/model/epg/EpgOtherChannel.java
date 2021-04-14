package com.aero51.moviedatabase.repository.model.epg;

public class EpgOtherChannel {

    private Integer drawableInteger;
    private String channelName;
    private String channelDisplayName;

    public Integer getDrawableInteger() {
        return drawableInteger;
    }

    public void setDrawableInteger(Integer drawableInteger) {
        this.drawableInteger = drawableInteger;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelDisplayName() {
        return channelDisplayName;
    }

    public void setChannelDisplayName(String channelDisplayName) {
        this.channelDisplayName = channelDisplayName;
    }
}
