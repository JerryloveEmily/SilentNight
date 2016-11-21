package com.jerry.silentnight.util;

import android.support.annotation.NonNull;
import android.text.TextUtils;

/**
 * 项目名：iShopping 包名：  com.jerry.corelibrary.utils 文件名：${} 作者：  Administrator on 2016/10/14 11:31
 * 邮箱：JerryloveEmily@163.com
 */
public final class Preconditions {
    private Preconditions(){}

    public static <T> T checkNotNull(T reference){
        if (reference == null){
            throw new NullPointerException("reference cannot null");
        }
        return reference;
    }

    public static <T> T checkNotNull(T reference, @NonNull Object errorMessage){
        if (reference == null){
            throw new NullPointerException(String.valueOf(errorMessage));
        }
        if (((reference instanceof String))
                && (TextUtils.isEmpty((CharSequence) reference) || "null".equals(reference))){
            throw new NullPointerException("reference 不能为空字符串，或者为\"null\"字符串");
        }
        return reference;
    }
}
