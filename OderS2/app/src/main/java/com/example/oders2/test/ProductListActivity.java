package com.example.oders2.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.oders2.R;
import com.example.oders2.adapter.ItemCategoryAdapter;
import com.example.oders2.adapter.MealsAdapter;
import com.example.oders2.dao.MealsDao;
import com.example.oders2.dao.OderDao2;
import com.example.oders2.po.Meals;
import com.example.oders2.po.Meals2;

import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {
   /* private TextView mTvCount;
    private MealsDao mealsDao;//操作
    private Button mBtnPay;
    private RecyclerView mRecyclerView;

    private ProductListAdapter mProductListAdapter;

    private List<Meals2> mDatas = new ArrayList<>();
    private List<Meals> meals = new ArrayList<>();//数据集合
    private Handler mainHandler,mainHandler2;   // 主线程
    private float mTotalPrice;
    private int count;
    private OderDao2 oderDao = new OderDao2();
    private Order2 morder2 = new Order2();

    public static void launch(Context mContext, Meals meals) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);


        initViews();
        initEvent();
        loadData();
    }

    private void loadData() {

    }

    private void initViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
        mTvCount = (TextView) findViewById(R.id.id_tv_count);
        mBtnPay = (Button) findViewById(R.id.id_btn_pay);

        mealsDao = new MealsDao();//实例化
        mainHandler = new Handler(getMainLooper());

        //mProductListAdapter=new ProductListAdapter(this, meals);

        new Thread(new Runnable() {
            @Override
            public void run() {
                meals = mealsDao.getAllMealsList();   // 获取所有的食物数据
                System.out.println("meals="+meals);
                //mDatas = mealsDao.getAllMealsList();
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        showLvData();
                    }
                });
            }
        }).start();

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
                        final int iRow = oderDao.addOder(morder2);
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {

                                System.out.println("成功");
                                //setResult(1);   // 使用参数表示当前界面操作成功，并返回管理界面
                                //finish();
                            }
                        });
                    }
                }).start();


            }
        });


    }
    private void initEvent() {

    }
    // 显示列表数据的方法
    private void showLvData() {
        mProductListAdapter = new ProductListAdapter(this,meals);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mProductListAdapter);
        mTvCount.setText("数量:0");
        mProductListAdapter.setOnProductListener(new ProductListAdapter.OnProductListener() {
            @Override
            public void onProductAdd(Meals productItem) {
                count++;
                System.out.println("数量"+count);
                mTvCount.setText("数量:"+count);
                mTotalPrice+=Float.parseFloat(productItem.getPrice());
                mBtnPay.setText(mTotalPrice + "元 立即支付");

                morder2.addProduct(productItem);
            }

            @Override
            public void onProductSub(Meals productItem) {
                count--;
                mTvCount.setText("数量"+count);
                mTotalPrice-=Float.parseFloat(productItem.getPrice());
                mBtnPay.setText(mTotalPrice + "元 立即支付");
                morder2.removeProduct(productItem);
                //System.out.println(productItem);
            }
        });


        }*/
}
