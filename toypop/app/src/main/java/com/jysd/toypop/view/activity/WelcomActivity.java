package com.jysd.toypop.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.RelativeLayout;

import com.baidu.mobads.SplashAd;
import com.baidu.mobads.SplashAdListener;
import com.jysd.toypop.R;

public class WelcomActivity extends Activity {

    boolean isFirstIn = false;
    // 跳转延时
    //应用id 1105701792
//    private final String APPId = "";
    //广告id 7070918559312744
//    private final String SplashPosId = "";
//    private SplashAD splashAD;
    RelativeLayout adsParent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcom);
        adsParent = (RelativeLayout) this
                .findViewById(R.id.adsRl);
        // the observer of AD
        SplashAdListener listener = new SplashAdListener() {
            @Override
            public void onAdDismissed() {
                skips();
                // 跳转至您的应用主界面
            }

            @Override
            public void onAdFailed(String arg0) {

                MyThread m = new MyThread();
                new Thread(m).start();
            }

            @Override
            public void onAdPresent() {

            }

            @Override
            public void onAdClick() {
                // 设置开屏可接受点击时，该回调可用
            }
        };//
        String adPlaceId = "2894720"; // 重要：请填上您的广告位ID，代码位错误会导致无法请求到广告
        new SplashAd(this, adsParent, listener, adPlaceId, true);


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