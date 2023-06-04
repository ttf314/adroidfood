package com.example.oders2.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.os.Message;
import android.widget.Toast;

import com.example.oders2.Gongju.AccessToServer;
import com.example.oders2.Gongju.AddSubView;
import com.example.oders2.Gongju.CommonUtils;
import com.example.oders2.Gongju.Constants;
import com.example.oders2.Gongju.DBUtil;
import com.example.oders2.Gongju.Global;
import com.example.oders2.Gongju.OnAddBtnClickListener;
import com.example.oders2.Gongju.OnDelBtnClickListener;
import com.example.oders2.Gongju.Util;
import com.example.oders2.R;
import com.example.oders2.adapter.ItemCategoryAdapter;
import com.example.oders2.adapter.MealsAdapter;
import com.example.oders2.dao.ItemCategoryDao;
import com.example.oders2.dao.MealsDao;
import com.example.oders2.po.Cars;
import com.example.oders2.po.ItemCategory;
import com.example.oders2.po.Meals;
import com.example.oders2.po.Meals2;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.oders2.Gongju.Global.batchId;
import static com.example.oders2.Gongju.Global.categoryId;
import static com.example.oders2.activity.MainActivity.m;

public class TypeActivity extends AppCompatActivity {
    private ListView listViewMeals, listViewItem;   // 食物列表组件
    private static Map m2 = new HashMap();
    private MealsDao mealsDao;//操作
    private ItemCategoryDao itemCategoryDao;
    private List<Meals> meals;//数据集合
    private Meals meals2;
    private List<ItemCategory> itemCategories;//数据集合
    private List<Cars> listcars;//数据集合
    private Handler carHandler, mainHandler2;   // 主线程
    private MealsAdapter mealsAdapter;
    private ItemCategoryAdapter itemCategoryAdapter;
    private EditText editTextsousuo;
    private String itemid;
    private RadioGroup rgMain;
    private RadioButton radioButton;
    private Boolean mWorking = true;
    private TextView tp_tv_price,tv_userid;
    private Handler handler,handler5;
    private Button buttonsousuo1;
    public Gson gson = new Gson();
    private boolean isFinishedInit = false;//是否完成初始化
    private Message message,message2,message3,message4,message5;

    private String carsString,carsString2;// 购物车字符串
    private String mname,price,url,description,mid;// 字符串
    private int uid;
    private String name=null,result,btname=null;
    private Boolean isClicked = false; // 标记已经点击过
    //private Meals item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);
        //二维码跳
        Intent intent =getIntent();
        name=intent.getStringExtra("name");
        //搜索栏跳
        btname=intent.getStringExtra("btname");

        //线程里面
        initView();

