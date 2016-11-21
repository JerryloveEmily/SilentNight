package com.jerry.silentnight.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

/**
 * 资源工具
 * Created by Administrator on 2015/10/10.
 */
public class ResourceUtils {
    private ResourceUtils(){
    }

    /**
     * 把Drawable转换成Bitmap
     * @param drawable 被转换的drawable
     * @return 转换后的bitmap
     */
    public static Bitmap drawableToBitmap(Context context, Drawable drawable){
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof NinePatchDrawable) {
            Bitmap bitmap = Bitmap
                    .createBitmap(
                            drawable.getIntrinsicWidth(),
                            drawable.getIntrinsicHeight(),
                            drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                    : Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            return bitmap;
        } else {
            return null;
        }
    }

    /**
     * 获取文本资源文件中的文本
     * @param context
     * @param resId
     * @return
     */
    public static String getResourceText(Context context, @StringRes int resId){
        String result = "";
        try {
            result = context.getResources().getString(resId);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取颜色资源文件中的颜色
     * @param obj
     * @param resId
     * @return
     */
    public static int getResourceColor(Object obj, @ColorRes int resId){
        if (obj instanceof Fragment){
            return ((Fragment)obj).getResources().getColor(resId);
        }else {
            return ((Context)obj).getResources().getColor(resId);
        }
    }

    public static void setBackgroundDrawable(View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }

    /**
     * 设置视图的上下左右的图片
     * @param context           ...
     * @param view              ...
     * @param leftDrawableId    左边图片
     * @param topDrawableId     上边图片
     * @param rightDrawableId   右边图片
     * @param bottomDrawableId  下边图片
     */
    public static void setResourceCompoundDrawables(
            @NonNull Context context,
            @NonNull TextView view,
            @DrawableRes int leftDrawableId,
            @DrawableRes int topDrawableId,
            @DrawableRes int rightDrawableId,
            @DrawableRes int bottomDrawableId){
        Drawable leftDrawable, topDrawable, rightDrawable, bottomDrawable;
        leftDrawable = getDrawable(context, leftDrawableId);
        topDrawable = getDrawable(context, topDrawableId);
        rightDrawable = getDrawable(context, rightDrawableId);
        bottomDrawable = getDrawable(context, bottomDrawableId);
        view.setCompoundDrawables(leftDrawable, topDrawable, rightDrawable, bottomDrawable);

    }

    @Nullable
    public static Drawable getDrawable(Context context, int drawableId){
        Drawable drawable;
        try{
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                drawable = context.getResources().getDrawable(drawableId);
            } else {
                drawable = context.getResources().getDrawable(drawableId, null);
            }
            if (null != drawable) {
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            }
        }catch (Resources.NotFoundException e){
            drawable = null;
        }
        return drawable;
    }

}
