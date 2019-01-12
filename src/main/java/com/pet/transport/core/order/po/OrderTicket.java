package com.pet.transport.core.order.po;

/**
 * @author zimok
 * @Title: OrderTicket
 * @ProjectName petTransport
 * @Description: 订单机票信息表
 * @date 2019/1/1119:22
 */
public class OrderTicket {
    //主键
    private String id;
    //主键
    private String orderId;
    //订单编号
    private String orderNo;
    //承运航班
    private String flight;
    //航班号
    private String flightNo;
    //起飞时间
    private String takeOffTime;
    //预计到达时间
    private String arriveTime;

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

    public String getFlight() {
        return flight;
    }

    public void setFlight(String flight) {
        this.flight = flight;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public String getTakeOffTime() {
        return takeOffTime;
    }

    public void setTakeOffTime(String takeOffTime) {
        this.takeOffTime = takeOffTime;
    }

    public String getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
    }
}
