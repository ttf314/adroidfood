package com.example.oders2.dao;


import android.os.Message;

import com.example.oders2.Gongju.AccessToServer;
import com.example.oders2.Gongju.CommonUtils;
import com.example.oders2.Gongju.Constants;
import com.example.oders2.Gongju.DBHelper;
import com.example.oders2.Gongju.Global;
import com.example.oders2.po.Cars;
import com.example.oders2.po.Meals;
import com.example.oders2.po.UserInfo;
import com.example.oders2.test.Order2;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.oders2.activity.MainActivity.m;

/**
 * 用户数据库操作类
 * 实现用户的CRUD操作
 */
public class OderDao2 extends DBHelper {
    Order2 order2 = new Order2();
    private String orderString;
    private String uid;

    public Gson gson = new Gson();
    public int addOder(Order2 order){
        int iRow = 0;
        Map<Cars, Integer> productsMap = order.productsMap;
        StringBuilder sb = new StringBuilder();
        for (Cars p : productsMap.keySet()) {
            sb.append(p.getId() + "_" + p.getMname() + "_" +productsMap.get(p));
            sb.append("|");
        }
        sb = sb.deleteCharAt(sb.length() - 1);
        try{
            //order.setProducts(sb.toString());
            System.out.println(m.get("rmb")+"人民币");
            double a = Double.parseDouble(m.get("rmb").toString());
            double b = (double)order.getPrice();
            if(a<b){
                System.out.println("钱不够小了");
            }else {
                double c=a-b;
                m.put("rmb",c);
                System.out.println("大");

                System.out.println("--------------"+m.get("rmb")+"人民币2");
                order2.setUid((Integer) m.get("id"));
                order2.setPrice(order.getPrice());
                order2.setCount(order.getCount());
                order2.setDate(CommonUtils.getDateStrFromNow());
                order2.setDetel(sb.toString());
                order.setWeizhi("一号座位");
                order2.setIsDelete(0);
                orderString = gson.toJson(order2);
                AccessToServer accessToServer4 = new AccessToServer();
                String result = accessToServer4.doPost(Global.URL
                                + "OrderServlet", new String[]{"orderString"},
                        new String[]{
                                orderString+""});
                Message message = Message.obtain();
                message.obj = result;
                message.what = Constants.SUBMIT_QUESTION;

            }



           /* getConnection();   // 取得连接信息
            //String sql = "insert into userinfo(userName, passWord, createDt, rmb,isDelete) values(?, ?, ?, ?, ?)";
            //String sql = "insert into order(det,count,price) values(?,?,?)";
            String sql = "insert into od(ct,pri,det) values(?,?,?)";
            System.out.println("连接ing");
            pStmt = conn.prepareStatement(sql);
            System.out.println("连接2");
            //pStmt.setString(1, sb.toString());
            System.out.println("连接3");
            pStmt.setString(1, order.getCount()+"");
            System.out.println("连接4");
            pStmt.setString(2, order.getPrice()+"");
            pStmt.setString(3, sb.toString());
            System.out.println("连接5");

            System.out.println("------------------"+order.getCount()+"----"+order.getPrice()+"---"+"------+"+sb.toString());
            iRow = pStmt.executeUpdate();*/

        }catch(Exception ex){
            ex.printStackTrace();
        }finally {
            closeAll();
        }
        return iRow;
    }



}
