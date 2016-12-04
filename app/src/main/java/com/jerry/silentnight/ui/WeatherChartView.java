package com.jerry.silentnight.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.jerry.silentnight.R;

/**
 * Created by Administrator on 2016/12/3 0003.
 */

public class WeatherChartView extends HorizontalScrollView {


    private LinearLayout mChildContainer;

    public WeatherChartView(Context context) {
        this(context, null);
    }

    public WeatherChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeatherChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setWillNotDraw(false);

        mChildContainer = new LinearLayout(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        mChildContainer.setLayoutParams(params);
        mChildContainer.setWillNotDraw(false);
        mChildContainer.setOrientation(LinearLayout.VERTICAL);
        ChartView chartView = new ChartView(context);
//        chartView.setBackgroundColor(Color.parseColor("#ff0000"));
        LinearLayout.LayoutParams llParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        chartView.setLayoutParams(llParams);
//        mChildContainer.addView(chartView);

        addView(chartView);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 天气图表视图
     */
    private class ChartView extends View {

        private Paint mWeekPaint, mDatePaint, mWeatherPaint,
                mMaxTemperaturePaint, mMinTemperaturePaint,
                mTemperatureChartPaint;

        private int mWeekColor = 0xffA6A6A6,
                mDateColor = 0xff999999,
                mMaxTempertureColor = 0xffdddddd,
                mMinTempertrureColor = 0xffA6A6A6;

        // 需要显示天气的日期个数
        private int mItemNums = 11;
        // 每个天气日期item的宽度的像素值
        private int mItemWidth = 200;

        // 绘制星期的文字的基线位置baseline
        private int mBaselineY = 60;

        private String[] mWeekNames = {
                "昨天", "今天", "周日",
                "周一", "周二", "周三",
                "周四", "周五", "周六",
                "周日", "周一"
        };
        private String[] mDateNames = {
                "12.03", "12.04", "12.05",
                "12.06", "12.07", "12.08",
                "12.09", "12.10", "12.11",
                "12.12", "12.13"
        };
        private String[] mMaxTempertrureNames = {
                "17°", "16°", "17°",
                "9°", "13°", "14°",
                "12°", "10°", "12°",
                "15°", "14°"
        };
        private String[] mMinTempertrureNames = {
                "8°", "8°", "8°",
                "5°", "7°", "8°",
                "7°", "6°", "7°",
                "9°", "6°"
        };

        private Drawable mDrawable;
        private Bitmap mBitmap;

        public ChartView(Context context) {
            super(context);
            initCompont(context);
        }

        public ChartView(Context context, AttributeSet attrs) {
            super(context, attrs);
            initCompont(context);
        }

        public ChartView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            initCompont(context);
        }

        private void initCompont(Context context) {
            initPaint();
            initDrawable(context);
        }

        private void initPaint() {
            mWeekPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mWeekPaint.setColor(mWeekColor);
            mWeekPaint.setTextSize(45f);
            mWeekPaint.setStyle(Paint.Style.FILL);

            mDatePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mDatePaint.setColor(mDateColor);
            mDatePaint.setTextSize(32f);
            mDatePaint.setStyle(Paint.Style.FILL);

            mWeatherPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mWeatherPaint.setColor(mDateColor);
            mWeatherPaint.setTextSize(35f);
            mWeatherPaint.setStyle(Paint.Style.FILL);

            mMaxTemperaturePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mMaxTemperaturePaint.setColor(mMaxTempertureColor);
            mMaxTemperaturePaint.setTextSize(38f);
            mMaxTemperaturePaint.setStyle(Paint.Style.FILL);
            mMinTemperaturePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mMinTemperaturePaint.setColor(mMinTempertrureColor);
            mMinTemperaturePaint.setTextSize(35f);
            mMinTemperaturePaint.setStyle(Paint.Style.FILL);
        }

        private void initDrawable(Context context){
            mBitmap = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.classic_ico_partly_cloudy);
            mBitmap = scaleBitmap(mBitmap, 0.6f);
        }

        /**
         * 按比例缩放图片
         *
         * @param origin 原图
         * @param ratio  比例
         * @return 新的bitmap
         */
        private Bitmap scaleBitmap(Bitmap origin, float ratio) {
            if (origin == null) {
                return null;
            }
            int width = origin.getWidth();
            int height = origin.getHeight();
            Matrix matrix = new Matrix();
            matrix.preScale(ratio, ratio);
            Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
            if (newBM.equals(origin)) {
                return newBM;
            }
            origin.recycle();
            return newBM;
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            int width = mItemNums * mItemWidth;
            int height = 600;
            setMeasuredDimension(width, height);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            drawWeek(canvas);
            drawDate(canvas);
            drawWeatherDrawable(canvas);
            drawTemperatureText(canvas);
        }

        private void drawWeek(Canvas canvas) {
            for (int i = 0; i < mItemNums; i++) {
                String name = mWeekNames[i];
                float nameWidth = mWeekPaint.measureText(name);
                float x = (mItemWidth - nameWidth) / 2 + i * mItemWidth;
                Log.e("ChartView", "drawWeek_x:" + x + ", y: " + mBaselineY);
                canvas.drawText(name, x, mBaselineY, mWeekPaint);
            }
        }

        private void drawDate(Canvas canvas){
            for (int i = 0; i < mItemNums; i++) {
                String name = mDateNames[i];
                float nameWidth = mDatePaint.measureText(name);
                float x = (mItemWidth - nameWidth) / 2 + i * mItemWidth;
                float y = mBaselineY + 50;
                Log.e("ChartView", "drawDate_x:" + x + ", y: " + y);
                canvas.drawText(name, x, y, mDatePaint);
            }
        }

        private void drawWeatherDrawable(Canvas canvas){
            for (int i = 0; i < mItemNums; i++) {
                float bitmapWidth = mBitmap.getWidth();
                float left = (mItemWidth - bitmapWidth) / 2 + i * mItemWidth;
                float top = mBaselineY + 50 + 30;
                Log.e("ChartView", "drawDate_left:" + left + ", top: " + top);
                canvas.drawBitmap(mBitmap, left, top, mWeatherPaint);
            }
        }

        private void drawTemperatureText(Canvas canvas){
            for (int i = 0; i < mItemNums; i++) {
                String maxName = mMaxTempertrureNames[i];
                String minName = mMinTempertrureNames[i];
                float maxNameWidth = mMaxTemperaturePaint.measureText(maxName);
                float minNameWidth = mMinTemperaturePaint.measureText(minName);
                float maxX = (mItemWidth - maxNameWidth) / 2 + i * mItemWidth;
                float maxY = 4 * mBaselineY + 30;
                float minX = (mItemWidth - minNameWidth) / 2 + i * mItemWidth;
                float minY = 5 * mBaselineY + 30;
                canvas.drawText(maxName, maxX, maxY, mMaxTemperaturePaint);
                canvas.drawText(minName, minX, minY, mMinTemperaturePaint);
            }
        }
    }
}
