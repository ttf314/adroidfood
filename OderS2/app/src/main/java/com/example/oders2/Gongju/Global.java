package com.example.oders2.Gongju;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;


public class Global {//定义一些全局变量
	public static int sourceAreaId =2;// 生源地Id,默认为江西
	public static int targetAreaId=2;// 意向省份Id
	public static int schoolAreaId=2;
	public static int batchId = 1;// 批次id
	public static int categoryId = 1;// 科目id
	public static String DB_PATH;// 数据库的路径
	public static String DB_NAME = "bkt.db";// 数据库的名称
//		public static String URL="http://10.0.2.2:8080/bkt/";//本地服务器地址test1_war_exploded
	public static String URL="http://"+Constants.url2+":8080/test1_war_exploded" +
		"/";//本地服务器地址
	public static String url="http://"+Constants.url2+":8080/test1_war_exploded";
	/*public static String URL="http://192.168.10.108:8080/test1-0.0.1-SNAPSHOT" +
			"/";//本地服务器地址*/
//	public static String URL="http://www.drhelp.cn/bkt/";//网络服务器地址
	public static DBUtil dbUtil;
	public static SQLiteDatabase db;
	public static SharedPreferences initPreferences;//本地保存的信息

}
