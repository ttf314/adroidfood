package com.example.oders2.po;


import java.io.Serializable;

/**
 * 餐品
 */
public class Cars implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 主键
     */
    private Integer usid;

    /**
     * 商品名称
     */
    private String mname;
    private int mid;
    /**
     * 商品价格
     */
    private String price;

    /**
     * 主图
     */
    private String url;

    /**
     * 描述
     */
    private String description;

    /**
     * 折扣
     */
    private int zk;
    /**
     * 是否有效 0有效 1已删除
     */
    private Integer isDelete;

    @Override
    public String toString() {
        return "Cars{" +
                "id=" + id +
                ", usid=" + usid +
                ", mname='" + mname + '\'' +
                ", mid=" + mid +
                ", price='" + price + '\'' +
                ", url='" + url + '\'' +
                ", description='" + description + '\'' +
                ", zk=" + zk +
                ", isDelete=" + isDelete +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUsid() {
        return usid;
    }

    public void setUsid(Integer usid) {
        this.usid = usid;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getZk() {
        return zk;
    }

    public void setZk(int zk) {
        this.zk = zk;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
}
