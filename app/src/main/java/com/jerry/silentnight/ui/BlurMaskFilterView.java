package com.jerry.silentnight.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.jerry.silentnight.R;
import com.jerry.silentnight.util.ScreenUtil;

/**
 * 项目名：SilentNight
 * 包名：  com.jerry.silentnight.ui
 * 文件名：${}
 * 作者：  Administrator on 2016/12/7 16:21
 * 邮箱：JerryloveEmily@163.com
 */
public class BlurMaskFilterView extends View {
    private Paint shadowPaint;// 画笔
    private Context mContext;// 上下文环境引用
    private Bitmap srcBitmap, shadowBitmap;// 位图和阴影位图

    private int x, y;// 位图绘制时左上角的起点坐标

    public BlurMaskFilterView(Context context) {
        this(context, null);
    }

    public BlurMaskFilterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        // 记得设置模式为SOFTWARE
        setLayerType(LAYER_TYPE_SOFTWARE, null);

        // 初始化画笔
        initPaint();

        // 初始化资源
        initRes(context);
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        // 实例化画笔
        shadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        shadowPaint.setColor(Color.DKGRAY);
        shadowPaint.setMaskFilter(
                new BlurMaskFilter(100, BlurMaskFilter.Blur.SOLID));
    }

    /**
     * 初始化资源
     */
    private void initRes(Context context) {
        // 获取位图
        srcBitmap = BitmapFactory.decodeResource(
                context.getResources(),
                R.drawable.blur_bg);

        // 获取位图的Alpha通道图
        srcBitmap.getPixel(1, 2);
        shadowBitmap = srcBitmap.extractAlpha();

        /*
         * 计算位图绘制时左上角的坐标使其位于屏幕中心
         */
        x = ScreenUtil.getScreenWidth(context) / 2 - srcBitmap.getWidth() / 2;
        y = ScreenUtil.getScreenHeight(context) / 2 - srcBitmap.getHeight() / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 再绘制位图
        canvas.drawBitmap(srcBitmap, x, y, shadowPaint);
        // 先绘制阴影
//        canvas.drawBitmap(shadowBitmap, x, y, shadowPaint);
    }
}
