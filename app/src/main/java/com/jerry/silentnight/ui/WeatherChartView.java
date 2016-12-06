package com.jerry.silentnight.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.jerry.silentnight.R;

import java.util.ArrayList;
import java.util.List;

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
        ChartView chartView = new ChartView(context);
        chartView.setBackgroundColor(Color.parseColor("#3d3d3d"));
        LinearLayout.LayoutParams llParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        chartView.setLayoutParams(llParams);
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
                mMaxTemperChartLinePaint, mMinTemperChartLinePaint, mTemperChartCirclePaint;

        private int mWeekColor = 0xffA6A6A6,
                mDateColor = 0xff999999,
                mMaxTempertureColor = 0xffeeeeee,
                mMinTempertrureColor = 0xffA6A6A6,
                mMaxTempertrureLineColor = 0xff999999,
                mMinTempertrureLineColor = 0xff666666,
                mTemperCircleColor = 0xffdddddd;

        // 需要显示天气的日期个数
        private int mItemNums = 11;
        // 每个天气日期item的宽度的像素值
        private int mItemWidth = 180;

        // 绘制星期的文字的基线位置baseline
        private int mWeekY = 60,
                mDateY = mWeekY + 50,
                mWeatherIconY = 2 * mWeekY + 20,
                mMaxTemperatureY = 4 * mWeekY + 20,
                mMinTemperatureY = 5 * mWeekY + 20,
                mMaxTemperatureChartY = 6 * mWeekY,
                mMinTemperatureChartY = 0;


        private String[] mWeekNames = {
                "昨天", "今天", "周日",
                "周一", "周二", "周三",
                "周四", "周五", "周六",
                "周日", "周一"
        };
        private String[] mDateNames = {
                "12.04", "12.05",
                "12.06", "12.07", "12.08",
                "12.09", "12.10", "12.11",
                "12.12", "12.13", "12.14"
        };
        private String[] mMaxTempertrureNames = {
                "25°", "27°", "22°",
                "23°", "22°", "24°",
                "22°", "22°", "24°",
                "24°", "21°"
        };
        private String[] mMinTempertrureNames = {
                "19°", "17°", "11°",
                "12°", "12°", "12°",
                "12°", "12°", "14°",
                "15°", "13°"
        };

        /*private String[][] mTempertrureRangeNames = {
                {"25°", "19°"}, {"27°", "17°"}, {"22°", "11°"},
                {"23°", "12°"}, {"22°", "12°"}, {"24°", "12°"},
                {"22°", "12°"}, {"22°", "12°"}, {"24°", "14°"},
                {"24°", "15°"}, {"21°", "13°"}
        };*/

        private int[][] mTempertrureRangeNames = {
                {25, 19}, {27, 17}, {22, 11},
                {23, 12}, {22, 12}, {24, 12},
                {22, 12}, {22, 12}, {24, 14},
                {24, 15}, {21, 13}
        };

        private Drawable mDrawable;
        private Bitmap mBitmap;
        private int mMaxTemperature = 0;
        private int mMinTemperature = 0;
        private Path mTemperChartPath = new Path();
        private Bitmap mGradientBitmap;

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
            initPaint(context);
            initDrawable(context);
            initPeriodTemperatureRange();
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        private void initPaint(Context context) {
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
            mMaxTemperaturePaint.setTextSize(40f);
            mMaxTemperaturePaint.setStyle(Paint.Style.FILL);
            mMinTemperaturePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mMinTemperaturePaint.setColor(mMinTempertrureColor);
            mMinTemperaturePaint.setTextSize(36f);
            mMinTemperaturePaint.setStyle(Paint.Style.FILL);

            mMaxTemperChartLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mMaxTemperChartLinePaint.setColor(mMaxTempertrureLineColor);
            mMaxTemperChartLinePaint.setStyle(Paint.Style.FILL_AND_STROKE);
            mMaxTemperChartLinePaint.setStrokeWidth(2);
//            mMaxTemperChartLinePaint.setShader(bitmapShader);

            mMinTemperChartLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mMinTemperChartLinePaint.setColor(mMaxTempertrureLineColor);
            mMinTemperChartLinePaint.setStyle(Paint.Style.STROKE);
            mMinTemperChartLinePaint.setStrokeWidth(2);

            mTemperChartCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mTemperChartCirclePaint.setColor(mTemperCircleColor);
            mTemperChartCirclePaint.setTextSize(36f);
            mTemperChartCirclePaint.setStrokeWidth(2f);
            mTemperChartCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        }

        private void initDrawable(Context context) {
            mBitmap = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.classic_ico_partly_cloudy);
            mBitmap = scaleBitmap(mBitmap, 0.6f);
        }

        /**
         * 初始化一个周期的温度范围
         */
        private void initPeriodTemperatureRange() {
            int[][] rangeNames = mTempertrureRangeNames;
            for (int i = 0; i < rangeNames.length; i++) {
                int[] names = rangeNames[i];
                int maxValue = names[0];
                int minValue = names[1];
                if (i == 0) {
                    mMaxTemperature = maxValue;
                    mMinTemperature = minValue;
                }
                if (maxValue > mMaxTemperature) {
                    mMaxTemperature = maxValue;
                }
                if (minValue < mMinTemperature) {
                    mMinTemperature = minValue;
                }
            }
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
            int height = 500;
            setMeasuredDimension(width, height);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            createShader();
        }

        private void createShader() {
            int h = getHeight();
            LinearGradient gradient = new LinearGradient(
                    0, mMaxTemperatureY, 0, h,
                    new int[] {0xff555555, 0xff262626, 0xff555555}, null,
                    Shader.TileMode.REPEAT);
            mMaxTemperChartLinePaint.setShader(gradient);
            mMinTemperChartLinePaint.setShader(gradient);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            drawWeek(canvas);
            drawDate(canvas);
            drawWeatherDrawable(canvas);
            drawTemperatureText(canvas);
            drawTemperatureChart(canvas);
        }

        private void drawWeek(Canvas canvas) {
            for (int i = 0; i < mItemNums; i++) {
                String name = mWeekNames[i];
                float nameWidth = mWeekPaint.measureText(name);
                float x = (mItemWidth - nameWidth) / 2 + i * mItemWidth;
//                Log.e("ChartView", "drawWeek_x:" + x + ", y: " + mWeekY);
                canvas.drawText(name, x, mWeekY, mWeekPaint);
            }
        }

        private void drawDate(Canvas canvas) {
            for (int i = 0; i < mItemNums; i++) {
                String name = mDateNames[i];
                float nameWidth = mDatePaint.measureText(name);
                float x = (mItemWidth - nameWidth) / 2 + i * mItemWidth;
                float y = mDateY;
//                Log.e("ChartView", "drawDate_x:" + x + ", y: " + y);
                canvas.drawText(name, x, y, mDatePaint);
            }
        }

        private void drawWeatherDrawable(Canvas canvas) {
            for (int i = 0; i < mItemNums; i++) {
                float bitmapWidth = mBitmap.getWidth();
                float left = (mItemWidth - bitmapWidth) / 2 + i * mItemWidth;
                float top = mWeatherIconY;
//                Log.e("ChartView", "drawDate_left:" + left + ", top: " + top);
                canvas.drawBitmap(mBitmap, left, top, mWeatherPaint);
            }
        }

        private void drawTemperatureText(Canvas canvas) {
            for (int i = 0; i < mItemNums; i++) {
                String maxName = mMaxTempertrureNames[i];
                String minName = mMinTempertrureNames[i];
                float maxNameWidth = mMaxTemperaturePaint.measureText(maxName);
                float minNameWidth = mMinTemperaturePaint.measureText(minName);
                float maxX = (mItemWidth - maxNameWidth) / 2 + i * mItemWidth;
                float maxY = mMaxTemperatureY;
                float minX = (mItemWidth - minNameWidth) / 2 + i * mItemWidth;
                float minY = mMinTemperatureY;
                canvas.drawText(maxName, maxX, maxY, mMaxTemperaturePaint);
                canvas.drawText(minName, minX, minY, mMinTemperaturePaint);
            }
        }

        private void drawTemperatureChart(Canvas canvas) {
            List<Points> pointsList = calculateStartEndXY();
            // 绘制最高温度的线
            for (int i = 0; i < pointsList.size(); i++) {
                Points points = pointsList.get(i);
                createShader(mMaxPath, i, points.startX, points.maxStartY, points.endX, points.maxEndY);
            }
            mMaxPath.lineTo(getWidth(), getHeight());
            mMaxPath.lineTo(0, getHeight());
            mMaxPath.close();
            canvas.drawPath(mMaxPath, mMaxTemperChartLinePaint);

            for (int i = 0; i < pointsList.size(); i++) {
                Points points = pointsList.get(i);
                createShader(mMinPath, i, points.startX, points.minStartY, points.endX, points.minEndY);
            }
            mMinPath.lineTo(getWidth(), getHeight());
            mMinPath.lineTo(0, getHeight());
            mMinPath.close();
            canvas.drawPath(mMinPath, mMinTemperChartLinePaint);
            for (int i = 0; i < pointsList.size(); i++) {
                Points points = pointsList.get(i);
                canvas.drawLine(points.startX, points.maxStartY,
                        points.endX, points.maxEndY, mTemperChartCirclePaint);
                // 绘制最低温度的线
                canvas.drawLine(points.startX, points.minStartY,
                        points.endX, points.minEndY, mTemperChartCirclePaint);
                // 绘制最高温度所在位置圆点
                canvas.drawCircle(points.endX, points.maxEndY, 6, mTemperChartCirclePaint);
                // 绘制最低温度所在位置圆点
                canvas.drawCircle(points.endX, points.minEndY, 6, mTemperChartCirclePaint);
            }
        }

        Path mMaxPath = new Path();
        Path mMinPath = new Path();

        private void createShader(Path path, int i, float startX, float startY, float endX, float endY) {
            if (i == 0) {
                path.moveTo(startX, startY);
            }
            path.lineTo(endX, endY);
        }

        private List<Points> calculateStartEndXY() {
            List<Points> pointsList = new ArrayList<>();
            // 每个温度刻度直接的y轴偏移量像素
            int offset = 5;
            int[][] rangeNames = mTempertrureRangeNames;
            for (int i = 0; i <= mItemNums; i++) {
                int maxTemper, minTemper;
                int preMaxTemper, curMaxTemper, preMinTemper, curMinTemper;
                if (i != mItemNums) {
                    int[] temperatureNames = rangeNames[i];
                    maxTemper = temperatureNames[0];
                    minTemper = temperatureNames[1];
                    // 前一个和当前的最高温度
                    preMaxTemper = ((i == 0) ? maxTemper : rangeNames[i - 1][0]);
                    curMaxTemper = maxTemper;

                    // 前一个和当前的最低温度
                    preMinTemper = ((i == 0) ? minTemper : rangeNames[i - 1][1]);
                    curMinTemper = minTemper;
                } else {
                    // 最后一条线的前一个和当前的最高温度
                    curMaxTemper = preMaxTemper = rangeNames[i - 1][0];

                    // 最后一条线的前一个和当前的最低温度
                    curMinTemper = preMinTemper = rangeNames[i - 1][1];
                }
                float startX = (i == 0) ? 0 : (i - 0.5f) * mItemWidth;
                float maxStartY = mMaxTemperatureChartY + (mMaxTemperature - preMaxTemper) * offset;
                float endX = (i == 0) ? mItemWidth / 2 : (i + 0.5f) * mItemWidth;
                float maxEndY = mMaxTemperatureChartY + (mMaxTemperature - curMaxTemper) * offset;
                // 最低温度起始和结束Y值
                float minStartY = mMaxTemperatureChartY + (mMaxTemperature - preMinTemper) * offset + 3;
                float minEndY = mMaxTemperatureChartY + (mMaxTemperature - curMinTemper) * offset + 3;
                Points points = new Points();
                points.startX = startX;
                points.maxStartY = maxStartY;
                points.endX = endX;
                points.maxEndY = maxEndY;
                points.minStartY = minStartY;
                points.minEndY = minEndY;
                pointsList.add(points);
            }
            return pointsList;
        }

        class Points {
            public float startX;
            public float maxStartY;
            public float endX;
            public float maxEndY;
            public float minStartY;
            public float minEndY;
        }
    }
}
