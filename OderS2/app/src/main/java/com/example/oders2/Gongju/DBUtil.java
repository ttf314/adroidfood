package com.example.oders2.Gongju;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;



//���ݿ����������
public class DBUtil {

	public DBUtil(Context context){
		Global.initPreferences = context.getSharedPreferences("dbInfo",
				Context.MODE_PRIVATE);// ��ʼ�����ݿⱣ����Ϣ
		boolean isInitDB=Global.initPreferences.getBoolean("initDB", false);
		if(!isInitDB){//���û�г�ʼ�����ݿ�
			//initDB(context);//����ǵ�һ��ʹ�ã���ִ�г�ʼ��
			Global.initPreferences.edit().putString("dbPath", Global.DB_PATH).commit();
			Global.initPreferences.edit().putBoolean("initDB",true).commit();//�������ݲ��ύ
		}else{
			Global.DB_PATH=Global.initPreferences.getString("dbPath","");
		}
		System.out.println("�������ݿ�·����"+Global.DB_PATH);
	}

	public SQLiteDatabase getDataBase(Context context) {//��ָ�������ݿ�
		return Global.db=context.openOrCreateDatabase(Global.DB_PATH, Context.MODE_PRIVATE, null);	//�����ݿ�
	}

	public List<String> getField(Context context, String sql) {//��ȡ���ݿ����ĳһ�ֶε�ֵ�ļ���
		List<String> fields = new ArrayList<String>();
		SQLiteDatabase db=getDataBase(context);
		Cursor cursor = db.rawQuery(sql, null);
		while (cursor.moveToNext()) {
			fields.add(cursor.getString(0));
		}
		db.close();
		return fields;
	}

}
