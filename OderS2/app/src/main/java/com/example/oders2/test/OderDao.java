package com.example.oders2.test;


import com.example.oders2.Gongju.DBHelper;
import com.example.oders2.po.Cars;
import com.example.oders2.po.Meals;
import com.example.oders2.po.UserInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用户数据库操作类
 * 实现用户的CRUD操作
 */
public class OderDao extends DBHelper {

    /**
     * 添加用户信息 C
     * @param order 要添加的用户
     * @return int 影响的行数
     */
    public int addOder(Order2 order){
        int iRow = 0;
        Map<Cars, Integer> productsMap = order.productsMap;
        StringBuilder sb = new StringBuilder();
        for (Cars p : productsMap.keySet()) {
            sb.append(p.getId() + "_" + productsMap.get(p));
            sb.append("|");
        }
        sb = sb.deleteCharAt(sb.length() - 1);
        try{
            getConnection();   // 取得连接信息
            //String sql = "insert into userinfo(userName, passWord, createDt, rmb,isDelete) values(?, ?, ?, ?, ?)";
            String sql = "insert into order(detel, count, price) values(?, ?, ?)";
            System.out.println("连接ing");
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, sb.toString());
            pStmt.setString(2, order.getCount()+"");
            pStmt.setString(3, order.getPrice()+"");
            iRow = pStmt.executeUpdate();
        }catch(Exception ex){
            ex.printStackTrace();
        }finally {
            closeAll();
        }
        return iRow;
    }

    /**
     * 修改用户信息 U
     * @param item 要修改的用户
     * @return int 影响的行数
     */
    public int editUser(UserInfo item){
        int iRow = 0;
        try{
            getConnection();   // 取得连接信息
            String sql = "update userinfo set userName=?, passWord=? ,rmb=? where id=?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, item.getUserName());
            pStmt.setString(2, item.getPassWord());
            pStmt.setInt(3, item.getRmb());
            pStmt.setInt(4, item.getId());
            iRow = pStmt.executeUpdate();
        }catch(Exception ex){
            ex.printStackTrace();
        }finally {
            closeAll();
        }
        return iRow;
    }




}
