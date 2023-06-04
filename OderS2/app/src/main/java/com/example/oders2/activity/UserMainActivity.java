package com.example.oders2.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.oders2.Gongju.AccessToServer;
import com.example.oders2.Gongju.CommonUtils;
import com.example.oders2.Gongju.Constants;
import com.example.oders2.Gongju.Global;
import com.example.oders2.Gongju.ImageUtil;
import com.example.oders2.Gongju.ImageUtil2;
import com.example.oders2.Gongju.OnAddBtnClickListener;
import com.example.oders2.Gongju.Util;
import com.example.oders2.R;
import com.example.oders2.adapter.ItemCategoryAdapter;
import com.example.oders2.adapter.MealsAdapter;
import com.example.oders2.adapter.UserMainAdapter;
import com.example.oders2.adapter.UserMainAdapter2;
import com.example.oders2.dao.ItemCategoryDao;
import com.example.oders2.dao.MealsDao;
import com.example.oders2.dao.UserDao;
import com.example.oders2.po.Cars;
import com.example.oders2.po.ItemCategory;
import com.example.oders2.po.Meals;
import com.example.oders2.po.UserInfo;
import com.example.oders2.test.ProductListAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.oders2.Gongju.ImageUtil.getImageAbsolutePath;
import static com.example.oders2.activity.MainActivity.m;

public class UserMainActivity extends AppCompatActivity {
    private RadioGroup rgMain;
    private Button button1,button2;
    private  int REQUEST_IMAGE,REQUEST_IMAGE2,REQUEST_CODE;
    private RecyclerView recyclerView;
    private EditText editText;
    private UserMainAdapter userMainAdapter;
    private UserMainAdapter2 userMainAdapter2;
    //private List<Meals> meals;//数据集合
    private List<Meals> meals = new ArrayList<>();;//数据集合
    private Meals meals2;
    private Message message,message2;
    private Handler handler,handler2;
    public Gson gson = new Gson();
    private ArrayList<String> imageUrls = new ArrayList<>();
    CarouselView carouselView;
    String url="http://172.20.10.6:8080";//图片获取的地址
    protected String []sample=new String[3];
    private String []sample2=new String[3];
    private String[] sampleImages2 = {"http://192.168.10.108:8080/test1_war_exploded/resource/ueditor/upload/50tbiao.png",
            "http://192.168.10.108:8080/test1_war_exploded/resource/ueditor/upload/50tbiao.png",
            "http://192.168.10.108:8080/test1_war_exploded/resource/ueditor/upload/50tbiao.png"};
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        ZXingLibrary.initDisplayOpinion(this);

        verifyStoragePermissions(this);

