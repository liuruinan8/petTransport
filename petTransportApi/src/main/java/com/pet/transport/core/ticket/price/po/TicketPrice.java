package com.pet.transport.core.ticket.price.po;

/**
 * 定价类 主要用于管理端来管理机票的定价
 *
 * 要件为：
 * 出发地
 * 结束地
 * 宠物体重的下限
 * 宠物体重的上限
 * 时间
 *
 * 业务逻辑：
 * 页面初始化未来一段时间的基础信息，客服人员可以维护每天的价格
 *
 */
public class TicketPrice {
        //主键
        private String id;
        //寄送地ID
        private String startPlaceId;
        //寄送地名称
        private String startPlaceName;
        //目的地ID
        private String endPlaceId;
        //目的地名称
        private String endPlaceName;
        //寄送日期
        private String transDate;
        //宠物体重下限
        private String petWeightStart;
        //宠物体重上限
        private String petWeightEnd;
        //建议价格
        private String price;

        public String getId() {
                return id;
        }

        public void setId(String id) {
                this.id = id;
        }

        public String getStartPlaceId() {
                return startPlaceId;
        }

        public void setStartPlaceId(String startPlaceId) {
                this.startPlaceId = startPlaceId;
        }

        public String getStartPlaceName() {
                return startPlaceName;
        }

        public void setStartPlaceName(String startPlaceName) {
                this.startPlaceName = startPlaceName;
        }

        public String getEndPlaceId() {
                return endPlaceId;
        }

        public void setEndPlaceId(String endPlaceId) {
                this.endPlaceId = endPlaceId;
        }

        public String getEndPlaceName() {
                return endPlaceName;
        }

        public void setEndPlaceName(String endPlaceName) {
                this.endPlaceName = endPlaceName;
        }

        public String getTransDate() {
                return transDate;
        }

        public void setTransDate(String transDate) {
                this.transDate = transDate;
        }

        public String getPetWeightStart() {
                return petWeightStart;
        }

        public void setPetWeightStart(String petWeightStart) {
                this.petWeightStart = petWeightStart;
        }

        public String getPetWeightEnd() {
                return petWeightEnd;
        }

        public void setPetWeightEnd(String petWeightEnd) {
                this.petWeightEnd = petWeightEnd;
        }

        public String getPrice() {
                return price;
        }

        public void setPrice(String price) {
                this.price = price;
        }
}
