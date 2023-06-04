package com.example.oders2.Gongju;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ZHG on 2017/01/25.
 */

public class Util {
    public static boolean isNetworkConnected(Context context) {// 判断网络是否连接
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);// 获取网络连接管理器
            NetworkInfo mNetworkInfo;
            //mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
            Toast.makeText(context, "网络不可用，请检查你的网络状态！", Toast.LENGTH_SHORT)
                    .show();
            Intent intent = null;
            if (android.os.Build.VERSION.SDK_INT > 10) {// 判断手机系统的版本 即API大于10
                // 就是3.0或以上版本
                intent = new Intent(
                        android.provider.Settings.ACTION_WIRELESS_SETTINGS);
            } else {
                intent = new Intent();
                ComponentName component = new ComponentName(
                        "com.android.settings",
                        "com.android.settings.WirelessSettings");
                intent.setComponent(component);
                intent.setAction("android.intent.action.VIEW");
            }
            context.startActivity(intent);
        }
        return false;
    }

    public static String getDate() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(date);
    }

}
