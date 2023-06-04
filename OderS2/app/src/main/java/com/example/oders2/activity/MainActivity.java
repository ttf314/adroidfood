package com.example.oders2.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.oders2.Gongju.AccessToServer;
import com.example.oders2.Gongju.CommonUtils;
import com.example.oders2.Gongju.Constants;
import com.example.oders2.Gongju.Global;
import com.example.oders2.Gongju.Util;
import com.example.oders2.R;
import com.example.oders2.adapter.ItemCategoryAdapter;
import com.example.oders2.dao.UserDao;
import com.example.oders2.po.ItemCategory;
import com.example.oders2.po.Meals;
import com.example.oders2.po.UserInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private int uid;
    private TextView btn_zuche;
    private Button btn_query_count, btn_login;
    private EditText et_uname, et_upass;  // 用户名、密码框
    private UserDao dao;   // 用户数据库操作类
    private Handler mainHandler ;     // 主线程
    //public static Map<String, Object> m = new HashMap();
    public static Map m = new HashMap();
    public static UserInfo userInfo = new UserInfo();
    public Gson gson = new Gson();
    private List<UserInfo> userInfoList;//数据集合
    private String userString,usernam,userid,result;// 字符串
    private Message message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){
        et_uname = findViewById(R.id.et_uname);
        et_upass = findViewById(R.id.et_upass);
        btn_login = findViewById(R.id.btn_login);
        btn_zuche=findViewById(R.id.btn_zuche);
        dao = new UserDao();
        mainHandler = new Handler(getMainLooper());   // 获取主线程

        btn_login.setOnClickListener(this);
        btn_zuche.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:    // 登录按钮
                try
                {
                    doLogup();
                }
                catch (Exception e)
                {
                    CommonUtils.showShortMsg(this, "错误！！！");
                    throw e;
                }
                break;
            case R.id.btn_zuche:    // 注册跳转按钮
                Intent intent = new Intent(this, ZhuCheActivity.class);
                startActivityForResult(intent, 1);
                break;
        }
    }
    // 执行登录操作
    private void doLogup() {
        final String uname = et_uname.getText().toString().trim();
        final String upass = et_upass.getText().toString().trim();
        usernam= et_uname.getText().toString().trim();

        userInfo.setUserName(uname);
        userInfo.setPassWord(upass);
        userString=gson.toJson(userInfo);

        if (TextUtils.isEmpty(uname)) {
            CommonUtils.showShortMsg(this, "请输入用户名");
            et_uname.requestFocus();
        } else if (TextUtils.isEmpty(upass)) {
            CommonUtils.showShortMsg(this, "请输入用户密码");
            et_upass.requestFocus();
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    doLogup2();
                }
            }).start();
        }
    }

    private void doLogup2() {
        if (Util.isNetworkConnected(this)) {
            new Thread() {
                public void run() {// 线程执行体
                    AccessToServer accessToServer = new AccessToServer();
                    result = accessToServer.doPost(Global.URL
                                    + "UserServlet", new String[]{"userString"},
                            new String[]{userString});
                    message = Message.obtain();
                    message.obj = result;
                    message.what = Constants.SUBMIT_QUESTION;
                    //request(message);
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            result = (String) message.obj;
                            try {
                                userInfoList = gson.fromJson(result,
                                        new TypeToken<List<UserInfo>>() {
                                        }.getType());
                            }catch (Exception e){
                            }
                            try {
                                //System.out.println(userInfoList.get(0).getRmb() + "-----------");

                                m.put("name", et_uname.getText().toString().trim());
                                m.put("id", userInfoList.get(0).getId()+"");
                                m.put("rmb", userInfoList.get(0).getRmb()+"");
                                m.put("passWord", userInfoList.get(0).getPassWord()+"");
                                m.put("realname", userInfoList.get(0).getRealname()+"");
                                m.put("phone", userInfoList.get(0).getPhone()+"");
                                m.put("sex", userInfoList.get(0).getSex()+"");
                                m.put("address", userInfoList.get(0).getAddress()+"");
                                m.put("email", userInfoList.get(0).getEmail()+"");
                                Intent intent = new Intent(MainActivity.this, UserMainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);;
                                intent.putExtra("item", m+"");
                                CommonUtils.showLonMsg(MainActivity.this,
                                        "登录成功，进入用户界面");
                                //System.out.println("==========="+m);
                                startActivity(intent);
                            }catch (Exception e){
                                CommonUtils.showDlgMsg(MainActivity.this,
                                        "用户名或密码错误");
                            }

                        }
                    });
                }
            }.start();
        }
    }
}


