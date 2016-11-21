package com.jerry.silentnight.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;


/**
 * 项目名：NatureAssistance
 * 包名：  com.boding.natureassistance.util
 * 文件名：${}
 * 作者：  Administrator on 2016/10/25 14:01
 * 邮箱：JerryloveEmily@163.com
 * 描述：视图工具
 */
public class ViewUtil {
    private ViewUtil() {
    }

    /**
     * 输入框获取焦点并弹出输入法软键盘
     *
     * @param editText ...
     */
    public static void showOrHideKeyboard(EditText editText, boolean isShow) {
        if (editText != null) {
            InputMethodManager inputManager = (InputMethodManager) editText
                    .getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (isShow){
                //设置可获得焦点
                editText.setFocusable(true);
                editText.setFocusableInTouchMode(true);
                //请求获得焦点
                editText.requestFocus();
                //调用系统输入法
                inputManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
            }else {
                inputManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
            }
        }
    }

    public static void goToPhoneDial(Context context, String phone){
        //跳转到拨号界面，同时传递电话号码
        Intent dialIntent =  new Intent(Intent.ACTION_DIAL,
                Uri.parse("tel:" + phone));
        context.startActivity(dialIntent);
    }
}
