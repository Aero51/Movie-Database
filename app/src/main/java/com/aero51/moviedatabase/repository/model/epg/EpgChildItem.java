package com.aero51.moviedatabase.repository.model.epg;

import java.util.List;

public class EpgChildItem {
    private Integer nearestTimePosition;
    private List<EpgProgram> programsList;

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
}
