package com.jerry.silentnight.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

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
        chartView.setBackgroundColor(Color.RED);
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

        private int mWeekColor = 0xffffffff,
                mDateColor = 0xff999999,
                mMaxTempertureColor = 0xff222222,
                mMinTempertrureColor = 0xff666666;

        // 需要显示天气的日期个数
        private int mItemNums = 11;
        // 每个天气日期item的宽度的像素值
        private int mItemWidth = 200;

        private String[] mWeekNames = {
                "昨天", "今天", "周日",
                "周一", "周二", "周三",
                "周四", "周五", "周六",
                "周日", "周一"
        };

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
        }

        private void initPaint() {
            mWeekPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mWeekPaint.setColor(mWeekColor);
            mWeekPaint.setTextSize(45f);
            mWeekPaint.setStyle(Paint.Style.FILL);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            int width = mItemNums * mItemWidth;
            int height = 400;
            setMeasuredDimension(width, height);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            drawWeek(canvas);
            super.onDraw(canvas);
        }

        private void drawWeek(Canvas canvas) {
            for (int i = 0; i < mItemNums; i++) {
                String name = mWeekNames[i];
                float nameWidth = mWeekPaint.measureText(name);
                float x = (mItemWidth - nameWidth) / 2 + i * mItemWidth;
                float y = 100f;
                Log.e("ChartView", "drawWeek_x:" + x + ", y: " + y);
                canvas.drawText(name, x, y, mWeekPaint);
            }
        }
    }
}
