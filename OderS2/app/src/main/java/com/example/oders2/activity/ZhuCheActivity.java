package com.example.oders2.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.example.oders2.Gongju.AccessToServer;
import com.example.oders2.Gongju.CommonUtils;
import com.example.oders2.Gongju.Constants;
import com.example.oders2.Gongju.Global;
import com.example.oders2.Gongju.Util;
import com.example.oders2.R;
import com.example.oders2.dao.UserDao;
import com.example.oders2.po.Cars;
import com.example.oders2.po.UserInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import static com.example.oders2.activity.MainActivity.m;

public class ZhuCheActivity extends AppCompatActivity {
    private EditText et_uname, et_upass,et_upass2;

    private UserDao userDao;   // 用户数据操作类实例
    private Handler mainHandler;
    public Gson gson = new Gson();
    private int num;
    private String a;
    private UserInfo userInfo;
    private boolean isFinishedInit = false;//是否完成初始化
    private String userString,usernam,userid,result;// 用户信息字符串
    private Message message;
    private List<UserInfo> userInfoList;//数据集合
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhu_che);

        et_uname = findViewById(R.id.et_uname);
        et_upass = findViewById(R.id.et_upass);
        et_upass2 = findViewById(R.id.et_upass2);
        userDao = new UserDao();
        mainHandler = new Handler(getMainLooper());

    }
    // 确定按钮的点击事件处理
    public void btn_ok_click(View view){

        final String uname = et_uname.getText().toString().trim();
        final String upass = et_upass.getText().toString().trim();
        final String upass2 = et_upass2.getText().toString().trim();
        usernam= et_uname.getText().toString().trim();
        UserInfo userInfo2=new UserInfo();
        userInfo2.setUserName(uname);
        userInfo2.setPassWord(upass);
        userInfo2.setCreateDt(CommonUtils.getDateStrFromNow());
        userString=gson.toJson(userInfo2);

        //CommonUtils.showShortMsg(ZhuCheActivity.this, "1111111");
        et_uname.requestFocus();
        if(TextUtils.isEmpty(uname)){
            CommonUtils.showShortMsg(ZhuCheActivity.this, "请输入用户名");
            et_uname.requestFocus();
            System.out.println("请输入用户名");
        } else if (uname.length() < 3 || uname.length() > 16) {
            CommonUtils.showShortMsg(ZhuCheActivity.this, "用户名必须为3-16个字符");
            et_uname.requestFocus();
        } else if(TextUtils.isEmpty(upass)){
            System.out.println("请输入用户密码");
            CommonUtils.showShortMsg(ZhuCheActivity.this, "请输入用户密码");
            et_upass.requestFocus();
        } else if (upass.length() < 1 || upass.length() > 16) {
            CommonUtils.showShortMsg(ZhuCheActivity.this, "密码必须为1-16个字符");
            et_upass.requestFocus();
        } else if(!upass2.equals(upass)) {
            CommonUtils.showShortMsg(ZhuCheActivity.this, "两次密码不一样");
            et_upass.requestFocus();
        } else {
            new Thread() {
                public void run() {
                    //showLvData3(message5);
                    doLogin();
                }
            }.start();
        }
        /*if(TextUtils.isEmpty(uname)){
            CommonUtils.showShortMsg(ZhuCheActivity.this, "请输入用户名");
            et_uname.requestFocus();
            System.out.println("请输入用户名");
        }else if(TextUtils.isEmpty(upass)){
            System.out.println("请输入用户密码");
            CommonUtils.showShortMsg(ZhuCheActivity.this, "请输入用户密码");
            et_upass.requestFocus();
        }else if(!upass2.equals(upass)) {
            CommonUtils.showShortMsg(ZhuCheActivity.this, "两次密码不一样");
            et_upass.requestFocus();
        }else {
            new Thread() {
                public void run() {
                    //showLvData3(message5);
                    doLogin();
                }
            }.start();
        }*/

         /*
        new Thread(new Runnable() {
            @Override
            public void run() {
                doLogin();
               //数据库查用户名
                final UserInfo item = userDao.getUserByUname(uname);
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (item == null) {
                            if(TextUtils.isEmpty(uname)){
                                CommonUtils.showShortMsg(ZhuCheActivity.this, "请输入用户名");
                                et_uname.requestFocus();
                                System.out.println("请输入用户名");
                            }else if(TextUtils.isEmpty(upass)){
                                System.out.println("请输入用户密码");
                               CommonUtils.showShortMsg(ZhuCheActivity.this, "请输入用户密码");
                                et_upass.requestFocus();
                            }else{
                                final UserInfo item = new UserInfo();
                                item.setUserName(uname);
                                item.setPassWord(upass);
                                item.setCreateDt(CommonUtils.getDateStrFromNow());
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        final int iRow = userDao.addUser(item);
                                        mainHandler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                setResult(1);   // 使用参数表示当前界面操作成功，并返回管理界面
                                                finish();
                                            }
                                        });
                                    }
                                }).start();
                            }

                            isFinishedInit=true;
                        } else {
                            CommonUtils.showShortMsg(ZhuCheActivity.this, "该用户名称已经注册");
                            et_upass.requestFocus();
                            isFinishedInit=false;
                        }
                    }
                });
            }
        }).start();
        //name= String.valueOf(item3.getUname());

*/

    }
    private void doLogin() {
        if (Util.isNetworkConnected(this)) {
            new Thread() {
                public void run() {// 线程执行体
                    AccessToServer accessToServer = new AccessToServer();
                    String result = accessToServer.doPost(Global.URL
                                    + "UserServlet2", new String[] { "userString"},
                            new String[] { userString });
                    Message message2 = Message.obtain();
                    message2.obj = result;
                    message2.what = Constants.GET_CONTROL_LINE;
                    mHandler.sendMessage(message2);
                }
            }.start();
        }

    }
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.GET_CONTROL_LINE:
                    if (msg.obj.equals("truetrue")) {
                        CommonUtils.showShortMsg(ZhuCheActivity.this, "注册成功");
                        Intent intent = new Intent(ZhuCheActivity.this, MainActivity.class);
                        intent.putExtra("item", m+"");
                        startActivity(intent);
                        finish();
                    } else {
                        CommonUtils.showShortMsg(ZhuCheActivity.this, "注册失败,用户名已经存在");
                    }
                    break;
            }
        }
    };

    public void btn_back(View view) {

        Intent intent = new Intent(this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);;
        startActivityForResult(intent, 1);
    }
}
