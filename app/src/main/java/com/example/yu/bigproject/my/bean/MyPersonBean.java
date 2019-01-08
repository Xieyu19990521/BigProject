package com.example.yu.bigproject.my.bean;

import java.util.List;

public class MyPersonBean {
    String message;
    String status;
    Result result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public class Result{
        long createTime;
        String headPic;
        String nickName;
        String password;
        String phone;
        long sex;
        long userId;

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getHeadPic() {
            return headPic;
        }

        public void setHeadPic(String headPic) {
            this.headPic = headPic;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public long getSex() {
            return sex;
        }

        public void setSex(long sex) {
            this.sex = sex;
        }

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }
    }
}
