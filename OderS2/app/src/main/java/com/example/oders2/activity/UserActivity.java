package com.example.oders2.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import com.example.oders2.Gongju.AddSubView;
import com.example.oders2.Gongju.CommonUtils;
import com.example.oders2.Gongju.LunarCalendarUtil;
import com.example.oders2.R;
import com.example.oders2.po.LunarCalendar;

public class UserActivity extends AppCompatActivity {
    private RadioGroup rgMain;

    private TextView tvAllOrder,tv_user_no_receive;
    private ImageButton ibUserIconAvator;
    private TextView tvUsername;
    private TextView tvUserPay;
    private TextView tvUserReceive;
    private TextView tvUserFinish;
    private TextView tvUserDrawback;
    private TextView tvUserLocation;
    private TextView tvUserCollect;
    private TextView tvUserCoupon;
    private TextView tvUserScore;
    private TextView tv_user_pass;
    private TextView tvUserTicket;
    private TextView tvUserInvitation;
    private TextView tvUserCallcenter;
    private TextView tvUserFeedback;
    private TextView tvUsercenter;
    private ImageButton ibUserSetting;
    private ImageButton ibUserMessage;
    private ScrollView scrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        initView();
        show();
        run();
    }


    private void initView() {
        rgMain = findViewById(R.id.rg_main);
        tvAllOrder = findViewById(R.id.tv_all_order);
        tvUsername = findViewById(R.id.tv_username);
        tvUserPay = findViewById(R.id.tv_user_pay);
        tvUserReceive = findViewById(R.id.tv_user_receive);
        tvUserDrawback = findViewById(R.id.tv_user_drawback);
        tvUserCollect = findViewById(R.id.tv_user_collect);
        tvUserCoupon = findViewById(R.id.tv_user_coupon);
        tvUserScore = findViewById(R.id.tv_user_score);
        tv_user_pass = findViewById(R.id.tv_user_pass);
        tvUserTicket = findViewById(R.id.tv_user_ticket);
        tvUserInvitation = findViewById(R.id.tv_user_invitation);
        tvUserCallcenter = findViewById(R.id.tv_user_callcenter);
        tvUserFeedback = findViewById(R.id.tv_user_feedback);
        scrollView = findViewById(R.id.scrollview);
        tv_user_no_receive= findViewById(R.id.tv_user_no_receive);
    }
    private void show() {
        //下方按钮点击事件
        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_home://主页
                        Intent intent = new Intent(UserActivity.this, UserMainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);;
                        startActivityForResult(intent, 1);
                        break;
                    case R.id.rb_type://分类
                        Intent intent2 = new Intent(UserActivity.this, TypeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);;
                        startActivityForResult(intent2, 1);
                        break;
                    case R.id.rb_community://发现
                        Intent intent3 = new Intent(UserActivity.this, FindActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);;
                        startActivityForResult(intent3, 1);
                        break;
                    case R.id.rb_cart://购物车
                        Intent intent4 = new Intent(UserActivity.this, CarActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);;
                        startActivityForResult(intent4, 1);
                        break;
                    case R.id.rb_user://用户中心
                        Intent intent5 = new Intent(UserActivity.this, UserActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);;
                        startActivityForResult(intent5, 1);
                        break;
                    default:
                        break;
                }

            }
        });
    }
    private void run() {
        //查看全部订单
        tvAllOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, ShowDingDanActivity.class);
                startActivity(intent);
            }
        });

        //待付款
        tvUserPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, UserPersonTextActivity.class);
                startActivity(intent);
            }
        });


        //未出单
        tv_user_no_receive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) { Intent intent = new Intent(UserActivity.this, UserPersonTextActivity.class);
                    startActivity(intent);
                    }
                });

        //已出单
        tvUserReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, ShowDingDan2Activity.class);
                startActivity(intent);
            }
        });

        //小六壬
        /*tvUserDrawback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

        tvUserCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取当前公历时间
                Calendar calendar = Calendar.getInstance();

                // 创建 LunarCalendarUtil 实例
                LunarCalendarUtil lunarCalendarUtil = new LunarCalendarUtil();

                // 获取农历日期和时辰
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1;
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                String lunarDate = lunarCalendarUtil.solarToLunarWithTime(year, month, day, hour);

                // 在这里执行小六壬的逻辑
                // 根据农历日期和时辰进行计算和判断
                // 示例：假设小六壬结果为result，根据result进行相应操作
                int result = calculateXiaoLiuRen(month, day, hour);
                switch (result) {
                    case 0:
                        CommonUtils.showDlgMsg(UserActivity.this,
                                "大吉大利，今晚吃鸡！");
                        // 大吉大利，今晚吃鸡！
                        // 在这里执行对应的操作
                        break;
                    case 1:
                        CommonUtils.showDlgMsg(UserActivity.this,
                                "好事成双，多点两份！");
                        // 好事成双，喜气洋洋！
                        // 在这里执行对应的操作
                        break;
                    case 2:
                        CommonUtils.showDlgMsg(UserActivity.this,
                                "三生有幸，吃遍山川！");
                        // 大吉大利，今晚吃鸡！
                        // 在这里执行对应的操作
                        break;
                    case 3:
                        CommonUtils.showDlgMsg(UserActivity.this,
                                "虽有波折，饭还可吃！");
                        // 好事成双，喜气洋洋！
                        // 在这里执行对应的操作
                        break;
                    case 4:
                        CommonUtils.showDlgMsg(UserActivity.this,
                                "劳心者治人，劳力者回家吧！");
                        // 大吉大利，今晚吃鸡！
                        // 在这里执行对应的操作
                        break;
                    case 5:
                        CommonUtils.showDlgMsg(UserActivity.this,
                                "诸事不宜，走为上策！");
                        // 好事成双，喜气洋洋！
                        // 在这里执行对应的操作
                        break;
                    // 其他结果的处理
                }

                // 在这里执行你想要的操作，比如将农历时间和小六壬结果显示在界面上
                // textView.setText(lunarDate + " 小六壬结果：" + result);
            }
        });


        //修改密码
        tv_user_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, UserPersonTextActivity.class);
                startActivity(intent);
            }
        });
    }

    // 小六壬计算逻辑示例
    private int calculateXiaoLiuRen(int month, int day, int hour) {
        // 在这里根据农历日期和时辰进行小六壬的计算和判断
        // 示例：假设计算逻辑为根据月份、日和时辰得出结果
        // 这里只是一个示例，请根据实际需求来实现计算逻辑
        int result = (month + day + hour) % 6;
        return result;
    }

}
