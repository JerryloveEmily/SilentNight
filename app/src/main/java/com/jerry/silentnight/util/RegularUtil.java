package com.jerry.silentnight.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则判断工具
 * Created by JerryloveEmily on 16/9/29.
 */

public class RegularUtil {

    private RegularUtil() {
    }

    /**
     * 验证手机号码
     *
     * @param phoneNumber 手机号码
     * @return boolean
     */
    public static boolean checkPhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile("^(0|86|17951|086)?(((13[0-9])|(14[5,7])|(15[0,1,2,3,5,6,7,8,9])|(17[0,1,3,6,7,8])|(18[0-9]))\\d{8})$");
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }
}
