package com.pet.transport.core.order.po;

/**
 * @author zimok
 * @Title: OrderPet
 * @ProjectName petTransport
 * @Description: 订单宠物信息表
 * @date 2019/1/1119:18
 */
public class OrderPet {
    //主键
    private String id;
    //主键
    private String orderId;
    //订单编号
    private String orderNo;
    //宠物类型
    private String petKind;
    //宠物高度
    private String petHeight;
    //宠物名称
    private String petName;
    //宠物体重
    private String petWeight;
    //宠物年龄
    private String petAge;
    //宠物性别
    private String petSex;
    //宠物性格
    private String petCharacter;
    //价格
    private String singleTicketPrice;
    //航空箱
    private String petBoxTypeId;
    //航空箱名称
    private String petBoxTypeName;
    //航空箱价格
    private String singleBoxPrice;
    //协助办理相关证照
    private String quarantineCertAss;
    //声明价值
    private String singleDeclarePrice;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPetKind() {
        return petKind;
    }

    public void setPetKind(String petKind) {
        this.petKind = petKind;
    }

    public String getPetHeight() {
        return petHeight;
    }

    public void setPetHeight(String petHeight) {
        this.petHeight = petHeight;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetWeight() {
        return petWeight;
    }

    public void setPetWeight(String petWeight) {
        this.petWeight = petWeight;
    }

    public String getPetAge() {
        return petAge;
    }

    public void setPetAge(String petAge) {
        this.petAge = petAge;
    }

    public String getPetSex() {
        return petSex;
    }

    public void setPetSex(String petSex) {
        this.petSex = petSex;
    }

    public String getPetCharacter() {
        return petCharacter;
    }

    public void setPetCharacter(String petCharacter) {
        this.petCharacter = petCharacter;
    }

    public String getSingleTicketPrice() {
        return singleTicketPrice;
    }

    public void setSingleTicketPrice(String singleTicketPrice) {
        this.singleTicketPrice = singleTicketPrice;
    }

    public String getPetBoxTypeId() {
        return petBoxTypeId;
    }

    public void setPetBoxTypeId(String petBoxTypeId) {
        this.petBoxTypeId = petBoxTypeId;
    }

    public String getPetBoxTypeName() {
        return petBoxTypeName;
    }

    public void setPetBoxTypeName(String petBoxTypeName) {
        this.petBoxTypeName = petBoxTypeName;
    }

    public String getSingleBoxPrice() {
        return singleBoxPrice;
    }

    public void setSingleBoxPrice(String singleBoxPrice) {
        this.singleBoxPrice = singleBoxPrice;
    }

    public String getQuarantineCertAss() {
        return quarantineCertAss;
    }

    public void setQuarantineCertAss(String quarantineCertAss) {
        this.quarantineCertAss = quarantineCertAss;
    }

    public String getSingleDeclarePrice() {
        return singleDeclarePrice;
    }

    public void setSingleDeclarePrice(String singleDeclarePrice) {
        this.singleDeclarePrice = singleDeclarePrice;
    }
}
