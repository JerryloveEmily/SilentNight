package com.jerry.silentnight.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.jerry.silentnight.R;
import com.jerry.silentnight.ui.activity.BreedActivity;
import com.jerry.silentnight.ui.activity.FoodActivity;
import com.jerry.silentnight.ui.activity.HealthActivity;
import com.jerry.silentnight.ui.activity.TravelActivity;
import com.jerry.silentnight.ui.activity.WeatherActivity;
import com.jerry.silentnight.ui.bean.LifeItem;
import com.jerry.silentnight.util.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 生活
 */
public class LifeFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    @Bind(R.id.tv_left)
    TextView mTvBack;
    @Bind(R.id.tv_center)
    TextView mTvTitle;
    @Bind(R.id.rcv_functions)
    RecyclerView mRcvFunctions;

    private BaseQuickAdapter<LifeItem, BaseViewHolder> mAdapter;

    private int[] mIconResIds = {
            R.drawable.ic_life_normal,
            R.drawable.ic_life_normal,
            R.drawable.ic_life_normal,
            R.drawable.ic_life_normal,
            R.drawable.ic_life_normal
    };
    private String[] mTitles = {
            "饮食",
            "健康",
            "天气",
            "旅行",
            "孕育"
    };
    private Class[] mCls = {
            FoodActivity.class,
            HealthActivity.class,
            WeatherActivity.class,
            TravelActivity.class,
            BreedActivity.class
    };

    private List<LifeItem> mLifeItemDatas;
    private int mScreenWidth;

    public LifeFragment() {
    }

    public static LifeFragment newInstance(String param1, String param2) {
        LifeFragment fragment = new LifeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_life, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initView();
        initEvent();
    }

    private void initView() {
        mTvBack.setVisibility(View.GONE);
        mTvTitle.setText("生活");

        mLifeItemDatas = getDatas();
        mScreenWidth = ScreenUtil.getScreenWidth(getContext());
        mAdapter = new BaseQuickAdapter<LifeItem, BaseViewHolder>(
                R.layout.fragment_life_list_item,
                mLifeItemDatas
        ) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, LifeItem s) {
                LinearLayout llRoot = baseViewHolder.getView(R.id.ll_item_root);
                ImageView ivIcon = baseViewHolder.getView(R.id.iv_icon);
                TextView tvTitle = baseViewHolder.getView(R.id.tv_title);
//                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tvTitle.getLayoutParams();
//                params.height = mScreenWidth / 3;
//                llRoot.setLayoutParams(params);
                ivIcon.setImageResource(s.getIconResId());
                tvTitle.setText(s.getTitle());
            }
        };
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        mRcvFunctions.setLayoutManager(layoutManager);
        mRcvFunctions.setAdapter(mAdapter);
    }

    private void initEvent(){
        mRcvFunctions.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter,
                                          View view, int position) {
                LifeItem item = mLifeItemDatas.get(position);
                Intent intent = new Intent(getContext(), item.getCls());
                startActivity(intent);
            }
        });
    }

    private List<LifeItem> getDatas(){
        List<LifeItem> lifeItems = new ArrayList<>();
        for (int i = 0; i < mIconResIds.length; i++) {
            LifeItem item = new LifeItem();
            item.setIconResId(mIconResIds[i]);
            item.setTitle(mTitles[i]);
            item.setCls(mCls[i]);
            lifeItems.add(item);
        }
        return lifeItems;
    }
}
