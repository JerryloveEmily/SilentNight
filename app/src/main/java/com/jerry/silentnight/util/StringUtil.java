package com.jerry.silentnight.util;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2015/4/27.
 */
public class StringUtil {
    private StringUtil(){}

    /**
     * 判断字符串是否能转换为数字
     * @param str 字符串
     * @return  是否能够被转换成数字
     */
    public static boolean isLegalNumber(String str){
        try {
            Integer.parseInt(str);
        }catch (NumberFormatException e){
            return false;
        }
        return true;
    }

    /**
     * 字符串是否符合正则规则
     *
     * @param regular 正则规则
     * @param value   需要判断的字符串
     * @return
     */
    public static boolean isMatchByRegular(String regular, String value) {
        Pattern p = Pattern.compile(regular);
        Matcher matcher = p.matcher(value);
        return matcher.matches();
    }

    /**
     * 修改TextView 部分文字的颜色
     * @param textView
     */
    public static void setTextPartColor(@NonNull Context context, @NonNull TextView textView,
                                        @ColorRes int resId, int start, int end, boolean isBold){
        SpannableStringBuilder style=new SpannableStringBuilder(textView.getText().toString());
        style.setSpan(new ForegroundColorSpan(ResourceUtils.getResourceColor(context, resId))
                , start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //设置指定位置文字的颜色
        if (isBold){
            // 加粗
            style.setSpan(new StyleSpan(android.graphics.Typeface.BOLD)
                    , start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            // 字体大小, 为原来的1.3倍
            style.setSpan(new RelativeSizeSpan(1.3f)
                    , start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        }
        textView.setText(style);
    }

    /**
     * 设置文本输入框的hint文字大小
     * @param editText  被设置的输入框
     * @param textHint  hint文字
     * @param hintSize  hint文字大小
     */
    public static void setTextHintSize(@NonNull EditText editText, @NonNull String textHint, int hintSize){
        // 新建一个可以添加属性的文本对象
        SpannableString ss = new SpannableString(textHint);
        // 新建一个属性对象,设置文字的大小
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(hintSize, true);
        // 附加属性到文本
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 设置hint
        editText.setHint(new SpannedString(ss)); // 一定要进行转换,否则属性会消失
    }


}
