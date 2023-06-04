package com.example.oders2.dao;


import com.example.oders2.Gongju.DBHelper;
import com.example.oders2.po.UserInfo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.example.oders2.Gongju.DBHelper.getConnection;

/**
 * 用户数据库操作类
 * 实现用户的CRUD操作
 */
public class UserDao extends DBHelper {

    // 查询所有的用户信息 R
    public List<UserInfo> getAllUserList(){
        List<UserInfo> list = new ArrayList<>();
        try{
            getConnection();   // 取得连接信息
            String sql = "select * from userinfo";
            pStmt = conn.prepareStatement(sql);
            rs = pStmt.executeQuery();
            while(rs.next()){   // 这里原来用的是if，查询数据集合时应使用while
                UserInfo item = new UserInfo();
                item.setId(rs.getInt("id"));
                item.setUserName(rs.getString("userName"));
                item.setPassWord(rs.getString("passWord"));
                item.setPhone(rs.getString("phone"));
                item.setRealname(rs.getString("realName"));
                item.setUserName(rs.getString("sex"));
                item.setPassWord(rs.getString("address"));
                item.setUserName(rs.getString("email"));
                item.setCreateDt(rs.getString("createDt"));
                item.setRmb(rs.getInt("rmb"));
                item.setIsDelete(rs.getInt("isDelete"));
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
     * 按用户名和密码查询用户信息 R
     * @param userName 用户名
     * @param passWord 密码
     * @return UserInfo 实例
     */
    public UserInfo getUserByUnameAndUpass(String userName, String passWord,int isDelete){
        UserInfo item = null;
        try{
            getConnection();   // 取得连接信息
            String sql = "select * from userinfo where userName=? and passWord=? and isDelete=?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, userName);
            pStmt.setString(2, passWord);
            pStmt.setInt(3, isDelete);
            rs = pStmt.executeQuery();
            if(rs.next()){
                item = new UserInfo();
                item.setId(rs.getInt("id"));
                item.setUserName(userName);
                item.setPassWord(passWord);
                item.setPhone(rs.getString("phone"));
                item.setRealname(rs.getString("realname"));
                item.setSex(rs.getString("sex"));
                item.setEmail(rs.getString("email"));
                item.setCreateDt(rs.getString("createDt"));
                item.setRmb(rs.getInt("rmb"));
                item.setIsDelete(rs.getInt("isDelete"));
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally {
            closeAll();
        }
        return item;
    }
    /**
     * 按用户名查询用户信息 R
     * @param userName 用户名
     * @return UserInfo 实例
     */
    public UserInfo getUserByUname(String userName){
        UserInfo item = null;
        try{
            getConnection();   // 取得连接信息
            String sql = "select * from userinfo where userName=? and isDelete=0";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, userName);
            rs = pStmt.executeQuery();
            if(rs.next()){
                item = new UserInfo();
                item.setId(rs.getInt("id"));
                item.setUserName(userName);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally {
            closeAll();
        }
        return item;
    }
    /**
     * 添加用户信息 C
     * @param item 要添加的用户
     * @return int 影响的行数
     */
    public int addUser(UserInfo item){
        int iRow = 0;
        int rmb=1000;
        int isDelete=0;
        try{
            getConnection();   // 取得连接信息
            String sql = "insert into userinfo(userName, passWord, createDt, rmb,isDelete) values(?, ?, ?, ?, ?)";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, item.getUserName());
            pStmt.setString(2, item.getPassWord());
            pStmt.setString(3, item.getCreateDt());
            pStmt.setInt(4, rmb);
            pStmt.setInt(5, isDelete);
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
