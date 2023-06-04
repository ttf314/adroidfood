package com.example.oders2.Gongju;

import java.util.ArrayList;
import java.util.List;

public class GetSQLite {
    /*
      把省份数据封装成集合，为后面调用数据库数据替换作准备
       */
    public List<String> setnum(){
        List<String> num=new ArrayList<>();

        for(int i=1;i<100;i++){
            num.add(String.valueOf(i));
        }
        return num;
    }

    public List<String> setnum2(){
        List<String> num=new ArrayList<>();

        for(int i=1;i<25;i++){
            num.add(String.valueOf("A"+i));
            num.add(String.valueOf("B"+i));
        }
        return num;
    }
}
