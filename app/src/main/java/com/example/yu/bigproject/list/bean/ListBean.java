package com.example.yu.bigproject.list.bean;

import java.util.List;

public class ListBean {
    String status;
    String message;
    List<Result> result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public class Result{
        String orderId;
        long userId;
        int payMethod;
        double payAmount;
        String expressCompName;
        long orderTime;
        String expressSn;
        long orderStatus;
        public List<Detail> detailList;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public int getPayMethod() {
            return payMethod;
        }

        public void setPayMethod(int payMethod) {
            this.payMethod = payMethod;
        }

        public double getPayAmount() {
            return payAmount;
        }

        public void setPayAmount(double payAmount) {
            this.payAmount = payAmount;
        }

        public String getExpressCompName() {
            return expressCompName;
        }

        public void setExpressCompName(String expressCompName) {
            this.expressCompName = expressCompName;
        }

        public long getOrderTime() {
            return orderTime;
        }

        public void setOrderTime(long orderTime) {
            this.orderTime = orderTime;
        }

        public String getExpressSn() {
            return expressSn;
        }

        public void setExpressSn(String expressSn) {
            this.expressSn = expressSn;
        }

        public long getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(long orderStatus) {
            this.orderStatus = orderStatus;
        }

        public List<Detail> getDetailList() {
            return detailList;
        }

        public void setDetailList(List<Detail> detailList) {
            this.detailList = detailList;
        }

        public class Detail{
            int orderDetailId;
            int commodityId;
            String commodityName;
            int commodityCount;
            String commodityPic;
            double commodityPrice;
            int commentStatus;

            public int getOrderDetailId() {
                return orderDetailId;
            }

            public void setOrderDetailId(int orderDetailId) {
                this.orderDetailId = orderDetailId;
            }

            public int getCommodityId() {
                return commodityId;
            }

            public void setCommodityId(int commodityId) {
                this.commodityId = commodityId;
            }

            public String getCommodityName() {
                return commodityName;
            }

            public void setCommodityName(String commodityName) {
                this.commodityName = commodityName;
            }

            public int getCommodityCount() {
                return commodityCount;
            }

            public void setCommodityCount(int commodityCount) {
                this.commodityCount = commodityCount;
            }

            public String getCommodityPic() {
                return commodityPic;
            }

            public void setCommodityPic(String commodityPic) {
                this.commodityPic = commodityPic;
            }

            public double getCommodityPrice() {
                return commodityPrice;
            }

            public void setCommodityPrice(double commodityPrice) {
                this.commodityPrice = commodityPrice;
            }

            public int getCommentStatus() {
                return commentStatus;
            }

            public void setCommentStatus(int commentStatus) {
                this.commentStatus = commentStatus;
            }
        }
    }
}