        show();
        initView();

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] items = {"扫一扫", "本地打开"};

                AlertDialog.Builder builder = new AlertDialog.Builder(UserMainActivity.this);
                builder.setTitle("选择扫码还是本地打开照片吧！！！");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch(item){
                            case 0:
                                Intent intent2 = new Intent(UserMainActivity.this, CaptureActivity.class);
                                startActivityForResult(intent2, REQUEST_IMAGE2);
                                break;
                            case 1:
                                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                intent.addCategory(Intent.CATEGORY_OPENABLE);
                                intent.setType("image/*");
                                startActivityForResult(intent, REQUEST_IMAGE);
                                break;
                            case 2:
                                // 选择了蓝色
                                break;
                        }
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();



            }
        });

        //下方按钮点击事件
        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_home://主页
                        Intent intent = new Intent(UserMainActivity.this, UserMainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);;
                        startActivityForResult(intent, 1);
                        break;
                    case R.id.rb_type://分类
                        Intent intent2 = new Intent(UserMainActivity.this, TypeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);;
                        startActivityForResult(intent2, 1);
                        break;
                    case R.id.rb_community://发现
                        Intent intent3 = new Intent(UserMainActivity.this, FindActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);;
                        startActivityForResult(intent3, 1);
                        break;
                    case R.id.rb_cart://购物车
                        Intent intent4 = new Intent(UserMainActivity.this, CarActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);;
                        startActivityForResult(intent4, 1);
                        break;
                    case R.id.rb_user://用户中心
                        Intent intent5 = new Intent(UserMainActivity.this, UserActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);;
                        startActivityForResult(intent5, 1);
                        break;
                    default:
                        break;
                }

            }
        });

        carouselView.setPageCount(3);
        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                if(sample[0]==null){
                    show();
                }
                //Picasso.get().load(sampleImages2[position]).into(imageView);
                System.out.println("0"+sample[0]);
                System.out.println("1"+sample[1]);
                System.out.println("2"+sample[2]);
                Picasso.get().load(sample[position]).into(imageView);
              }
        });
        carouselView.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(int position) {
                switch (position) {
                    case 0:
                        Intent intent = new Intent(UserMainActivity.this, DeTelMealActivity2.class);
                        intent.putExtra("item", "1");
                        startActivityForResult(intent, 1);
                        // 在这里实现跳转到第一个位置的逻辑
                        break;
                    case 1:
                        Intent intent2 = new Intent(UserMainActivity.this, DeTelMealActivity2.class);
                        intent2.putExtra("item", "2");
                        startActivityForResult(intent2, 1);
                        break;
                    case 2:
                        Intent intent3 = new Intent(UserMainActivity.this, DeTelMealActivity2.class);
                        intent3.putExtra("item", "3");
                        startActivityForResult(intent3, 1);
                        break;
                    default:
                        // 在这里实现跳转到其他位置的逻辑
                        break;
                } }
        });

    }

    private void initView() {
        rgMain = findViewById(R.id.rg_main);
        recyclerView=findViewById(R.id.id_recyclerviewmain);
        recyclerView.setAdapter(userMainAdapter);

        handler = new Handler(getMainLooper());
        handler2 = new Handler(getMainLooper());
        carouselView = findViewById(R.id.carouselView);
        editText=findViewById(R.id.editTextsousuo1);

        button1=findViewById(R.id.button1);

    }

    private void show() {
        if (Util.isNetworkConnected(this)) {// 判断网络是否有用
            new Thread() {
                public void run() {
                    AccessToServer accessToServer2 = new AccessToServer();
                    String result2 = accessToServer2.doGet(Global.URL
                            + "UserMainServlet", new String[]{"year"}, new String[]{""});
                    message2 = Message.obtain();
                    message2.what = Constants.GET_CONTROL_LINE;// 结果消息标记
                    message2.obj = result2;// 查询结果

                    AccessToServer accessToServer = new AccessToServer();
                    String result = accessToServer.doGet(Global.URL
                            + "MealsServlet", new String[]{"year"}, new String[]{""});
                    message = Message.obtain();
                    message.what = Constants.GET_CONTROL_LINE;// 结果消息标记
                    message.obj = result;// 查询结果


                    handler2.postAtFrontOfQueue(new Runnable() {
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
                meals = gson.fromJson(result,
                        new TypeToken<List<Meals>>() {
                        }.getType());
                if (meals == null || meals.size() == 0) {
                    Toast.makeText(this, "数据维护中...请稍后再试！",
                            Toast.LENGTH_LONG).show();
                } else {

                    userMainAdapter2 = new UserMainAdapter2(this, meals);
                    recyclerView.setAdapter(userMainAdapter2);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(linearLayoutManager);

                }

            default:
                break;

        }
    }
    // 显示类别列表数据的方法
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
                    Toast.makeText(this, "数据维护中...请稍后再试！",
                            Toast.LENGTH_LONG).show();
                } else {
                    //sample[0]=
                    //sample = {meals.get(0).getUrl1(),meals.get(1).getUrl1(),meals.get(2).getUrl1()};

                    System.out.println(url+meals.get(0).getUrl1().replace("\\","/"));
                    sample[0]=url+meals.get(0).getUrl1().replace("\\","/");
                    sample[1]=url+meals.get(1).getUrl1().replace("\\","/");
                    sample[2]=url+meals.get(2).getUrl1().replace("\\","/");
                    System.out.println("0"+sample[0]);
                    System.out.println("1"+sample[1]);
                    System.out.println("2"+sample[2]);
                    handler.postAtFrontOfQueue(new Runnable() {
                        @Override
                        public void run() {
                            carouselView.setPageCount(3);
                            carouselView.setImageListener(new ImageListener() {
                                @Override
                                public void setImageForPosition(int position, ImageView imageView) {
                                    //Picasso.get().load(sampleImages2[position]).into(imageView);
                                    System.out.println("0"+sample[0]);
                                    System.out.println("1"+sample[1]);
                                    System.out.println("2"+sample[2]);
                                    Picasso.get().load(sample[position]).into(imageView);
                                   }
                            });
                        }
                    });
                    //sample=sample2;
                }
        }

    }


    //二维码
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_IMAGE) {
            if (data != null) {
                Uri uri = data.getData();
                ContentResolver cr = getContentResolver();
                try {
                    Bitmap mBitmap = MediaStore.Images.Media.getBitmap(cr, uri);//显得到bitmap图片

                    CodeUtils.analyzeBitmap(ImageUtil.getImageAbsolutePath(this,uri), new CodeUtils.AnalyzeCallback() {
                        @Override
                        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {

                            Toast.makeText(UserMainActivity.this, result+"号店铺的信息", Toast.LENGTH_LONG).show();
                            //bundle.putSerializable("meals", (Serializable) item);
                            Intent intent = new Intent(UserMainActivity.this, TypeActivity.class);
                            intent.putExtra("name", result);
                            startActivityForResult(intent, 1);
                        }
                        @Override
                        public void onAnalyzeFailed() {
                            Toast.makeText(UserMainActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                        }
                    });

                    if (mBitmap != null) {
                        mBitmap.recycle();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }}else if(requestCode == REQUEST_IMAGE2){
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Toast.makeText(this, "解析结果:" + result, Toast.LENGTH_LONG).show();
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(UserMainActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public void btn_sousuo(View view) {
        Intent intent = new Intent(UserMainActivity.this, TypeActivity.class);
        intent.putExtra("btname", editText.getText().toString());
        startActivityForResult(intent, 1);

    }

    public static String getPathFromUri (Context context, Uri uri) {
        String path = null;
        if (uri != null) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                path = getImageAbsolutePath(context, uri);
            } else {
                Cursor cursor = null;
                try {
                    String[] proj = {MediaStore.Images.Media.DATA};
                    cursor = context.getContentResolver().query(uri, proj, null, null, null);
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    path = cursor.getString(column_index);
                } catch (Exception e) {
                } finally {
                    if (cursor != null)
                        cursor.close();
                }
            }

        }
        return path;
    }

    public void verifyStoragePermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}
