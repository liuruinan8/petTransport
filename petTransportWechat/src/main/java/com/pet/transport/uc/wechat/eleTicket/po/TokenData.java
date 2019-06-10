package com.pet.transport.uc.wechat.eleTicket.po;

/*
 * 微信极速发票工具类
 */
public class TokenData {
    // 抬头类型
    private int title_type;
    // 抬头
    private String title;
    // 联系方式
    private String phone;
    // 税号
    private String tax_no;
    // 地址
    private String addr;
    // 银行类型
    private String bank_type;
    // 银卡号
    private String bank_no;

    public int getTitle_type() {
        return title_type;
    }
    public void setTitle_type(int title_type) {
        this.title_type = title_type;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getTax_no() {
        return tax_no;
    }
    public void setTax_no(String tax_no) {
        this.tax_no = tax_no;
    }
    public String getAddr() {
        return addr;
    }
    public void setAddr(String addr) {
        this.addr = addr;
    }
    public String getBank_type() {
        return bank_type;
    }
    public void setBank_type(String bank_type) {
        this.bank_type = bank_type;
    }
    public String getBank_no() {
        return bank_no;
    }
    public void setBank_no(String bank_no) {
        this.bank_no = bank_no;
    }
}