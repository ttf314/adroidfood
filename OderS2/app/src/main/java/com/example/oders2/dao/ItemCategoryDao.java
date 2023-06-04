package com.example.oders2.dao;

import com.example.oders2.Gongju.DBHelper;
import com.example.oders2.po.ItemCategory;

import java.util.ArrayList;
import java.util.List;

public class ItemCategoryDao extends DBHelper {
    // 查询餐品类别信息
    public List<ItemCategory> getItemCategoryList(){
        List<ItemCategory> list = new ArrayList<>();
        try{
            getConnection();   // 取得连接信息
            String sql = "select * from item_category where isDelete = 0 and pid is not null";
            pStmt = conn.prepareStatement(sql);
            rs = pStmt.executeQuery();
            while(rs.next()){   // 这里原来用的是if，查询数据集合时应使用while
                ItemCategory item = new ItemCategory();
                item.setId(rs.getInt("id"));
                item.setName(rs.getString("name"));
                item.setPid(rs.getInt("pid"));
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
}

