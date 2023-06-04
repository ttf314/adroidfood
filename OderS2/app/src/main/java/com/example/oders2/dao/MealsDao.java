package com.example.oders2.dao;


import com.example.oders2.Gongju.DBHelper;
import com.example.oders2.po.Meals;
import com.example.oders2.po.UserInfo;

import java.util.ArrayList;
import java.util.List;

import static com.example.oders2.Gongju.DBHelper.getConnection;
import static com.example.oders2.Gongju.DBHelper.pStmt;


public class MealsDao extends DBHelper {
    // 查询上架了的食物信息
    public List<Meals> getAllMealsList(){
        List<Meals> list = new ArrayList<>();
        try{
            getConnection();   // 取得连接信息
            String sql = "select * from meals where isDelete = 0";
            pStmt = conn.prepareStatement(sql);
            rs = pStmt.executeQuery();
            while(rs.next()){   // 这里原来用的是if，查询数据集合时应使用while
                Meals item = new Meals();
                item.setId(rs.getInt("id"));
                item.setName(rs.getString("name"));
                item.setPrice(rs.getString("price"));
                item.setScNum(rs.getInt("scNum"));
                item.setGmNum( rs.getInt("gmNum"));
                item.setUrl1(rs.getString("url1"));
                item.setDesccription(rs.getString("description"));
                item.setPam1( rs.getString("pam1"));
                item.setVal1(rs.getString("val1"));
                item.setType( rs.getInt("type"));
                item.setZk( rs.getInt("zk"));
                item.setCategoryIdOne( rs.getInt("category_id_one"));
                item.setCategoryIdTwo( rs.getInt("category_id_two"));
                item.setIsDelete( rs.getInt("isDelete"));
                list.add(item);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally {
            closeAll();
        }
        return list;
    }

    // 模糊查询食物信息
    public List<Meals> getMealsList(String name){
        List<Meals> list = new ArrayList<>();
        try{
            getConnection();   // 取得连接信息
            String sql = "select * from meals where isDelete = 0 and name like'%"+name+"%'";
            pStmt = conn.prepareStatement(sql);
            //pStmt.setString(1, name);
            rs = pStmt.executeQuery();
            while(rs.next()){   // 这里原来用的是if，查询数据集合时应使用while
                Meals item = new Meals();
                item.setId(rs.getInt("id"));
                item.setName(rs.getString("name"));
                item.setPrice(rs.getString("price"));
                item.setScNum(rs.getInt("scNum"));
                item.setGmNum( rs.getInt("gmNum"));
                item.setUrl1(rs.getString("url1"));
                item.setDesccription(rs.getString("description"));
                item.setPam1( rs.getString("pam1"));
                item.setVal1(rs.getString("val1"));
                item.setType( rs.getInt("type"));
                item.setZk( rs.getInt("zk"));
                item.setCategoryIdOne( rs.getInt("category_id_one"));
                item.setCategoryIdTwo( rs.getInt("category_id_two"));
                item.setIsDelete( rs.getInt("isDelete"));
                list.add(item);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally {
            closeAll();
        }
        return list;
    }
    // 列表查询食物信息
    public List<Meals> getMealsList2(String category_id_two){
        List<Meals> list = new ArrayList<>();
        try{
            getConnection();   // 取得连接信息
            String sql = "select * from meals where isDelete = 0 and category_id_two =?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, category_id_two);
            rs = pStmt.executeQuery();
            while(rs.next()){   // 这里原来用的是if，查询数据集合时应使用while
                Meals item = new Meals();
                item.setId(rs.getInt("id"));
                item.setName(rs.getString("name"));
                item.setPrice(rs.getString("price"));
                item.setScNum(rs.getInt("scNum"));
                item.setGmNum( rs.getInt("gmNum"));
                item.setUrl1(rs.getString("url1"));
                item.setDesccription(rs.getString("description"));
                item.setPam1( rs.getString("pam1"));
                item.setVal1(rs.getString("val1"));
                item.setType( rs.getInt("type"));
                item.setZk( rs.getInt("zk"));
                item.setCategoryIdOne( rs.getInt("category_id_one"));
                item.setCategoryIdTwo( rs.getInt("category_id_two"));
                item.setIsDelete( rs.getInt("isDelete"));
                list.add(item);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally {
            closeAll();
        }
        return list;
    }

    /**
     * 按餐品名称查询用户信息
     * @return Meals 实例
     */
    public Meals getMealsByname(String name,int isDelete){
        Meals item = null;
        try{
            getConnection();   // 取得连接信息
            String sql = "select * from meals where name=? and isDelete=?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, name);
            pStmt.setInt(2, isDelete);
            rs = pStmt.executeQuery();
            if(rs.next()){
                item = new Meals();
                item.setId(rs.getInt("id"));
                item.setName(rs.getString("name"));
                item.setPrice(rs.getString("price"));
                item.setScNum(rs.getInt("scNum"));
                item.setGmNum( rs.getInt("gmNum"));
                item.setUrl1(rs.getString("url1"));
                item.setDesccription(rs.getString("description"));
                item.setPam1( rs.getString("pam1"));
                item.setVal1(rs.getString("val1"));
                item.setType( rs.getInt("type"));
                item.setZk( rs.getInt("zk"));
                item.setCategoryIdOne( rs.getInt("category_id_one"));
                item.setCategoryIdTwo( rs.getInt("category_id_two"));
                item.setIsDelete( rs.getInt("isDelete"));
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally {
            closeAll();
        }
        return item;
    }

    /**
     * 修改用户信息 U
     * @return int 影响的行数
     */
    public int editMeals(int type,int id ){
        int iRow = 0;
        try{
            getConnection();   // 取得连接信息
            String sql = "update meals set type=? where id=?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1,type);
            pStmt.setInt(2, id);
            iRow = pStmt.executeUpdate();
        }catch(Exception ex){
            ex.printStackTrace();
        }finally {
            closeAll();
        }
        return iRow;
    }
}
