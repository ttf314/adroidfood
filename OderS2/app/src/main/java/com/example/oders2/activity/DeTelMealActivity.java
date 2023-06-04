package com.example.oders2.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
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

public class DeTelMealActivity extends AppCompatActivity {
    //private MealsDao mealsDao;//操作
    private List<Meals> meals;//数据集合
    //private Meals meals;
    private ImageView imageView;
    private Button bt_addcar;
    private TextView textViewPrice,textViewDes,tv_mname;
    private Handler mainHandler ,carHandler,evHandler;     // 主线程
    private String newurl,newur2;
    private Message message,message2;
    public Gson gson = new Gson();
    /*public String url="http://172.16.165.99:8080";//图片获取的地址*/
    public String name="";
    private int mid;
    private String uid,mname,murl,mdetel,mprice;
    private String carsString;// 购物车字符串
    private ListView listViewEv;
    private List<Evaluates> listevaluates;//数据集合
    private Evaluates evaluates;
    private EvaluateAdapter evaluateAdapter;
    private ScrollView scrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_de_tel_meal);

        initView();
        show();
        addcar();
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                listViewEv.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        listViewEv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // 停止父级滚动
                        if (scrollView != null) {
                            scrollView.requestDisallowInterceptTouchEvent(true);
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        // 允许父级滚动
                        if (scrollView != null) {
                            scrollView.requestDisallowInterceptTouchEvent(false);
                        }
                        break;
                }
                // 处理 ListView 的滚动
                v.onTouchEvent(event);
                return true;
            }
        });

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
                    CommonUtils.showShortMsg(DeTelMealActivity.this, "添加成功");
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
        scrollView=findViewById(R.id.scrollview);
        /*ScrollView scrollView = findViewById(R.id.scrollview);
        scrollView.scrollTo(0, 0);*/

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
                                + "MealsServlet3", new String[]{"name"},
                        new String[]{
                                name+""});
                AccessToServer accessToServer2 = new AccessToServer();
                String result2 = accessToServer.doGet(Global.URL
                                + "EvaluateServlet2fingbyname", new String[]{"name"},
                        new String[]{
                                name+""});

                message = Message.obtain();
                message.what = Constants.GET_CONTROL_LINE;// 结果消息标记
                message.obj = result;// 查询结果

                message2 = Message.obtain();
                message2.what = Constants.GET_CONTROL_LINE;// 结果消息标记
                message2.obj = result2;// 查询结果
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        showLvData(message);
                        showLvData2(message2);
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
                    Toast.makeText(this, "暂无评价，快快购买来评价哦！",
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
                }
                break;
            default:
                break;

        }
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
