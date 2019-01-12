package com.pet.transport.uc.user.po;

public class User {
    private String userId;
    private String userName;
    private String userPassword;
    private String userMobile;
    /**
     * SNS appid
     * */
    private String appId;
    /**
     * 接口访问凭证 第一次保存时存储 如果以后userId为空而且token过期的数据 可以删除处理。
     * access_token的有效时间是比较短的，大概：QQ的是30天，微信的是2小时，新浪的是7天。
     * */
    private String accessToken;
    /**
     *  凭证有效期，单位：秒
     */
    private String expiresIn;
    /**
     *  创建时间
     */
    private String createTime;
    /**
     * refresh_token 时间较长 都是30天
     */
    private String refreshToken;
    /**
     * 是否关注
     * */
    private String subscribe;
    /**
     * 关注时间
     * */
    private String subscribeTime;
    /**
     * 昵称
     * */
    private String nickname;
    /**
     * 用户的性别（1是男性，2是女性，0是未知）
     * */
    private String  sex;
    /**
     * 用户所在国家
     * */
    private String country;
    /**
     * 用户所在省份
     */
    private String province;
    /**
     * 用户所在城市
     */
    private String city;
    /**
     * * 用户的语言，简体中文为zh_CN
     */
    private String language;
    /**
     * 用户头像
     */
    private String headImgUrl;

    /**
     * 是否通过实名认证。T是通过 F是没有实名认证。
     */
    private String isCertified;
    /**
     * openId:一个公众号对应一个
     * */
    private String openId;
    /**
     * 用户类型 superadmin admin plain jxs
     */
    private String userType;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(String subscribe) {
        this.subscribe = subscribe;
    }

    public String getSubscribeTime() {
        return subscribeTime;
    }

    public void setSubscribeTime(String subscribeTime) {
        this.subscribeTime = subscribeTime;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getIsCertified() {
        return isCertified;
    }

    public void setIsCertified(String isCertified) {
        this.isCertified = isCertified;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
