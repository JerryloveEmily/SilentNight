package com.jerry.silentnight.util;

import android.content.Context;
import android.widget.Toast;

/**
 * 项目名：NatureAssistance
 * 包名：  com.boding.natureassistance.util
 * 文件名：${}
 * 作者：  Administrator on 2016/11/1 19:23
 * 邮箱：JerryloveEmily@163.com
 */
public class ToastUtil {
    private ToastUtil(){}

    public static void showShortMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
