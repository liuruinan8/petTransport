package com.pet.transport.core.ticket.airBox.po;

public class AirBox {
    //航空箱
    private String boxTypeId;
    //航空箱名称
    private String boxTypeName;
    //宠物体重下限
    private double weightStart;
    //宠物体重上限
    private double weightEnd;
    //是否为强制大箱
    private String isBigBox;
    //是否再用
    private String isUse;
    //价格
    private String price;
    //备注
    private String note;

    public String getBoxTypeId() {
        return boxTypeId;
    }

    public void setBoxTypeId(String boxTypeId) {
        this.boxTypeId = boxTypeId;
    }

    public String getBoxTypeName() {
        return boxTypeName;
    }

    public void setBoxTypeName(String boxTypeName) {
        this.boxTypeName = boxTypeName;
    }

    public double getWeightStart() {
        return weightStart;
    }

    public void setWeightStart(double weightStart) {
        this.weightStart = weightStart;
    }

    public double getWeightEnd() {
        return weightEnd;
    }

    public void setWeightEnd(double weightEnd) {
        this.weightEnd = weightEnd;
    }

    public String getIsBigBox() {
        return isBigBox;
    }

    public void setIsBigBox(String isBigBox) {
        this.isBigBox = isBigBox;
    }

    public String getIsUse() {
        return isUse;
    }

    public void setIsUse(String isUse) {
        this.isUse = isUse;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
