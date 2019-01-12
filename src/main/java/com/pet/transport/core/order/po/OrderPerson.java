package com.pet.transport.core.order.po;

/**
 * @author zimok
 * @Title: OrderPerson
 * @ProjectName petTransport
 * @Description: 订单发运 收运人信息表
 * @date 2019/1/1119:22
 */
public class OrderPerson {
    //主键
    private String id;
    //主键
    private String orderId;
    //订单编号
    private String orderNo;

    //发运人姓名
    private String deliverName;
    //发运人手机号
    private String deliverMobile;
    //收运人姓名
    private String receiverName;
    //收运人电话
    private String receiverMobile;

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

    public String getDeliverName() {
        return deliverName;
    }

    public void setDeliverName(String deliverName) {
        this.deliverName = deliverName;
    }

    public String getDeliverMobile() {
        return deliverMobile;
    }

    public void setDeliverMobile(String deliverMobile) {
        this.deliverMobile = deliverMobile;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }
}
