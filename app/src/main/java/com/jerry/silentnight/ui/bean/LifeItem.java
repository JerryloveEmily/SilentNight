package com.jerry.silentnight.ui.bean;

import android.support.annotation.DrawableRes;

/**
 * 项目名：SilentNight
 * 包名：  com.jerry.silentnight.ui.bean
 * 文件名：${}
 * 作者：  Administrator on 2016/11/21 15:04
 * 邮箱：JerryloveEmily@163.com
 */
public class LifeItem {
    private int iconResId;
    private String title;
    private Class<?> cls;

    public Class<?> getCls() {
        return cls;
    }

    public void setCls(Class<?> cls) {
        this.cls = cls;
    }

    public int getIconResId() {
        return iconResId;
    }

    public void setIconResId(@DrawableRes int iconResId) {
        this.iconResId = iconResId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
