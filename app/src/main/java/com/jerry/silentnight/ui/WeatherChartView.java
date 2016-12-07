package com.jerry.silentnight.ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.support.annotation.ColorInt;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
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

    private ChartView mChartView;
    private int mBackgroundColor = Color.parseColor("#3d3d3d");

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
        mChartView = new ChartView(context);
//        mChartView.setBackgroundColor(mBackgroundColor);
        LinearLayout.LayoutParams llParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        mChartView.setLayoutParams(llParams);
        addView(mChartView);
    }

    @Override
    public void setBackgroundColor(int color) {
        super.setBackgroundColor(color);
        this.mBackgroundColor = color;
    }

    public int getWeekTextColor() {
        return mChartView.getWeekTextColor();
    }

    public void setWeekTextColor(int textColor) {
        mChartView.setWeekTextColor(textColor);
    }

    public int getDateTextColor() {
        return mChartView.getDateTextColor();
    }

    public void setDateTextColor(int textColor) {
        mChartView.setDateTextColor(textColor);
    }

    public int getItemWidth() {
        return mChartView.getItemWidth();
    }

    public void setItemWidth(int itemWidth) {
        mChartView.setItemWidth(itemWidth);
    }

    public float getMinTemperTextSize() {
        return mChartView.getMinTemperTextSize();
    }

    public void setMinTemperTextSize(float textSize) {
        mChartView.setMinTemperTextSize(textSize);
    }

    public float getMaxTemperTextSize() {
        return mChartView.getMaxTemperTextSize();
    }

    public void setMaxTemperTextSize(float textSize) {
        mChartView.setMaxTemperTextSize(textSize);
    }

    public float getDateTextSize() {
        return mChartView.getDateTextSize();
    }

    public void setDateTextSize(float textSize) {
        mChartView.setDateTextSize(textSize);
    }

    public float getWeekTextSize() {
        return mChartView.getWeekTextSize();
    }

    public void setWeekTextSize(float textSize) {
        mChartView.setWeekTextSize(textSize);
    }

    public int getMinTemperTextColor() {
        return mChartView.getMinTemperTextColor();
    }

    public void setMinTemperTextColor(int textColor) {
        mChartView.setMinTemperTextColor(textColor);
    }

    public int getMaxTemperTextColor() {
        return mChartView.getMaxTemperTextColor();
    }

    public void setMaxTemperTextColor(int textColor) {
        mChartView.setMaxTemperTextColor(textColor);
    }

    /**
     * 天气图表视图
     */
    private class ChartView extends View {

        private TextPaint mWeekPaint, mDatePaint,
                mMaxTemperTextPaint, mMinTemperTextPaint;

        private Paint mWeatherIconPaint,
                mMaxTemperChartLinePaint, mMinTemperChartLinePaint,
                mTemperChartCirclePaint;

        private int mWeekTextColor = 0xffA6A6A6,
                mDateTextColor = 0xff999999,
                mMaxTemperTextColor = 0xffeeeeee,
                mMinTemperTextColor = 0xffA6A6A6,
                mMaxTempertrureLineColor = 0xff999999,
                mMinTempertrureLineColor = 0xff666666,
                mTemperCircleColor = 0xffdddddd;

        private float mWeekTextSize = 16f,
                mDateTextSize = 10f,
                mMaxTemperTextSize = 16f,
                mMinTemperTextSize = 15f;

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

        private int[][] mTempertrureRangeNames = {
                {25, 19}, {27, 17}, {22, 11},
                {23, 12}, {22, 12}, {24, 12},
                {22, 12}, {22, 12}, {24, 14},
                {24, 15}, {21, 13}
        };

        private List<InnerData> mDatas;

        private Bitmap[] mWeatherIconBitmaps;
        private int mMaxTemperature = 0;
        private int mMinTemperature = 0;
        private Path mMaxTemperPath = new Path();
        private Path mMinTemperPath = new Path();

        public ChartView(Context context) {
            super(context);
            init(context);
        }

        public ChartView(Context context, AttributeSet attrs) {
            super(context, attrs);
            init(context);
        }

        public ChartView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            init(context);
        }

        private void init(Context context) {
            initComponents(context);
            initData();
        }

        private void initComponents(Context context) {
            initTextSize();
            initPaint(context);
            initDrawable(context);
            initPeriodTemperatureRange();
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        /**
         * 初始化文字大小
         */
        private void initTextSize() {
            mWeekTextSize = applyDimensionTextSize(mWeekTextSize);
            mDateTextSize = applyDimensionTextSize(mDateTextSize);
            mMaxTemperTextSize = applyDimensionTextSize(mMaxTemperTextSize);
            mMinTemperTextSize = applyDimensionTextSize(mMinTemperTextSize);
        }

        /**
         * 按SP单位缩放文字大小
         *
         * @param size 文字大小
         * @return 缩放后的文字大小
         */
        private float applyDimensionTextSize(float size) {
            return applyDimensionTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        }

        private float applyDimensionTextSize(int unit, float size) {
            Context c = getContext();
            Resources r;
            if (c == null)
                r = Resources.getSystem();
            else
                r = c.getResources();
            return TypedValue.applyDimension(
                    unit, size, r.getDisplayMetrics());
        }

        private void initData() {
            mDatas = new ArrayList<>();
            for (int i = 0; i < 11; i++) {
                InnerData data = new InnerData();
                data.setWeekName(mWeekNames[i]);
                data.setDateName(mDateNames[i]);
                data.setWeatherIcon(mWeatherIconBitmaps[i]);
                data.setMaxTemper(mTempertrureRangeNames[i][0]);
                data.setMinTemper(mTempertrureRangeNames[i][1]);
                mDatas.add(data);
            }
        }

        private void initPaint(Context context) {
            mWeekPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
            mWeekPaint.setColor(mWeekTextColor);
            mWeekPaint.setTextSize(mWeekTextSize);
            mWeekPaint.setStyle(Paint.Style.FILL);

            mDatePaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
            mDatePaint.setColor(mDateTextColor);
            mDatePaint.setTextSize(mDateTextSize);
            mDatePaint.setStyle(Paint.Style.FILL);

            mWeatherIconPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mWeatherIconPaint.setColor(mDateTextColor);
            mWeatherIconPaint.setTextSize(35f);
            mWeatherIconPaint.setStyle(Paint.Style.FILL);

            mMaxTemperTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
            mMaxTemperTextPaint.setColor(mMaxTemperTextColor);
            mMaxTemperTextPaint.setTextSize(mMaxTemperTextSize);
            mMaxTemperTextPaint.setStyle(Paint.Style.FILL);
            mMinTemperTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
            mMinTemperTextPaint.setColor(mMinTemperTextColor);
            mMinTemperTextPaint.setTextSize(mMinTemperTextSize);
            mMinTemperTextPaint.setStyle(Paint.Style.FILL);

            mMaxTemperChartLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mMaxTemperChartLinePaint.setColor(mMaxTempertrureLineColor);
            mMaxTemperChartLinePaint.setStyle(Paint.Style.FILL_AND_STROKE);
            mMaxTemperChartLinePaint.setStrokeWidth(2);

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
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.classic_ico_partly_cloudy);
            bitmap = scaleBitmap(bitmap, 0.6f);
            mWeatherIconBitmaps = new Bitmap[mItemNums];
            for (int i = 0; i < mItemNums; i++) {
                mWeatherIconBitmaps[i] = bitmap;
            }
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
                    new int[]{0xff555555, 0xff262626, 0xff555555}, null,
                    Shader.TileMode.REPEAT);
            mMaxTemperChartLinePaint.setShader(gradient);
            mMinTemperChartLinePaint.setShader(gradient);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
