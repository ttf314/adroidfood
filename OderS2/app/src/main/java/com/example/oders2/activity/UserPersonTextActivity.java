package com.example.oders2.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup;


import com.example.oders2.Gongju.AccessToServer;
import com.example.oders2.Gongju.CommonUtils;
import com.example.oders2.Gongju.Constants;
import com.example.oders2.Gongju.Global;
import com.example.oders2.Gongju.Util;
import com.example.oders2.R;
import com.example.oders2.po.UserInfo;
import com.google.gson.Gson;

import org.apache.http.client.methods.HttpGet;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import static com.example.oders2.activity.MainActivity.m;

public class UserPersonTextActivity extends AppCompatActivity {
    private List<UserInfo> userInfoList;//数据集合
    private TextView tv_id;
    private RadioGroup genderRadioGroup;
    private RadioButton maleRadioButton;
    private RadioButton femaleRadioButton;
    private EditText phone,userName,address,email,realname;
    private String sex="女";
    private Message message;
    private Handler handler;
    public Gson gson = new Gson();
    UserInfo userInfo = new UserInfo();
    private String userInfoString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_person_text);

        initView();
        genderRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.male_radio_button:
                        sex="男";
                        break;
                    case R.id.female_radio_button:
                        sex="女";
                        break;
                    default:
                        // Handle no selection
                        break;
                }
            }
        });

        run();
    }



    private void initView() {
        genderRadioGroup = findViewById(R.id.gender_radio_group);
        userName =  findViewById(R.id.userName);
        phone =  findViewById(R.id.phone);
        address =  findViewById(R.id.address);
        email =  findViewById(R.id.email);
        realname =  findViewById(R.id.realname);
        tv_id =  findViewById(R.id.textView);
        genderRadioGroup = findViewById(R.id.gender_radio_group);
        maleRadioButton = findViewById(R.id.male_radio_button);
        femaleRadioButton = findViewById(R.id.female_radio_button);
    }
    private void run() {
        tv_id.setText((CharSequence) m.get("id"));
        userName.setText((CharSequence) m.get("name"));
        phone.setText((CharSequence) m.get("phone"));
        address.setText((CharSequence) m.get("address"));
        email.setText((CharSequence) m.get("email"));
        realname.setText((CharSequence) m.get("realname"));
        if (m.get("sex").equals("男")) {
            maleRadioButton.setChecked(true);
        } else if (sex.equals("女")) {
            femaleRadioButton.setChecked(true);
        }

    }

    public void btn_ok_click(View view) {
        userInfo.setUserName(userName.getText().toString());
        userInfo.setPhone(phone.getText().toString());
        userInfo.setAddress(address.getText().toString());
        userInfo.setEmail(email.getText().toString());
        userInfo.setRealname(realname.getText().toString());
        userInfo.setSex(sex);
        userInfo.setId(Integer.valueOf(tv_id.getText().toString()));
        m.put("name", userName.getText().toString());
        m.put("phone", phone.getText().toString());
        m.put("sex", sex);
        m.put("address", address.getText().toString());
        m.put("email", email.getText().toString());
        m.put("realname", realname.getText().toString());
        userInfoString= gson.toJson(userInfo);
        System.out.println(userInfoString);
        if (Util.isNetworkConnected(this)) {// 判断网络是否有用
            new Thread() {
                public void run() {
                    String encodedUserInfoString = null;
                    try {
                        encodedUserInfoString = URLEncoder.encode(userInfoString, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    AccessToServer accessToServer2 = new AccessToServer();
                    String result = accessToServer2.doGet(Global.URL
                                    + "UserServlet4", new String[]{"userInfoString"},
                            new String[]{
                                    encodedUserInfoString+""});
                    message = Message.obtain();
                    message.what = Constants.SUBMIT_QUESTION;// 省控线结果消息标记
                    message.obj = result;// 省控线查询结果
                    mHandler.sendMessage(message);
                }
            }.start();
        }
    }
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.SUBMIT_QUESTION:
                    CommonUtils.showShortMsg(UserPersonTextActivity.this, "成功");

            }
        }
    };

    public void btn_back(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
