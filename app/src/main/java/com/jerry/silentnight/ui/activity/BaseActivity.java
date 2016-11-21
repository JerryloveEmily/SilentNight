package com.jerry.silentnight.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.jerry.silentnight.R;
import com.jerry.silentnight.util.StatusBarUtil;

/**
 * 项目名：SilentNight
 * 包名：  com.jerry.silentnight.ui.activity
 * 文件名：${}
 * 作者：  Administrator on 2016/11/21 15:53
 * 邮箱：JerryloveEmily@163.com
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setColor(this,
                Color.WHITE, 0);
    }

    protected void initView(){

    }

    protected void initEvent(){

    }
}
