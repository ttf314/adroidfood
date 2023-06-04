package com.example.oders2.test;


import com.example.oders2.po.Cars;
import com.example.oders2.po.Meals;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Order2 implements Serializable {

    public static class ProductVo implements Serializable {
        public Cars product;
        public int count;
    }

    private int id;
    private int uid;
    private String date;
    private List<Cars> products = new ArrayList<Cars>();
    public Map<Cars, Integer> productsMap = new HashMap();

    public List<ProductVo> ps;
    private int count;
    private float nowrmb;
    private float price;
    private String detel;
    private String weizhi;
    private int isDelete;

    public void addProduct(Cars product) {
        Integer count = productsMap.get(product);
        if (count == null || count <= 0) {
            productsMap.put(product, 1);
        } else {
            productsMap.put(product, count + 1);
        }
    }

    public void removeProduct(Cars product) {
        Integer count = productsMap.get(product);
        if (count == null || count <= 0) {
            return;
        } else {
            productsMap.put(product, count - 1);
        }
    }

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

    public Map<Cars, Integer> getProductsMap() {
        return productsMap;
    }

    public void setProductsMap(Map<Cars, Integer> productsMap) {
        this.productsMap = productsMap;
    }

    public List<ProductVo> getPs() {
        return ps;
    }

    public void setPs(List<ProductVo> ps) {
        this.ps = ps;
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


    public List<Cars> getProducts() {
        return products;
    }

    public void setProducts(List<Cars> products) {
        this.products = products;
    }

}