package com.example.oders2.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oders2.Gongju.AccessToServer;
import com.example.oders2.Gongju.CommonUtils;
import com.example.oders2.Gongju.Constants;
import com.example.oders2.Gongju.GetSQLite;
import com.example.oders2.Gongju.Global;
import com.example.oders2.Gongju.Util;
import com.example.oders2.R;
import com.example.oders2.adapter.CarsAdapter;
import com.example.oders2.adapter.ItemCategoryAdapter;

import com.example.oders2.dao.OderDao2;
import com.example.oders2.po.Cars;
import com.example.oders2.po.ItemCategory;
import com.example.oders2.po.Meals;
import com.example.oders2.po.Meals2;
import com.example.oders2.test.Order2;
import com.example.oders2.test.ProductListAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.oders2.activity.MainActivity.m;

public class CarActivity extends AppCompatActivity {
    private RadioGroup rgMain;
    private TextView mTvCount,mTvrmb,id_tv_uid,wei;
    private Button mBtnPay,id_btn_del;
    private RecyclerView mRecyclerView;
    private CarsAdapter carsAdapter;
    private List<String> num = new ArrayList<String>();// 数量的集合
    private List<Cars> cars = new ArrayList<>();;//数据集合
    private Handler mainHandler,orderHandler,delHandler;   // 主线程
    private float mTotalPrice;
    private int count;
    private OderDao2 oderDao = new OderDao2();
    private Order2 morder2 = new Order2();
    private Message message;
    private Handler handler;
    public Gson gson = new Gson();
    public String name,rmb;

    Order2 order2 = new Order2();
    private String orderString;

