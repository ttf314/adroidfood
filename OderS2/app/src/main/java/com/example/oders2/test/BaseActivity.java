package com.example.oders2.test;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.oders2.R;


/**
 * Created by zhanghongyang01 on 16/10/18.
 */

public class BaseActivity extends AppCompatActivity {

    private ProgressDialog mLoadingDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(0xFF000000);
        }
        mLoadingDialog = new ProgressDialog(this);
        mLoadingDialog.setMessage("加载中...");

    }

    protected void startLoadingProgress() {
        mLoadingDialog.show();
    }

    protected void stopLoadingProgress() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopLoadingProgress();
        mLoadingDialog = null;
    }

    protected void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.id_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}