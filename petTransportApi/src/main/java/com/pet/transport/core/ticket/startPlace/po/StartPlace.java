package com.pet.transport.core.ticket.startPlace.po;

/**
 * 维护出发地点信息
 */
public class StartPlace {
    //起始地Code
    private String startPlaceCode;
    //起始地名称
    private String startPlaceName;
    //起始地英文简称
    private String startPlaceSimple;


    public String getStartPlaceCode() {
        return startPlaceCode;
    }

    public void setStartPlaceCode(String startPlaceCode) {
        this.startPlaceCode = startPlaceCode;
    }

    public String getStartPlaceName() {
        return startPlaceName;
    }

    public void setStartPlaceName(String startPlaceName) {
        this.startPlaceName = startPlaceName;
    }

    public String getStartPlaceSimple() {
        return startPlaceSimple;
    }

    public void setStartPlaceSimple(String startPlaceSimple) {
        this.startPlaceSimple = startPlaceSimple;
    }
}
