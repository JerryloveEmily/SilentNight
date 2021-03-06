package com.jerry.silentnight.ui.activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jerry.silentnight.R;
import com.jerry.silentnight.ui.WeatherChartView;
import com.jerry.silentnight.util.AvoidDoubleClickUtil;
import com.jerry.silentnight.util.StatusBarUtil;
import com.jerry.silentnight.util.ToastUtil;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * 天气
 */
public class WeatherActivity extends BaseActivity {

    @Bind(R.id.rl_title_bar)
    RelativeLayout mRlNavBar;
    @Bind(R.id.tv_left)
    TextView mTvBack;
    /*@Bind(R.id.tv_center)
    TextView mTvTitle;*/
    @Bind(R.id.tv_weather_status)
    TextView mTvWeatherStatus;
    @Bind(R.id.tv_tomorrow_weather_status)
    TextView mTvTomorrowWeatherStatus;
    @Bind(R.id.wcv_chart)
    WeatherChartView mWeaChartView;

    @BindColor(R.color.colorPrimary)
    int mColorNavBar;
    @BindString(R.string.txt_weather)
    String mTxtWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setColor(this,
                getResources().getColor(R.color.colorPrimary),
                0);
        setContentView(R.layout.activity_weather);

        ButterKnife.bind(this);
        initView();
        initEvent();
        Log.e("WeatherActivity", "value: " + (Integer.MAX_VALUE|Integer.MIN_VALUE));
    }

    @Override
    protected void initView() {
//        mRlNavBar.setBackgroundColor(mColorNavBar);
//        mTvTitle.setTextColor(Color.WHITE);
//        mTvTitle.setText(mTxtWeather);
        mTvWeatherStatus.setTextSize(30f);
        Typeface typeface = Typeface.createFromAsset(
                getResources().getAssets(),
                "fonts/classic.otf");
        mTvWeatherStatus.setTypeface(typeface);
        mTvTomorrowWeatherStatus.setTypeface(typeface);
        initWeatherChartView();
    }

    private void initWeatherChartView() {
        int beforeColor = mWeaChartView.getWeekTextColor();
        Log.e("Activity", "beforeColor: " + beforeColor);
        mWeaChartView.setBackgroundColor(Color.parseColor("#3d3d3d"));
//        mWeaChartView.setWeekTextSize(10);
//        mWeaChartView.setDateTextSize(25);
//        mWeaChartView.setMaxTemperTextSize(9);
//        mWeaChartView.setMinTemperTextSize(20);
//        mWeaChartView.setItemWidth(260);
        int afterColor = mWeaChartView.getWeekTextColor();
        Log.e("Activity", "afterColor: " + afterColor);
    }

    @Override
    protected void initEvent() {
        mTvBack.setOnClickListener(new AvoidDoubleClickUtil.OnClickListener() {
            @Override
            public void onSingleClick(View v) {
                finish();
            }
        });
    }
}
