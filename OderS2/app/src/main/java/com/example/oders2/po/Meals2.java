package com.example.oders2.po;

public class Meals2 extends Meals{
    private int num;

    public Meals2(Meals meals){
        this.id=meals.getId();
        this.name=meals.getName();
        this.price=meals.getPrice();
        this.scNum=meals.getScNum();
        this.gmNum=meals.getGmNum();
        this.url1=meals.getUrl1();
        this.description=meals.getDesccription();
        this.pam1=meals.getPam1();
        this.val1=meals.getVal1();
        this.type=meals.getType();
        this.zk=meals.getZk();
        this.categoryIdOne=meals.getCategoryIdOne();
        this.categoryIdTwo=meals.getCategoryIdTwo();
        this.isDelete=meals.getIsDelete();
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
