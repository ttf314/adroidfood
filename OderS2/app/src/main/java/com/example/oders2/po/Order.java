package com.example.oders2.po;


import java.io.Serializable;

public class Order implements Serializable {


    private int id;
    private int uid;
    private String date;
    private int count;
    private float nowrmb;
    private float price;
    private String detel;
    private String weizhi;
    private int isDelete;

    public float getNowrmb() {
        return nowrmb;
    }

    public void setNowrmb(float nowrmb) {
        this.nowrmb = nowrmb;
    }

    public String getWeizhi() {
        return weizhi;
    }

    public void setWeizhi(String weizhi) {
        this.weizhi = weizhi;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getDetel() {
        return detel;
    }

    public void setDetel(String detel) {
        this.detel = detel;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }




}