    private String uid;
    private Spinner sp_wei;
    private int weiid2,gmnum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);

        initView();
        show();
    }
    private void initView() {
        rgMain = findViewById(R.id.rg_main);
        mRecyclerView = findViewById(R.id.id_recyclerviewcar);
        mTvCount = findViewById(R.id.id_tv_count);
        mTvrmb= findViewById(R.id.id_tv_rmb);
        id_tv_uid= findViewById(R.id.id_tv_uid);
        mBtnPay = findViewById(R.id.id_btn_pay);
        id_btn_del= findViewById(R.id.id_btn_del);
        mainHandler = new Handler(getMainLooper());
        orderHandler= new Handler(getMainLooper());
        handler = new Handler(getMainLooper());

        name=String.valueOf(m.get("id"));
        uid=String.valueOf(m.get("id"));
        rmb=String.valueOf(m.get("rmb"));

        mTvrmb.setText(rmb);

        GetSQLite getSQLite=new GetSQLite();
        num=getSQLite.setnum2();//获取数量集合
        sp_wei=findViewById(R.id.sp_wei);
        ArrayAdapter<String> numAdapter = new ArrayAdapter<>(
                this, R.layout.spinner_item_year, num);

        sp_wei.setAdapter(numAdapter);

        wei=findViewById(R.id.wei);
        MyItemSelectedListener itemSelectedListener = new MyItemSelectedListener();
        sp_wei.setOnItemSelectedListener(itemSelectedListener);

        if (Util.isNetworkConnected(this)) {// 判断网络是否有用
            new Thread() {
                public void run() {
                    AccessToServer accessToServer = new AccessToServer();
                    String result = accessToServer.doGet(Global.URL
                            + "CarsServlet3", new String[]{"name"}, new String[]{
                            name+ ""});
                    System.out.println("result=" + result);
                    message = Message.obtain();
                    message.what = Constants.GET_CONTROL_LINE;// 结果消息标记
                    message.obj = result;// 查询结果
                    System.out.println("11111111111");
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("2222222");
                            showLvData(message);

                        }
                    });
                }
            }.start();
        }

        mBtnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count <=0){
                    System.out.println("没有点击");
                    return;
                }
                morder2.setCount(count);
                morder2.setPrice(mTotalPrice);

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                addOder(morder2);
                                System.out.println("成功");
                                Intent intent4 = new Intent(CarActivity.this, CarActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);;
                                startActivityForResult(intent4, 1);
                                //setResult(1);   // 使用参数表示当前界面操作成功，并返回管理界面
                                //finish();
                            }
                        });
                    }
                }).start();

            }
        });

        id_btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delCar();
                Intent intent4 = new Intent(CarActivity.this, CarActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);;
                startActivityForResult(intent4, 1);
            }
        });


    }

    private class MyItemSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int position, long id) {
            switch (parent.getId()) {
                case R.id.sp_wei:// 如果是选择省份列表
                    weiid2 = position;
                    break;
                default:
                    break;
            }
            wei.setText(Html.fromHtml("<font color=red><b>"
                    + weiid2
            ));
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }
    private void delCar() {
        if (Util.isNetworkConnected(this)) {
            new Thread() {
                public void run() {// 线程执行体
                    AccessToServer accessToServer = new AccessToServer();
                    String result = accessToServer.doPost(Global.URL
                                    + "CarsServlet4", new String[] { "uid"},
                            new String[] { Integer.valueOf(uid)+"" });
                    Message message = Message.obtain();
                    message.obj = result;
                    message.what = Constants.SUBMIT_QUESTION;
                    //delHandler.sendMessage(message);
                }
            }.start();
        }

    }


    private void show() {
        //下方按钮点击事件
        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_home://主页
                        Intent intent = new Intent(CarActivity.this, UserMainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);;
                        startActivityForResult(intent, 1);
                        break;
                    case R.id.rb_type://分类
                        Intent intent2 = new Intent(CarActivity.this, TypeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);;
                        startActivityForResult(intent2, 1);
                        break;
                    case R.id.rb_community://发现
                        Intent intent3 = new Intent(CarActivity.this, FindActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);;
                        startActivityForResult(intent3, 1);
                        break;
                    case R.id.rb_cart://购物车
                        Intent intent4 = new Intent(CarActivity.this, CarActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);;
                        startActivityForResult(intent4, 1);
                        break;
                    case R.id.rb_user://用户中心
                        Intent intent5 = new Intent(CarActivity.this, UserActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);;
                        startActivityForResult(intent5, 1);
                        break;
                    default:
                        break;
                }

            }
        });
    }

    // 显示列表数据的方法
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
                cars = gson.fromJson(result,
                        new TypeToken<List<Cars>>() {
                        }.getType());
                if (cars == null || cars.size() == 0) {
                    Toast.makeText(this, "数据维护中...请稍后再试！",
                            Toast.LENGTH_LONG).show();
                } else {
                    System.out.println("333333");
                    carsAdapter = new CarsAdapter(this,cars);
                    mRecyclerView.setAdapter(carsAdapter);
                    //RecyclerView的出现不光可以代替ListView，也可以代替GridView，所以啊大胸弟，你在用的时候要告诉RecyclerView你要代替的是哪个啊，就是所谓的初始化配置，不配置就会警告报错、不显示数据
                    //分割线可以不设置，动画也可以不设置，但是LayoutManager必须设置。
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                    mRecyclerView.setLayoutManager(linearLayoutManager);
                    //listViewItem.setAdapter(itemCategoryAdapter);
                    carsAdapter.setOnProductListener(new CarsAdapter.OnProductListener() {
                        @Override
                        public void onProductAdd(Cars productItem) {
                            count++;
                            System.out.println("数量"+count);
                            mTvCount.setText("数量:"+count);
                            mTotalPrice+=Float.parseFloat(productItem.getPrice());
                            mBtnPay.setText(mTotalPrice + "元 立即支付");
                            morder2.addProduct(productItem);
                        }

                        @Override
                        public void onProductSub(Cars productItem) {
                            count--;
                            mTvCount.setText("数量"+count);
                            mTotalPrice-=Float.parseFloat(productItem.getPrice());
                            mBtnPay.setText(mTotalPrice + "元 立即支付");
                            morder2.removeProduct(productItem);
                            //System.out.println(productItem);
                        }
                    });
                }
            default:
                break;
        }
    }

    public int addOder(Order2 order){

        int iRow = 0;
        Map<Cars, Integer> productsMap = order.productsMap;
        StringBuilder sb = new StringBuilder();
        Meals meals = new Meals();
        List<Meals> meals1 = new ArrayList<>();;//数据集合
        for (Cars p : productsMap.keySet()) {
            sb.append(p.getMname() + "_" +productsMap.get(p)+ "_");
            sb.append("|");

        }
        sb = sb.deleteCharAt(sb.length() - 1);
        System.out.println(sb.toString());
        try{
            //order.setProducts(sb.toString());
            System.out.println(m.get("rmb")+"人民币");
            float a = Float.parseFloat(m.get("rmb").toString());
            float b = (Float)order.getPrice();
            if(a<b){
                System.out.println("钱不够小了");
                CommonUtils.showLonMsg(CarActivity.this,
                        "钱不够了哦！！！");
            }else {
                float c=a-b;
                m.put("rmb",c);
                System.out.println("大");
                CommonUtils.showLonMsg(CarActivity.this,
                        "购买成功");
                System.out.println("--------------"+m.get("rmb")+"人民币2");
                //order2.setUid(1);
                int id = Integer.valueOf(uid);
                order2.setUid(id);
                order2.setNowrmb(c);
                order2.setPrice(order.getPrice());
                order2.setCount(order.getCount());
                order2.setDate(CommonUtils.getDateStrFromNow());
                order2.setDetel(sb.toString());
                order2.setWeizhi(wei.getText()+"");
                order2.setIsDelete(0);

                orderString = gson.toJson(order2);
                System.out.println("11111111111111");

                new Thread() {
                    public void run() {
                        //showLvData3(message5);
                        if (Util.isNetworkConnected(CarActivity.this)) {
                            new Thread() {
                                public void run() {// 线程执行体
                                    System.out.println("2222222222");
                                    AccessToServer accessToServer4 = new AccessToServer();
                                    String result = accessToServer4.doPost(Global.URL
                                                    + "OrderServlet", new String[]{"orderString"},
                                            new String[]{orderString+""});
                                    Message message = Message.obtain();
                                    message.obj = result;
                                    message.what = Constants.SUBMIT_QUESTION;
                                    orderHandler.sendMessage(message);
                                }
                            }.start();
                        }

                        System.out.println("3333333333333");
                    }
                }.start();
            }
        }catch(Exception ex){
            System.out.println("44444444444");
            ex.printStackTrace();
        }finally {
            System.out.println("5555555555");
            oderDao.closeAll();
        }
        System.out.println("66666666666");
        return iRow;
    }
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.SUBMIT_QUESTION:
                    CommonUtils.showShortMsg(CarActivity.this, "成功");
            }
        }
    };
}
