package com.example.oders2.activity;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.oders2.Gongju.AccessToServer;
import com.example.oders2.Gongju.Constants;
import com.example.oders2.Gongju.Global;
import com.example.oders2.Gongju.OnDelBtnClickListener;
import com.example.oders2.Gongju.Util;
import com.example.oders2.R;
import com.example.oders2.adapter.ItemCategoryAdapter;
import com.example.oders2.adapter.LvDingDanInfoAdapter;
import com.example.oders2.po.ItemCategory;
import com.example.oders2.po.Meals;
import com.example.oders2.po.Order;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.oders2.activity.MainActivity.m;

public class ShowDingDanActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView btn_return, btn_add;   // 返回图片按钮 ，添加图片按钮
    private ListView lv_food;   // 食物列表组件
    private Handler mainHandler;   // 主线程
    private String uid;
    private int did;
    private Message message;
    private Handler handler;
    public Gson gson = new Gson();
    private List<Order> orders;//数据集合
    private LvDingDanInfoAdapter lvDingDanInfoAdapter;
    private ListView listOrders;   // 食物列表组件
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_ding_dan);

        //线程里面
        initView();
        show();
    }
    private void initView(){

        btn_return = findViewById(R.id.btn_return);
        btn_add = findViewById(R.id.btn_add);
        uid=String.valueOf(m.get("id"));
        mainHandler = new Handler(getMainLooper());
        handler = new Handler(getMainLooper());
        listOrders=findViewById(R.id.listOrders);
        btn_return.setOnClickListener(this);
        btn_add.setOnClickListener(this);






    }
    private void show() {
        if (Util.isNetworkConnected(this)) {// 判断网络是否有用
            new Thread() {
                public void run() {
                    AccessToServer accessToServer2 = new AccessToServer();
                    String result2 = accessToServer2.doGet(Global.URL
                                    + "OrderServlet2", new String[]{"uid"},
                            new String[]{
                                    uid});
                    System.out.println("result2=" + result2);
                    message = Message.obtain();
                    message.what = Constants.GET_CONTROL_LINE;// 省控线结果消息标记
                    message.obj = result2;// 省控线查询结果
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            showLvData(message);

                        }
                    });
                }
            }.start();
        }


        new Thread(new Runnable() {
            @Override
            public void run() {

                //回到主线程显示数据
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        }).start();
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
                orders = gson.fromJson(result,
                        new TypeToken<List<Order>>() {
                        }.getType());
                if (orders == null || orders.size() == 0) {
                    Toast.makeText(this, "数据维护中...请稍后再试！",
                            Toast.LENGTH_LONG).show();
                } else {
                    lvDingDanInfoAdapter = new LvDingDanInfoAdapter(this, orders);
                    listOrders.setAdapter(lvDingDanInfoAdapter);
                    lvDingDanInfoAdapter.setOnDelBtnClickListener(new OnDelBtnClickListener() {
                        @Override
                        public void onDelBtnClick(View view, int position) {

                            Order item = orders.get(position);//position数据
                            System.out.println(item.getDetel());
                            did=item.getId();
                            String[] fields = item.getDetel().split("\\|");
                            ArrayList<String> dataList = new ArrayList<>();
                            for (String field : fields) {
                                String[] parts = field.split("_");
                                String name = parts[0];
                                System.out.println("name:"+name);
                                dataList.add(name);
                            }
                            String[] data = new String[dataList.size()];
                            dataList.toArray(data);

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(ShowDingDanActivity.this, android.R.layout.simple_list_item_1, data);
                            ListView listView = new ListView(ShowDingDanActivity.this);
                            listView.setAdapter(adapter);
                            AlertDialog.Builder builder = new AlertDialog.Builder(ShowDingDanActivity.this);
                            builder.setTitle("选择一个进行评价吧！！！！");
                            builder.setView(listView);
                            final AlertDialog dialog = builder.create();
                            dialog.show();
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    String selectedItem = (String) parent.getItemAtPosition(position);
                                    Toast.makeText(getApplicationContext(), "您选择了 " + selectedItem, Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                    Intent intent = new Intent(ShowDingDanActivity.this, EvaluateActivity.class);
                                    intent.putExtra("item", selectedItem+"_"+did+"");
                                    startActivityForResult(intent, 1);

                                }
                            });

                        }
                    });
                }
                break;
            default:
                break;
        }
    }

    private void delDingdan(final int id) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        show();  // 重新加载数据
                    }
                });
            }
        }).start();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_return:
                Intent intent = new Intent(ShowDingDanActivity.this, UserActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                //finish();
                break;
            case R.id.btn_add:
                // 代码桩，打开添加食物界面

                break;
        }
    }
}
