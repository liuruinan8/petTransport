package com.pet.transport.core.order.po;

public class Order {
    //主键
    private String id;
    //订单编号
    private String orderNo;
    //寄送地ID
    private String startPlaceCode;
    //寄送地名称
    private String startPlaceName;
    //目的地ID
    private String distinationPlaceCode;
    //目的地名称
    private String distinationPlaceName;
    //寄送日期
    private String transDate;
    //宠物类型
    private String petKind;
    //宠物体重
    private String petWeight;
    //价格
    private String ticketPrice;
    //航空箱
    private String petBoxTypeId;
    //航空箱名称
    private String petBoxTypeName;
    //航空箱价格
    private String petBoxPrice;
    //上门地址所在地区划
    private String placeAreaCode;
    //上门地址所在地区划名称
    private String placeAreaName;
    //上门地址
    private String placeDetail;
    //上门价格
    private String placePrice;
    //用户ID
    private String userId;
    //用户联系方式
    private String userMobile;
    //保价
    private String insuredPrice;
    //总价
    private String totalPrice;
    //订单状态
    private String orderStatus;
    //支付状态
    private String payStatus;
    //支付单号
    private String paySerialNo;
    //支付账户
    private String payAccount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDistinationPlaceCode() {
        return distinationPlaceCode;
    }

    public void setDistinationPlaceCode(String distinationPlaceCode) {
        this.distinationPlaceCode = distinationPlaceCode;
    }

    public String getDistinationPlaceName() {
        return distinationPlaceName;
    }

    public void setDistinationPlaceName(String distinationPlaceName) {
        this.distinationPlaceName = distinationPlaceName;
    }

    public String getPetKind() {
        return petKind;
    }

    public void setPetKind(String petKind) {
        this.petKind = petKind;
    }

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }

    public String getPetWeight() {
        return petWeight;
    }

    public void setPetWeight(String petWeight) {
        this.petWeight = petWeight;
    }

    public String getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(String ticketPrice) {
        this.ticketPrice = ticketPrice;
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

    public String getPetBoxPrice() {
        return petBoxPrice;
    }

    public void setPetBoxPrice(String petBoxPrice) {
        this.petBoxPrice = petBoxPrice;
    }

    public String getPlaceAreaCode() {
        return placeAreaCode;
    }

    public void setPlaceAreaCode(String placeAreaCode) {
        this.placeAreaCode = placeAreaCode;
    }

    public String getPlaceAreaName() {
        return placeAreaName;
    }

    public void setPlaceAreaName(String placeAreaName) {
        this.placeAreaName = placeAreaName;
    }

    public String getPlaceDetail() {
        return placeDetail;
    }

    public void setPlaceDetail(String placeDetail) {
        this.placeDetail = placeDetail;
    }

    public String getPlacePrice() {
        return placePrice;
    }

    public void setPlacePrice(String placePrice) {
        this.placePrice = placePrice;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getInsuredPrice() {
        return insuredPrice;
    }

    public void setInsuredPrice(String insuredPrice) {
        this.insuredPrice = insuredPrice;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getPaySerialNo() {
        return paySerialNo;
    }

    public void setPaySerialNo(String paySerialNo) {
        this.paySerialNo = paySerialNo;
    }

    public String getPayAccount() {
        return payAccount;
    }

    public void setPayAccount(String payAccount) {
        this.payAccount = payAccount;
    }
    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
