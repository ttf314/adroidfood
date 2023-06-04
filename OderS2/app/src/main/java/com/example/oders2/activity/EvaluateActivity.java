package com.example.oders2.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.oders2.Gongju.AccessToServer;
import com.example.oders2.Gongju.CommonUtils;
import com.example.oders2.Gongju.Constants;
import com.example.oders2.Gongju.Global;
import com.example.oders2.Gongju.Util;
import com.example.oders2.R;
import com.example.oders2.po.Evaluates;
import com.google.gson.Gson;

import static com.example.oders2.activity.MainActivity.m;

public class EvaluateActivity extends AppCompatActivity {
    private TextView textViewPrice,textViewDes,tv_mname;
    private int did;
    private String uid;
    private EditText editText;
    public Gson gson = new Gson();
    private String evalString;// 用户信息字符串
    private Handler mainHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate);

        initView();
        show();

        add();

    }

    private void initView() {
        tv_mname=findViewById(R.id.tv_mname);
        editText=findViewById(R.id.editText);
        mainHandler = new Handler(getMainLooper());
    }
    private void show() {
        Intent intent =getIntent();
        String[] fields = intent.getStringExtra("item").split("\\|");
        for (String field : fields) {
            String[] parts = field.split("_");
            String name = parts[0];
            int quantity = Integer.parseInt(parts[1]);
            System.out.println("name:"+name);
            System.out.println("quantity:"+quantity);
            did=quantity;
            tv_mname.setText(name);
            // 你可以将这些字段存入数据库中
            // 例如：调用一个保存到数据库的方法
            //saveToDatabase(name, quantity);
        }


    }
    private void add() {


    }

    public void btn_ok_click(View view) {
        uid=String.valueOf(m.get("id"));
        Evaluates evaluate=new Evaluates();
        evaluate.setUid(Integer.valueOf(uid));
        evaluate.setDid(did);
        evaluate.setVdedtel(editText.getText().toString());
        evaluate.setTimes(CommonUtils.getDateStrFromNow());
        evaluate.setMealname(tv_mname.getText().toString());
        evalString=gson.toJson(evaluate);
        if (Util.isNetworkConnected(this)) {
            new Thread() {
                public void run() {// 线程执行体
                    AccessToServer accessToServer = new AccessToServer();
                    String result = accessToServer.doPost(Global.URL
                                    + "EvaluateServlet", new String[] { "evalString"},
                            new String[] { evalString });
                    Message message2 = Message.obtain();
                    message2.obj = result;
                    message2.what = Constants.GET_CONTROL_LINE;
                    //mainHandler.sendMessage(message2);
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(EvaluateActivity.this, ShowDingDanActivity.class);
                            startActivity(intent);
                        }
                    });



                }
            }.start();
        }

    }
}
