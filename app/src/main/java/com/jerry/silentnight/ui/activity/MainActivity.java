package com.jerry.silentnight.ui.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jerry.silentnight.R;
import com.jerry.silentnight.ui.fragment.AccountFragment;
import com.jerry.silentnight.ui.fragment.LifeFragment;
import com.jerry.silentnight.ui.fragment.StudyFragment;
import com.jerry.silentnight.ui.fragment.WorkFragment;
import com.jerry.silentnight.util.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @Bind(R.id.tab_bar)
    RadioGroup mTabBar;
    @Bind(R.id.tab_work)
    RadioButton mHomeTab;
    @Bind(R.id.fl_container)
    FrameLayout mFlContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        initEvent();
    }

    protected void initView() {
        selectChanged(mTabBar.getCheckedRadioButtonId());
    }

    protected void initEvent(){
        mTabBar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                selectChanged(checkedId);
            }
        });
    }

    private void selectChanged(int checkedId) {
        FragmentManager fManger = getSupportFragmentManager();
        WorkFragment workFragment = (WorkFragment) fManger.findFragmentByTag(WorkFragment.class.getSimpleName());
        StudyFragment studyFragment = (StudyFragment) fManger.findFragmentByTag(StudyFragment.class.getSimpleName());
        LifeFragment lifeFragment = (LifeFragment) fManger.findFragmentByTag(LifeFragment.class.getSimpleName());
        AccountFragment accountFragment = (AccountFragment) fManger.findFragmentByTag(AccountFragment.class.getSimpleName());
        FragmentTransaction ft = fManger.beginTransaction();

        // 隐藏对应的fragment界面
        if (checkedId == R.id.tab_work
                || checkedId == R.id.tab_study
                || checkedId == R.id.tab_life
                || checkedId == R.id.tab_account) {
            if (null != workFragment && !workFragment.isHidden()) {
                ft.hide(workFragment);
            }
            if (null != studyFragment && !studyFragment.isHidden()) {
                ft.hide(studyFragment);
            }
            if (null != lifeFragment && !lifeFragment.isHidden()) {
                ft.hide(lifeFragment);
            }
            if (null != accountFragment && !accountFragment.isHidden()) {
                ft.hide(accountFragment);
            }
        }

        // 选中的fragment，加入事务或者直接显示
        switch (checkedId) {
            case R.id.tab_work:
                if (null == workFragment) {
                    ft.add(R.id.fl_container, WorkFragment.newInstance("", ""),
                            WorkFragment.class.getSimpleName());
                } else {
                    ft.show(workFragment);
                }
                break;
            case R.id.tab_study:
                if (null == studyFragment) {
                    ft.add(R.id.fl_container, StudyFragment.newInstance("", ""),
                            StudyFragment.class.getSimpleName());
                } else {
                    ft.show(studyFragment);
                }
                break;
            case R.id.tab_article:
                ToastUtil.showShortMessage(this, "写文章");
                break;
            case R.id.tab_life:
                if (null == studyFragment) {
                    ft.add(R.id.fl_container, LifeFragment.newInstance("", ""),
                            LifeFragment.class.getSimpleName());
                } else {
                    ft.show(studyFragment);
                }
                break;
            case R.id.tab_account:
                if (null == accountFragment) {
                    ft.add(R.id.fl_container, AccountFragment.newInstance("", ""),
                            AccountFragment.class.getSimpleName());
                } else {
                    ft.show(accountFragment);
                }
                break;
        }
        ft.commitAllowingStateLoss();
    }
}
