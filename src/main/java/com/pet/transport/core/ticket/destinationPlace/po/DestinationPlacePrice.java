package com.pet.transport.core.ticket.destinationPlace.po;

/**
 * @author zimok
 * @Title: DestinationPlacePrice
 * @ProjectName petTransport
 * @Description: 目的点相关票价
 * @date 2019/1/1120:29
 */
public class DestinationPlacePrice {

    private String startPlaceCode;

    private String destinationPlaceCode;
    //用户类型
    private String userType;
    //最低收费
    private String lowPrice;
    //增加一只收费
    private String pricePerOne;
    //区间费率(元/kg) 收费8kg<N<=20kg
    private String rateIntervalA;
    //区间费率(元/kg) 收费20kg<N<=40kg
    private String rateIntervalB;
    //40kg<N
    private String rateIntervalC;
    //预留三个字段
    private String rateIntervalD;
    //预留三个字段
    private String rateIntervalE;
    //预留三个字段
    private String rateIntervalF;
    //单只超重（>40kg）
    private String  singleOverWeightRate;
    //检疫证明代办费用
    private String quarantineCertCost;

    public String getStartPlaceCode() {
        return startPlaceCode;
    }

    public void setStartPlaceCode(String startPlaceCode) {
        this.startPlaceCode = startPlaceCode;
    }

    public String getDestinationPlaceCode() {
        return destinationPlaceCode;
    }

    public void setDestinationPlaceCode(String destinationPlaceCode) {
        this.destinationPlaceCode = destinationPlaceCode;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(String lowPrice) {
        this.lowPrice = lowPrice;
    }

    public String getPricePerOne() {
        return pricePerOne;
    }

    public void setPricePerOne(String pricePerOne) {
        this.pricePerOne = pricePerOne;
    }

    public String getRateIntervalA() {
        return rateIntervalA;
    }

    public void setRateIntervalA(String rateIntervalA) {
        this.rateIntervalA = rateIntervalA;
    }

    public String getRateIntervalB() {
        return rateIntervalB;
    }

    public void setRateIntervalB(String rateIntervalB) {
        this.rateIntervalB = rateIntervalB;
    }

    public String getRateIntervalC() {
        return rateIntervalC;
    }

    public void setRateIntervalC(String rateIntervalC) {
        this.rateIntervalC = rateIntervalC;
    }

    public String getRateIntervalD() {
        return rateIntervalD;
    }

    public void setRateIntervalD(String rateIntervalD) {
        this.rateIntervalD = rateIntervalD;
    }

    public String getRateIntervalE() {
        return rateIntervalE;
    }

    public void setRateIntervalE(String rateIntervalE) {
        this.rateIntervalE = rateIntervalE;
    }

    public String getRateIntervalF() {
        return rateIntervalF;
    }

    public void setRateIntervalF(String rateIntervalF) {
        this.rateIntervalF = rateIntervalF;
    }

    public String getSingleOverWeightRate() {
        return singleOverWeightRate;
    }

    public void setSingleOverWeightRate(String singleOverWeightRate) {
        this.singleOverWeightRate = singleOverWeightRate;
    }

    public String getQuarantineCertCost() {
        return quarantineCertCost;
    }

    public void setQuarantineCertCost(String quarantineCertCost) {
        this.quarantineCertCost = quarantineCertCost;
    }
}
