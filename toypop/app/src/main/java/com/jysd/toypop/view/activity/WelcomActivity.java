package com.jysd.toypop.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jysd.toypop.R;
import com.qq.e.ads.splash.SplashAD;
import com.qq.e.ads.splash.SplashADListener;

public class WelcomActivity extends Activity {

    boolean isFirstIn = false;
    // 跳转延时
    //应用id
    private final String APPId = "1105701792";
    //广告id
    private final String SplashPosId = "7070918559312744";
    private SplashAD splashAD;
    RelativeLayout adsParent;
    TextView skipView;
    private static final String SKIP_TEXT = "点击跳过%d";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcom);
        adsParent = (RelativeLayout) this
                .findViewById(R.id.adsRl);
        skipView = (TextView) this.findViewById(R.id.skip_view);
        splashAD = new SplashAD(this, adsParent, skipView, APPId, SplashPosId, new SplashADListener() {
            @Override
            public void onADDismissed() {
                skips();
            }

            @Override
            public void onNoAD(int i) {
                //广告加载失败
                MyThread m = new MyThread();
                new Thread(m).start();
            }

            @Override
            public void onADPresent() {

            }

            @Override
            public void onADClicked() {

            }
            /**
             * 倒计时回调，返回广告还将被展示的剩余时间。
             * 通过这个接口，开发者可以自行决定是否显示倒计时提示，或者还剩几秒的时候显示倒计时
             *
             *  millisUntilFinished 剩余毫秒数
             */
            @Override
            public void onADTick(long millisUntilFinished) {
                skipView.setText(String.format(SKIP_TEXT, Math.round(millisUntilFinished / 1000f)));
            }
        }, 0);


        // the observer of AD
//        SplashAdListener listener = new SplashAdListener() {
//            @Override
//            public void onAdDismissed() {
//                skips();
//                // 跳转至您的应用主界面
//            }
//
//            @Override
//            public void onAdFailed(String arg0) {
//
//                MyThread m = new MyThread();
//                new Thread(m).start();
//            }
//
//            @Override
//            public void onAdPresent() {
//
//            }
//
//            @Override
//            public void onAdClick() {
//                // 设置开屏可接受点击时，该回调可用
//            }
//        };//
//        String adPlaceId = "2894720"; // 重要：请填上您的广告位ID，代码位错误会导致无法请求到广告
//        new SplashAd(this, adsParent, listener, adPlaceId, true);


    }

    private void jump() {
        this.startActivity(new Intent(WelcomActivity.this, MainActivity.class));
        this.finish();
    }


    class MyThread implements Runnable {
        public void run() {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            skips();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    private void skips() {
        // 读取SharedPreferences中需要的数据
        // 使用SharedPreferences来记录程序的使用次数
        SharedPreferences preferences = getSharedPreferences(
                "TOYPOP_SP", MODE_PRIVATE);

        // 取得相应的值，如果没有该值，说明还未写入，用true作为默认值
        isFirstIn = preferences.getBoolean("isFirstInTOYPOP_SP", true);

        // 判断程序与第几次运行，如果是第一次运行则跳转到引导界面，否则跳转到主界面
        if (!isFirstIn) {

            jump();//进入主界面

        } else {
            goGuide();//进入引导
        }

    }

    private void goGuide() { // 跳至引导页
        Intent intent = new Intent(getApplicationContext(), GuideActivity.class);
        startActivity(intent);
        finish();
    }
}