//            mWeekY = (int) (mWeekY - (mWeekPaint.ascent() + mWeekPaint.descent())/2);
//            mDateY = (int) (mDateY - (mDatePaint.ascent() + mDatePaint.descent())/2);
//            mMaxTemperatureY = (int) (mMaxTemperatureY - (mMaxTemperTextPaint.ascent() + mMaxTemperTextPaint.descent())/2);
//            mMinTemperatureY = (int) (mMinTemperatureY - (mMinTemperTextPaint.ascent() + mMinTemperTextPaint.descent())/2);

            for (int i = 0; i < mDatas.size(); i++) {
                InnerData data = mDatas.get(i);
                drawTextOrBitmap(canvas, data.getWeekName(), i, mWeekY, false, mWeekPaint);
                drawTextOrBitmap(canvas, data.getDateName(), i, mDateY, false, mDatePaint);
                drawTextOrBitmap(canvas, data.getWeatherIcon(), i, mWeatherIconY, true, mWeatherIconPaint);
                drawTextOrBitmap(canvas, appendTemperature(data.getMaxTemper()), i, mMaxTemperatureY, false, mMaxTemperTextPaint);
                drawTextOrBitmap(canvas, appendTemperature(data.getMinTemper()), i, mMinTemperatureY, false, mMinTemperTextPaint);
            }
            drawTemperatureChart(canvas);
        }

        private String appendTemperature(int value) {
            return String.valueOf(value) + "°";
        }

        private void drawTemperatureText(Canvas canvas, int position) {
            drawTextOrBitmap(canvas, mMaxTempertrureNames[position], position, mMaxTemperatureY, false, mMaxTemperTextPaint);
            drawTextOrBitmap(canvas, mMinTempertrureNames[position], position, mMinTemperatureY, false, mMinTemperTextPaint);
        }

        private void drawTextOrBitmap(Canvas canvas, Object object, int position, float y, boolean isBitmap, Paint paint) {
            if (isBitmap) {
                drawWeatherDrawable(canvas, (Bitmap) object, position, y, paint);
            } else {
                drawText(canvas, (String) object, position, y, paint);
            }
        }

        private void drawText(Canvas canvas, String text, int position, float y, Paint paint) {
            float nameWidth = paint.measureText(text);
            float x = (mItemWidth - nameWidth) / 2 + position * mItemWidth;
            canvas.drawText(text, x, y, paint);
        }

        private void drawWeatherDrawable(Canvas canvas, Bitmap bitmap, int position, float top, Paint paint) {
            float bitmapWidth = bitmap.getWidth();
            float left = (mItemWidth - bitmapWidth) / 2 + position * mItemWidth;
            canvas.drawBitmap(bitmap, left, top, paint);
        }

        private void drawTemperatureChart(Canvas canvas) {
            List<Points> pointsList = calculateStartEndXY();
            // 绘制最高温度的线
            for (int i = 0; i < pointsList.size(); i++) {
                Points points = pointsList.get(i);
                createShader(mMaxTemperPath, i, points.startX, points.maxStartY, points.endX, points.maxEndY);
            }
            mMaxTemperPath.lineTo(getWidth(), getHeight());
            mMaxTemperPath.lineTo(0, getHeight());
            mMaxTemperPath.close();
            canvas.drawPath(mMaxTemperPath, mMaxTemperChartLinePaint);

            for (int i = 0; i < pointsList.size(); i++) {
                Points points = pointsList.get(i);
                createShader(mMinTemperPath, i, points.startX, points.minStartY, points.endX, points.minEndY);
            }
            mMinTemperPath.lineTo(getWidth(), getHeight());
            mMinTemperPath.lineTo(0, getHeight());
            mMinTemperPath.close();
            canvas.drawPath(mMinTemperPath, mMinTemperChartLinePaint);
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
            float startX;
            float maxStartY;
            float endX;
            float maxEndY;
            float minStartY;
            float minEndY;
        }

        public class InnerData {
            private String weekName;
            private String dateName;
            private Bitmap weatherIcon;
            private int maxTemper;
            private int minTemper;

            public String getWeekName() {
                return weekName;
            }

            public void setWeekName(String weekName) {
                this.weekName = weekName;
            }

            public String getDateName() {
                return dateName;
            }

            public void setDateName(String dateName) {
                this.dateName = dateName;
            }

            public Bitmap getWeatherIcon() {
                return weatherIcon;
            }

            public void setWeatherIcon(Bitmap weatherIcon) {
                this.weatherIcon = weatherIcon;
            }

            public int getMaxTemper() {
                return maxTemper;
            }

            public void setMaxTemper(int maxTemper) {
                this.maxTemper = maxTemper;
            }

            public int getMinTemper() {
                return minTemper;
            }

            public void setMinTemper(int minTemper) {
                this.minTemper = minTemper;
            }
        }

        /**
         * 设置文本颜色
         *
         * @param paint     需要设置的画笔
         * @param textColor 设置进来的颜色
         */
        private void setTextColor(Paint paint, int textColor) {
            if (textColor != paint.getColor()) {
                paint.setColor(textColor);
                invalidate();
            }
        }

        /**
         * 设置文本大小
         *
         * @param paint    需要设置的画笔
         * @param textSize 设置进来的大小
         */
        private void setTextSize(Paint paint, float textSize) {
            textSize = applyDimensionTextSize(textSize);
            if (textSize != paint.getTextSize()) {
                paint.setTextSize(textSize);
                invalidate();
            }
        }

        public int getWeekTextColor() {
            return mWeekTextColor;
        }

        public void setWeekTextColor(int textColor) {
            this.mWeekTextColor = textColor;
            setTextColor(mWeekPaint, textColor);
        }

        public int getDateTextColor() {
            return mDateTextColor;
        }

        public void setDateTextColor(int textColor) {
            this.mDateTextColor = textColor;
            setTextColor(mDatePaint, textColor);
        }

        public int getItemWidth() {
            return mItemWidth;
        }

        public void setItemWidth(int itemWidth) {
            if (this.mItemWidth != itemWidth) {
                this.mItemWidth = itemWidth;
                invalidate();
            }
        }

        public int getMinTemperTextColor() {
            return mMinTemperTextColor;
        }

        public void setMinTemperTextColor(int textColor) {
            this.mMinTemperTextColor = textColor;
            setTextColor(mMinTemperTextPaint, textColor);
        }

        public int getMaxTemperTextColor() {
            return mMaxTemperTextColor;
        }

        public void setMaxTemperTextColor(int textColor) {
            this.mMaxTemperTextColor = textColor;
            setTextColor(mMaxTemperTextPaint, textColor);
        }

        public float getWeekTextSize() {
            return mWeekTextSize;
        }

        public void setWeekTextSize(float textSize) {
            this.mWeekTextSize = textSize;
            setTextSize(mWeekPaint, textSize);
        }

        public float getDateTextSize() {
            return mDateTextSize;
        }

        public void setDateTextSize(float textSize) {
            this.mDateTextSize = textSize;
            setTextSize(mDatePaint, textSize);
        }

        public float getMaxTemperTextSize() {
            return mMaxTemperTextSize;
        }

        public void setMaxTemperTextSize(float textSize) {
            this.mMaxTemperTextSize = textSize;
            setTextSize(mMaxTemperTextPaint, textSize);
        }

        public float getMinTemperTextSize() {
            return mMinTemperTextSize;
        }

        public void setMinTemperTextSize(float textSize) {
            this.mMinTemperTextSize = textSize;
            setTextSize(mMinTemperTextPaint, textSize);
        }
    }
}
