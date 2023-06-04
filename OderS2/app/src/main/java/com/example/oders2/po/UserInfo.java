package com.example.oders2.po;

import java.io.Serializable;

public class UserInfo implements Serializable {
    private int id;    // 用户的id
    private String userName;   // 用户名
    private String passWord;   // 用户密码
    private String phone;   // 用户密码
    private String realname;   // 用户真名
    private String sex;   // 用户性别
    private String address;   // 用户地址
    private String email;   // 用户邮箱
    private String createDt;   // 创建时间
    private int rmb;
    private Integer isDelete;

    public UserInfo() {
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", phone='" + phone + '\'' +
                ", realname='" + realname + '\'' +
                ", sex='" + sex + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", createDt='" + createDt + '\'' +
                ", rmb=" + rmb +
                ", isDelete=" + isDelete +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreateDt() {
        return createDt;
    }

    public void setCreateDt(String createDt) {
        this.createDt = createDt;
    }

    public int getRmb() {
        return rmb;
    }

    public void setRmb(int rmb) {
        this.rmb = rmb;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public UserInfo(int id, String userName, String passWord, String phone, String realname, String sex, String address, String email, String createDt, int rmb, Integer isDelete) {
        this.id = id;
        this.userName = userName;
        this.passWord = passWord;
        this.phone = phone;
        this.realname = realname;
        this.sex = sex;
        this.address = address;
        this.email = email;
        this.createDt = createDt;
        this.rmb = rmb;
        this.isDelete = isDelete;
    }
}