        buttonsousuo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // 点击事件的代码
                        String name="";
                        name = editTextsousuo.getText().toString();
                        AccessToServer accessToServer4 = new AccessToServer();
                        String result4 = accessToServer4.doGet(Global.URL
                                        + "MealsServlet3", new String[]{"name"},
                                new String[]{
                                        name+""});
                        message4 = Message.obtain();
                        message4.what = Constants.GET_CONTROL_LINE;// 结果消息标记
                        message4.obj = result4;// 查询结果
                        mainHandler2.post(new Runnable() {
                            @Override
                            public void run() {
                                showLvData2(message4);
                            }
                        });
                    }
                }).start();

            }
        });
        show();

        tv_userid.setText((CharSequence) m.get("id"));
        //editTextsousuo.setText("重庆麻辣");

        //餐品列表点击事件
        listViewMeals.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Meals item = meals.get(arg2);
                //bundle.putSerializable("meals", (Serializable) item);
                Intent intent = new Intent(TypeActivity.this, DeTelMealActivity.class);
                intent.putExtra("item", item.getName().toString());
                startActivityForResult(intent, 1);

            }
        });

        //类别列表点击事件
        listViewItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                ItemCategory itemCategory = itemCategories.get(arg2);
                String itemname = itemCategory.getName();
                itemid = itemCategory.getId().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        AccessToServer accessToServer2 = new AccessToServer();
                        String result3 = accessToServer2.doGet(Global.URL
                                        + "MealsServlet2", new String[]{"itemid"},
                                new String[]{
                                        itemid+""});
                        message3 = Message.obtain();
                        message3.what = Constants.GET_CONTROL_LINE;// 结果消息标记
                        message3.obj = result3;// 查询结果
                    mainHandler2.post(new Runnable() {
                            @Override
                            public void run() {
                                showLvData2(message3);
                            }
                        });
                    }
                }).start();

            }
        });


        //下方按钮点击事件
        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home://主页
                        Intent intent = new Intent(TypeActivity.this, UserMainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        ;
                        startActivityForResult(intent, 1);
                        break;
                    case R.id.rb_type://分类
                        Intent intent2 = new Intent(TypeActivity.this, TypeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        ;
                        startActivityForResult(intent2, 1);
                        break;
                    case R.id.rb_community://发现
                        Intent intent3 = new Intent(TypeActivity.this, FindActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        ;
                        startActivityForResult(intent3, 1);
                        break;
                    case R.id.rb_cart://购物车
                        Intent intent4 = new Intent(TypeActivity.this, CarActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        ;
                        startActivityForResult(intent4, 1);
                        break;
                    case R.id.rb_user://用户中心
                        Intent intent5 = new Intent(TypeActivity.this, UserActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                        startActivityForResult(intent5, 1);
                        break;
                    default:
                        break;
                }

            }
        });


    }


    private void initView() {
        listViewMeals = findViewById(R.id.lv_food);
        listViewItem = findViewById(R.id.lv_item);
        editTextsousuo = findViewById(R.id.editTextsousuo);
        rgMain = findViewById(R.id.rg_main);
        radioButton = findViewById(R.id.rb_home);
        tp_tv_price = findViewById(R.id.tp_tv_price);
        tv_userid= findViewById(R.id.tv_userid);
        buttonsousuo1= findViewById(R.id.buttonsousuo1);
        mealsDao = new MealsDao();//实例化
        itemCategoryDao = new ItemCategoryDao();//实例化
        carHandler = new Handler(getMainLooper());
        mainHandler2 = new Handler(getMainLooper());
        handler = new Handler(getMainLooper());
        handler5 = new Handler(getMainLooper());
        listcars = new ArrayList<>();
    }

    private void show() {
        if (Util.isNetworkConnected(this)) {// 判断网络是否有用
            new Thread() {
                public void run() {
                    AccessToServer accessToServer = new AccessToServer();
                    if(name!=null){
                        result = accessToServer.doGet(Global.URL
                                + "ItemCategoryServlet", new String[]{"name"}, new String[]{name});
                    }else {
                        result = accessToServer.doGet(Global.URL
                                + "ItemCategoryServlet", new String[]{"name"}, new String[]{""});
                    }
                    AccessToServer accessToServer2 = new AccessToServer();
                    String result2 = accessToServer2.doGet(Global.URL
                            + "MealsServlet", new String[]{"itemid"},
                            new String[]{
                                    ""});
                    System.out.println("result=" + result);
                    System.out.println("result2=" + result2);
                    message = Message.obtain();
                    message.what = Constants.GET_CONTROL_LINE;// 省控线结果消息标记
                    message.obj = result;// 省控线查询结果
                    message2 = Message.obtain();
                    message2.what = Constants.GET_CONTROL_LINE;// 省控线结果消息标记
                    message2.obj = result2;// 省控线查询结果
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            showLvData(message);
                            showLvData2(message2);
                        }
                    });
                }
            }.start();
        }
    }

    // 显示类别列表数据的方法
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
                itemCategories = gson.fromJson(result,
                        new TypeToken<List<ItemCategory>>() {
                        }.getType());
                if (itemCategories == null || itemCategories.size() == 0) {
                    Toast.makeText(this, "数据维护中...请稍后再试！",
                            Toast.LENGTH_LONG).show();
                } else {
                    if (!isFinishedInit) {
                        isFinishedInit = true;
                    }// 是否完成初始化操作
                    itemCategoryAdapter = new ItemCategoryAdapter(this, itemCategories);
                    listViewItem.setAdapter(itemCategoryAdapter);
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
                meals = gson.fromJson(result,
                        new TypeToken<List<Meals>>() {
                        }.getType());
                if (meals == null || meals.size() == 0) {
                    Toast.makeText(this, "没这个菜呢！提醒店家出新品吧！",
                            Toast.LENGTH_LONG).show();
                    editTextsousuo.setText("");
                    show();
                } else {
                    if (!isFinishedInit) {
                        isFinishedInit = true;
                    }// 是否完成初始化操作
                    mealsAdapter = new MealsAdapter(this, meals, tp_tv_price);
                    listViewMeals.setAdapter(mealsAdapter);

                    mealsAdapter.setOnAddBtnClickListener(new OnAddBtnClickListener() {
                        @Override
                        public void onAddBtnClick(View view, int position) {
                            Meals item = meals.get(position);//position数据
                            Cars cars=new Cars();
                            Cars cars2=new Cars();
                            uid= Integer.valueOf(tv_userid.getText().toString());
                            mname=item.getName();
                            price=item.getPrice();
                            url=item.getUrl1();
                            description=item.getDesccription();
                            mid=item.getId()+"";
                            cars.setUsid(uid);
                            cars.setMname(mname);
                            cars.setPrice(price);
                            cars.setUrl(url);
                            cars.setDescription(description);
                            cars.setMid(item.getId());
                            carsString = gson.toJson(cars);
                            new Thread() {
                                public void run() {
                                    //showLvData3(message5);
                                    addcar();
                                }
                            }.start();
                        }
                    });
                }
            default:
                break;
        }
        if(btname!=null){
            editTextsousuo.setText(btname);
            buttonsousuo1.performClick();
            btname=null;
        }
    }
    private void addcar() {
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
                        CommonUtils.showShortMsg(TypeActivity.this, "成功");
            }
        }
    };
}
