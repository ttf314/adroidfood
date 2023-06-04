package com.example.oders2.po;


import java.io.Serializable;

public class Evaluates implements Serializable {
    private int id;    // id
    private int uid;    // 用户uid
    private int did;    // 订单did
    private String vdedtel;    // 用户评价vdedtel
    private String times;
    private String mealname;
    private int isDelete;    //

    public String getMealname() {
        return mealname;
    }

    public void setMealname(String mealname) {
        this.mealname = mealname;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
    }

    public String getVdedtel() {
        return vdedtel;
    }

    public void setVdedtel(String vdedtel) {
        this.vdedtel = vdedtel;
    }
}
