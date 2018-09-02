package com.pet.transport.core.ticket.destinationPlace.po;

/**
 * 维护目的地点信息
 */
public class DestinationPlace {

    private String destinationPlaceCode;

    private String destinationPlaceName;
    //目的地英文简称
    private String destinationPlaceSimple;

    private String startPlaceCode;

    private String startPlaceName;

    private String price;

    public String getDestinationPlaceCode() {
        return destinationPlaceCode;
    }

    public void setDestinationPlaceCode(String destinationPlaceCode) {
        this.destinationPlaceCode = destinationPlaceCode;
    }

    public String getDestinationPlaceName() {
        return destinationPlaceName;
    }

    public void setDestinationPlaceName(String destinationPlaceName) {
        this.destinationPlaceName = destinationPlaceName;
    }

    public String getDestinationPlaceSimple() {
        return destinationPlaceSimple;
    }

    public void setDestinationPlaceSimple(String destinationPlaceSimple) {
        this.destinationPlaceSimple = destinationPlaceSimple;
    }

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
