package com.jerry.silentnight.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * 项目名：SilentNight
 * 包名：  com.jerry.silentnight.ui.fragment
 * 文件名：${}
 * 作者：  Administrator on 2016/11/21 15:51
 * 邮箱：JerryloveEmily@163.com
 */
public class BaseFragment extends Fragment{

    protected Activity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }
}
