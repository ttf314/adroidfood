package com.example.oders2.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.oders2.Gongju.AccessToServer;
import com.example.oders2.Gongju.CommonUtils;
import com.example.oders2.Gongju.Constants;
import com.example.oders2.Gongju.Global;
import com.example.oders2.Gongju.Util;
import com.example.oders2.R;
import com.example.oders2.adapter.EvaluateAdapter;
import com.example.oders2.po.Cars;
import com.example.oders2.po.Evaluates;
import com.example.oders2.po.Meals;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import static com.example.oders2.activity.MainActivity.m;

public class DeTelMealActivity2 extends AppCompatActivity {
    //private MealsDao mealsDao;//操作
    private List<Meals> meals;//数据集合
    //private Meals meals;
    private ImageView imageView;
    private Button bt_addcar;
    private TextView textViewPrice,textViewDes,tv_mname;
    private Handler mainHandler ,carHandler;     // 主线程
    private String newurl,newur2;
    private Message message,message2;
    public Gson gson = new Gson();
    public String name="";
    private int mid;
    private String uid,mname,murl,mdetel,mprice;
    private String carsString;// 购物车字符串
    private List<Evaluates> listevaluates;//数据集合
    private EvaluateAdapter evaluateAdapter;
    private ListView listViewEv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_de_tel_meal);

        initView();
        show();
        addcar();

    }

    private void addcar() {
        bt_addcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.valueOf(uid);
                Cars cars=new Cars();
                cars.setUsid(id);
                cars.setMname(mname);
                cars.setPrice(mprice);
                cars.setUrl(murl);
                cars.setDescription(mdetel);
                cars.setMid(mid);
                carsString = gson.toJson(cars);
                new Thread() {
                    public void run() {
                        //showLvData3(message5);
                        addcar2();
                    }
                }.start();
            }
        });

    }
    private void addcar2() {
        if (Util.isNetworkConnected(this)) {
            new Thread() {
                public void run() {// 线程执行体
                    AccessToServer accessToServer = new AccessToServer();
                    String result = accessToServer.doPost(Global.URL
                                    + "CarsServlet", new String[] { "cars"},
                            new String[] { carsString });
                    Message message = Message.obtain();
                    message.obj = result;
                    message.what = Constants.SUBMIT_QUESTION;
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
                    CommonUtils.showShortMsg(DeTelMealActivity2.this, "添加成功");
                    break;
            }
        }
    };

    private void initView() {
        uid=String.valueOf(m.get("id"));
        imageView = findViewById(R.id.tu);
        textViewPrice  = findViewById(R.id.tv_price);
        textViewDes=findViewById(R.id.tv_description);
        tv_mname=findViewById(R.id.tv_mname);
        bt_addcar=findViewById(R.id.bt_addcar);
        listViewEv=findViewById(R.id.lv_item);
        //mealsDao = new MealsDao();//实例化
        mainHandler = new Handler(getMainLooper());   // 获取主线程
        carHandler = new Handler(getMainLooper());


    }

    private void show() {
        Intent intent =getIntent();
        if (intent!=null){
            textViewDes.setText(intent.getStringExtra("item"));
            name=intent.getStringExtra("item");

        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                AccessToServer accessToServer = new AccessToServer();
                String result = accessToServer.doGet(Global.URL
                                + "MealsServlet5", new String[]{"name"},
                        new String[]{
                                name+""});
                message = Message.obtain();
                message.what = Constants.GET_CONTROL_LINE;// 省控线结果消息标记
                message.obj = result;// 查询结果
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        showLvData(message);
                    }
                });
            }
        }).start();

    }
    // 显示餐品列表数据的方法
    private void showLvData(Message msg) {
        String result = (String) msg.obj;
        switch (msg.what) {
            case Constants.GET_CONTROL_LINE:
                if ("exception".equals(result) || "error".equals(result)
                        || "".equals(result)) {
                    Toast.makeText(this, "访问服务器异常!",
                            Toast.LENGTH_SHORT);
                    return;
                }

                meals = gson.fromJson(result,new TypeToken<List<Meals>>() {
                }.getType());
                if (meals == null) {
                    Toast.makeText(this, "数据维护中...请稍后再试！",
                            Toast.LENGTH_LONG).show();
                } else {
                    textViewPrice.setText(meals.get(0).getPrice());
                    textViewDes.setText(meals.get(0).getDesccription());
                    tv_mname.setText(meals.get(0).getName());
                    newurl=meals.get(0).getUrl1().replace("\\","/");
                    Glide.with(this).load(Constants.url+newurl).into(imageView);
                    mname=meals.get(0).getName();
                    mid=meals.get(0).getId();
                    murl=meals.get(0).getUrl1();
                    mdetel=meals.get(0).getDesccription();
                    mprice=meals.get(0).getPrice();
                    show2();
                }
                break;
            default:
                break;
        }
    }

    private void show2() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                AccessToServer accessToServer2 = new AccessToServer();
                String result2 = accessToServer2.doGet(Global.URL
                                + "EvaluateServlet2fingbyname", new String[]{"name"},
                        new String[]{
                                mname+""});
                message2 = Message.obtain();
                message2.what = Constants.GET_CONTROL_LINE;// 结果消息标记
                message2.obj = result2;// 查询结果
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        showLvData2(message2);
                    }
                });
            }
        }).start();

    }

    // 显示餐品列表数据的方法
    private void showLvData2(Message msg) {
        String result = (String) msg.obj;
        switch (msg.what) {
            case Constants.GET_CONTROL_LINE:
                if ("exception".equals(result) || "error".equals(result)
                        || "".equals(result)) {
                    Toast.makeText(this, "访问服务器异常!",
                            Toast.LENGTH_SHORT);
                    return;
                }
                listevaluates = gson.fromJson(result,
                        new TypeToken<List<Evaluates>>() {
                        }.getType());
                if (listevaluates == null || listevaluates.size() == 0) {
                    Toast.makeText(this, "暂无评价，快快购买来评价哦！",
                            Toast.LENGTH_LONG).show();
                } else {
                    evaluateAdapter = new EvaluateAdapter(this, listevaluates);
                    listViewEv.setAdapter(evaluateAdapter);
                }
            default:
                break;
        }
    }
}
