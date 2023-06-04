package com.example.oders2.po;

public class Meals {
    /**
     * 主键
     */
    protected Integer id;

    /**
     * 商品名称
     */
    protected String name;

    /**
     * 商品价格
     */
    protected String price;

    /**
     * 收藏数
     */
    protected Integer scNum;

    /**
     * 购买数
     */
    protected Integer gmNum;

    /**
     * 主图
     */
    protected String url1;


    /**
     * 描述
     */
    protected String description;

    protected String pam1;

    protected String val1;

    protected Integer type;
    /**
     * 折扣
     */
    protected Integer zk;
    /**
     * 类别id一级
     */
    protected Integer categoryIdOne;
    //驼峰命名，关联链接ItemCategory
    protected ItemCategory yiji;

    /**
     * 类别id二级
     */
    protected Integer categoryIdTwo;
    //驼峰命名，关联链接ItemCategory
    protected ItemCategory erji;

    /**
     * 是否有效 0有效 1已删除
     */
    protected Integer isDelete;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getScNum() {
        return scNum;
    }

    public void setScNum(Integer scNum) {
        this.scNum = scNum;
    }

    public Integer getGmNum() {
        return gmNum;
    }

    public void setGmNum(Integer gmNum) {
        this.gmNum = gmNum;
    }

    public String getUrl1() {
        return url1;
    }

    public void setUrl1(String url1) {
        this.url1 = url1;
    }

    public String getDesccription() {
        return description;
    }

    public void setDesccription(String desccription) {
        this.description = desccription;
    }

    public String getPam1() {
        return pam1;
    }

    public void setPam1(String pam1) {
        this.pam1 = pam1;
    }

    public String getVal1() {
        return val1;
    }

    public void setVal1(String val1) {
        this.val1 = val1;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getZk() {
        return zk;
    }

    public void setZk(Integer zk) {
        this.zk = zk;
    }

    public Integer getCategoryIdOne() {
        return categoryIdOne;
    }

    public void setCategoryIdOne(Integer categoryIdOne) {
        this.categoryIdOne = categoryIdOne;
    }

    public ItemCategory getYiji() {
        return yiji;
    }

    public void setYiji(ItemCategory yiji) {
        this.yiji = yiji;
    }

    public Integer getCategoryIdTwo() {
        return categoryIdTwo;
    }

    public void setCategoryIdTwo(Integer categoryIdTwo) {
        this.categoryIdTwo = categoryIdTwo;
    }

    public ItemCategory getErji() {
        return erji;
    }

    public void setErji(ItemCategory erji) {
        this.erji = erji;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public String toString() {
        return "meals{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", scNum=" + scNum +
                ", gmNum=" + gmNum +
                ", url1='" + url1 + '\'' +
                ", desccription='" + description + '\'' +
                ", pam1='" + pam1 + '\'' +
                ", val1='" + val1 + '\'' +
                ", type=" + type +
                ", zk=" + zk +
                ", categoryIdOne=" + categoryIdOne +
                ", yiji=" + yiji +
                ", categoryIdTwo=" + categoryIdTwo +
                ", erji=" + erji +
                ", isDelete=" + isDelete +
                '}';
    }


}
