package com.pet.transport.core.ticket.petKind.po;

public class PetKind {
    /**
     * 类型ID
     */
    private String kindId;
    /**
     * 类型名称
     */
    private String kindName;
    /**
     * 类型助记码
     */
    private String kindCode;
    /**
     * 类型名称
     */
    private String kindNameSimple;
    /**
     * 类型创建时间
     */
    private String createTime;
    /**
     * 类型是否可用
     */
    private String isUse;
    /**
     * 类型创建人
     */
    private String creatorId;
    /**
     * 类型创建人名称
     */
    private String creatorName;

    public String getKindId() {
        return kindId;
    }

    public void setKindId(String kindId) {
        this.kindId = kindId;
    }

    public String getKindName() {
        return kindName;
    }

    public void setKindName(String kindName) {
        this.kindName = kindName;
    }

    public String getKindCode() {
        return kindCode;
    }

    public void setKindCode(String kindCode) {
        this.kindCode = kindCode;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getIsUse() {
        return isUse;
    }

    public void setIsUse(String isUse) {
        this.isUse = isUse;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getKindNameSimple() {
        return kindNameSimple;
    }

    public void setKindNameSimple(String kindNameSimple) {
        this.kindNameSimple = kindNameSimple;
    }
}
