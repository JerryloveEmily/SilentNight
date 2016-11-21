package com.jerry.silentnight.ui.activity;

import android.animation.Animator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jerry.silentnight.R;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    @Bind(R.id.tv_shimmer)
    ShimmerTextView tvShimmer;
    private Shimmer mShimmer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mShimmer = new Shimmer();
        mShimmer.setRepeatCount(2)
                .setDuration(1200)
                .setAnimatorListener(new AnimatorListenerAdapter(){
                    @Override
                    public void onAnimationEnd(Animator animator) {
                        super.onAnimationEnd(animator);
                        Intent intent = new Intent(SplashActivity.this,
                                MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
        mShimmer.start(tvShimmer);
    }

    private class AnimatorListenerAdapter implements Animator.AnimatorListener{

        @Override
        public void onAnimationStart(Animator animator) {
        }

        @Override
        public void onAnimationEnd(Animator animator) {
        }

        @Override
        public void onAnimationCancel(Animator animator) {
        }

        @Override
        public void onAnimationRepeat(Animator animator) {
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mShimmer.cancel();
    }
}
